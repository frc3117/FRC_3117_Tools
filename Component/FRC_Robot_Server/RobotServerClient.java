package frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;

import frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Interface.CommandCallback;
import frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Interface.ConnectionCallback;
import frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Interface.DatafeedCallback;
import frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Packet.*;

public class RobotServerClient
{
    public RobotServerClient(String address)
    {
        this(address, 5810);
    }
    public RobotServerClient(String address, int port)
    {
        _address = address;
        _port = port;
    }

    private String _address = "localhost";
    private int _port = 1;

    private String _name;

    private HashMap<String, CommandCallback> _commandCallback = new HashMap<>();
    private HashMap<String, DatafeedCallback> _dataFeedCallback = new HashMap<>();

    private HashMap<String, Response> _awaitingResponse = new HashMap<>();

    private Socket _tcp;
    private Thread _loopThread;

    private long _frame = 0;

    private boolean _shouldDisconnect;

    private void Loop()
    {
        if (!_tcp.isConnected())
        {
            _tcp = null;
        }

        if (IsConnected())
        {
            try
            {
                while (_tcp.getInputStream().available() > 0) 
                {
                    var packet = GetPacket();

                    if (packet != null)
                    {
                        switch (packet.Type)
                        {
                            case Command:
                            {
                                var commandPacket = packet.GetData(CommandPacket.class);
                                var responsePacket = new Packet(packet.Sender, PacketType.Response, HandleCommand(commandPacket));

                                SendPacket(responsePacket);
                                break;
                            }

                            case Data:
                            {
                                var dataPacket = packet.GetData(DataPacket.class);
                                _dataFeedCallback.get(dataPacket.Name).Invoke(dataPacket);
                                break;
                            }

                            case Response:
                            {
                                var responsePacket = packet.GetData(ResponsePacket.class);
                                if (_awaitingResponse.containsKey(responsePacket.RequestID))
                                {
                                    _awaitingResponse.get(responsePacket.RequestID).SetResponsePacket(responsePacket);
                                }
                                break;
                            }
                            default:
                                break;
                        }
                    }
                }

                var awaitingKeySet = _awaitingResponse.keySet();
                for (var responseKey : awaitingKeySet) 
                {
                    var response = _awaitingResponse.get(responseKey);

                    if (Duration.between(LocalDateTime.now(), response.SentTime).toMillis() >= 5000)
                    {
                        response.Timeout();
                        awaitingKeySet.remove(responseKey);
                    }
                }

                if (_frame++ % 100 == 0)
                    SendPacket(new Packet("Server", PacketType.HeartBeat, null));

                Thread.sleep(10);
            }
            catch (Exception e)
            {

            }
        }
        else
        {
            try
            {
                _tcp = new Socket(_address, _port);
                Register(_name);

                for (var feed : _dataFeedCallback.keySet()) 
                {
                    var requestID = GenerateRequestID();
                    SendPacket(new Packet("Server", PacketType.Command, new CommandPacket("subscribe", requestID, feed)));
                }
            }
            catch (Exception e)
            {

            }   
        }
    }

    public void Connect(ConnectionCallback callback)
    {
        if (_tcp == null)
        {
            _loopThread = new Thread(new RobotServerTask(this, callback));
            _loopThread.start();
        }
    }
    public void Disconnect()
    {
        if (_tcp != null)
        {
            try
            {
                _tcp.close();
            }
            catch (Exception e)
            {

            }

            _tcp = null;
        }
    }

    public boolean IsConnected()
    {
        return _tcp != null;
    }

    public void Register (String name)
    {
        if (name != null)
        {
            _name = name;

            var packet = new Packet("Server", PacketType.Register, new RegisterPacket(name));

            SendPacket(packet);
        }
    }

    public void AddCommand(String name, CommandCallback callback)
    {
        _commandCallback.put(name, callback);
    }
    public void RemoveCommand(String name)
    {
        _commandCallback.remove(name);
    }
    public void ClearCommand()
    {
        _commandCallback.clear();
    }

    public void Subscribe(String name, DatafeedCallback callback)
    {
        var requestID = GenerateRequestID();
        SendPacket(new Packet("Server", PacketType.Command, new CommandPacket("subscribe", requestID, name)));

        _dataFeedCallback.put(name, callback);
    }
    public void Unsubscribe(String name)
    {
        var requestID = GenerateRequestID();
        SendPacket(new Packet("Server", PacketType.Command, new CommandPacket("unsubscribe", requestID, name)));

        _dataFeedCallback.remove(name);
    }

    public void FeedData(String name, Object data)
    {
        SendPacket(new Packet("Server", PacketType.Data, new DataPacket(name, data)));
    }

    public Response SendPacket(Packet packet)
    {
        return SendPacket(packet, null);
    }
    public Response SendPacket(Packet packet, String requestId)
    {
        try
        {
            //Create the message to send to the client
            var json = new Gson().toJson(packet);

            var buffer = json.getBytes(StandardCharsets.UTF_8);
            var data = ByteBuffer.allocate(4 + buffer.length).order(ByteOrder.LITTLE_ENDIAN).putInt(buffer.length).put(buffer).array();

            _tcp.getOutputStream().write(data);

            if (requestId == null)
                return null;

            var response = new Response(LocalDateTime.now());
            _awaitingResponse.put(requestId, response);

            return response;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private Packet GetPacket()
    {
        try
        {
            //Read the next incoming message
            //Header: 4 bytes (Int32), representing the lenght of the message (X)
            //Message: X bytes (Json), representing the message in a json format
            var byteToRead = ByteBuffer.wrap(_tcp.getInputStream().readNBytes(4)).order(ByteOrder.LITTLE_ENDIAN).getInt();

            var json = new String(_tcp.getInputStream().readNBytes(byteToRead), StandardCharsets.UTF_8);

            return new Gson().fromJson(json, Packet.class);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private String GenerateRequestID()
    {
        return UUID.randomUUID().toString();
    }

    private ResponsePacket HandleCommand(CommandPacket command)
    {
        if (_commandCallback.containsKey(command.Command))
            return _commandCallback.get(command.Command).Invoke(command);

        switch (command.Command)
        {
            case "print":
                System.out.println(command.GetParam(String.class));
                return new ResponsePacket(command.RequestID, true, null);

            default:
                return new ResponsePacket(command.RequestID, false, null);
        }
    }

    private class RobotServerTask implements Runnable
    {
        public RobotServerTask(final RobotServerClient client, final ConnectionCallback callback)
        {
            _client = client;
            _callback = callback;
        }

        private final RobotServerClient _client;
        private final ConnectionCallback _callback;

        @Override
        public void run() 
        {
            try
            {
                _tcp = new Socket(_address, _port);
                _callback.Invoke();
            }
            catch (Exception e)
            {
                _tcp = null;
                return;
            }

            while (true)
            {
                if (_shouldDisconnect)
                    return;

                _client.Loop();
            }
        }
    }
}
