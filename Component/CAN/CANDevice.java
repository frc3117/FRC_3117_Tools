package frc.robot.Library.FRC_3117_Tools.Component;

import edu.wpi.first.hal.CANData;
import edu.wpi.first.wpilibj.CAN;

public class CANDevice
{
    public CANDevice(int deviceID)
    {
        _deviceID = deviceID;
        Open();
    }

    private int _deviceID;
    private CAN _can;

    public boolean readPacketNew(int apiId, CANData data)
    {
        return _can.readPacketNew(apiId, data);
    }
    public boolean readPacketLatest(int apiId, CANData data)
    {
        return _can.readPacketLatest(apiId, data);
    }
    public boolean readPacketTimeout(int apiId, int timeoutMs, CANData data)
    {
        return _can.readPacketTimeout(apiId, timeoutMs, data);
    }

    public void writePacket(byte[] data, int apiId)
    {
        _can.writePacket(data, apiId);
    }
    public void writePacketNoThrow(byte[] data, int apiId)
    {
        _can.writePacketNoThrow(data, apiId);
    }
    public void writePacketRepeating(byte[] data, int apiId, int repeatMs)
    {
        _can.writePacketRepeating(data, apiId, repeatMs);
    }
    public void writePacketRepeatingNoThrow(byte[] data, int apiId, int repeatMs)
    {
        _can.writePacketRepeatingNoThrow(data, apiId, repeatMs);
    }

    public void Open()
    {
        if (!IsOpen())
            _can = new CAN(_deviceID);
    }
    public void Close()
    {
        if (IsOpen())
        {
            _can.close();
            _can = null;
        }
    }

    public boolean IsOpen()
    {
        return _can != null;
    }
}
