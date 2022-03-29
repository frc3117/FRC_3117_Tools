package frc.robot.Library.FRC_3117_Tools.Component.CAN;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.hal.CANData;

public class MultiAnalogInputCAN extends CANDevice
{
    public MultiAnalogInputCAN(int deviceID, int resolution) 
    {
        super(deviceID);
        _resolution = resolution;
    }
    
    private int _resolution;
    private int[] _values = new int[64];

    public int GetResolution()
    {
        return _resolution;
    }

    public AnalogInputCAN GetAnalogInput(int analogInputID)
    {
        return new AnalogInputCAN(this, analogInputID);
    }
    
    public int GetValue(int analogInputID)
    {
        var data = new CANData();
        if (readPacketNew(analogInputID, data))
        {
            var bb = ByteBuffer.wrap(data.data);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            _values[analogInputID] = bb.getInt() + (bb.getInt() * _resolution);
        }

        return _values[analogInputID];
    }
}
