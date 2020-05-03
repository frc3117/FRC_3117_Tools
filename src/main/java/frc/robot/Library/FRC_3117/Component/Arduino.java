package frc.robot.Library.FRC_3117.Component;

import java.util.Arrays;
import java.util.HashMap;

import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Library.FRC_3117.Interface.Action;
import frc.robot.Library.FRC_3117.Interface.Component;

/**
 * A class to comunicate to an arduino through a serial port
 */
public class Arduino implements Component
{
    public Arduino(int baudRate, SerialPort.Port port)
    {
        _port = port;
        _baudRate = baudRate;

        _methods = new HashMap<>();

        Open();
    }
    public Arduino(int baudRate,SerialPort.Port port, boolean open)
    {
        _port = port;
        _baudRate = baudRate;

        _methods = new HashMap<>();

        if(open)
            Open();
    }
   
    private SerialPort _serial;
    private SerialPort.Port _port;
    private int _baudRate;

    private HashMap<String, Action> _methods;

    private boolean _isOpen = false;
    private boolean _isReading = false;

    private byte _byteToRead;

    private String[] _currentParams;

    public void Awake()
    {

    }

    public void Init()
    {
        
    }

    public void DoSystem()
    {
        //Data available
        while(_serial.getBytesReceived() > 0)
        {
            if(_isReading)
            {
                if(_serial.getBytesReceived() < _byteToRead)
                    return;

                String result = new String(_serial.read(_byteToRead));
                
                //[0] = Method Name
                //All The Other Are Parameters (If there is any);
                String[] Split = result.split("|", 0);
                
                if(Split.length >= 2)
                    _currentParams = Arrays.copyOfRange(Split, 1, Split.length);

                if(_methods.containsKey(Split[0]))
                {
                    _methods.get(Split[0]).Invoke();
                }

                _currentParams = null;
                _isReading = false;
            }
            else
            {
                _byteToRead = _serial.read(1)[0];
                _isReading = true;
            }
            
        }
    }

    /**
     * Register a callback for an incoming command
     * @param MethodName The name of the command
     * @param Method The callback of the command
     */
    public void RegisterMethod(String MethodName, Action Method)
    {
        _methods.put(MethodName, Method);
    }
    
    /**
     * Get the selected parameter of the current incoming method
     * @param index The index of the parameter
     * @return The selected parameter of the current incoming method
     */
    public String GetParam(int index)
    {
        if(_currentParams == null)
            return null;

        return _currentParams[index];
    }
    /**
     * Get all the parameters of the current incoming method
     * @return The parameters of the current incoming method
     */
    public String[] GetAllParams()
    {
        return _currentParams;
    }

    /**
     * Send a command to the arduino
     * @param CommandName The name of the command
     * @param Params The parameters of the command
     */
    public void SendCommand(String CommandName, String... Params)
    {
        if(Params.length >= 1)
            _serial.writeString(CommandName + "|" + String.join("|", Params) + "$");
        else
            _serial.writeString(CommandName + "$");
    }
    /**
     * Send a command to the arduino
     * @param CommandName The name of the command
     * @param Params The parameters of the command
     */
    public void SendCommand(String CommandName, Object... Params)
    {
        String[] stringParams = new String[Params.length];

        for(int i = 0; i < Params.length; i++)
        {
            stringParams[i] = Params[i].toString();
        }

        SendCommand(CommandName, stringParams);
    }

    /**
     * Start the comunication betwee the roborio and the arduino
     */
    public void Open()
    {
        if(_isOpen)
            return;

        _serial = new SerialPort(_baudRate, _port);
        _isOpen = true;
    }
    /**
     * Stop the comunication between the roborio and the arduino
     */
    public void Close()
    {
        if(!_isOpen)
            return;

        _serial.close();
        _isOpen = false;
    }
}
