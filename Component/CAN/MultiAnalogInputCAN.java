package frc.robot.Library.FRC_3117_Tools.Component.CAN;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.hal.CANData;

public class MultiAnalogInputCAN extends CANDevice
{
    public MultiAnalogInputCAN(int deviceID) 
    {
        super(deviceID);
    }
    
    private double[] _values = new double[64];

    public AnalogInputCAN GetAnalogInput(int analogInputID)
    {
        return new AnalogInputCAN(this, analogInputID);
    }

    public double GetValue(int analogInputID)
    {
        var data = new CANData();
        if (readPacketNew(analogInputID, data))
        {
            var bb = ByteBuffer.wrap(data.data);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            _values[analogInputID] = bb.getDouble();
        }

        return _values[analogInputID];
    }
}
