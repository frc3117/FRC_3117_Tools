package frc.robot.Library.FRC_3117_Tools.Component.CAN;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.hal.CANData;

public class AnalogInputCAN extends CANDevice
{
    public AnalogInputCAN(int deviceID) 
    {
        super(deviceID);
    }
    
    private double _currentValue;

    public double GetValue()
    {
        var data = new CANData();
        if (readPacketNew(0, data))
        {
            var bb = ByteBuffer.wrap(data.data);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            _currentValue = bb.getDouble();
        }

        return _currentValue;
    }
}
