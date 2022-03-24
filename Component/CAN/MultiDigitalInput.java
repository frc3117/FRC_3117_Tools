package frc.robot.Library.FRC_3117_Tools.Component.CAN;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.hal.CANData;

public class MultiDigitalInput extends CANDevice
{
    public MultiDigitalInput(int deviceID) 
    {
        super(deviceID);
    }
    
    private long _value = 0;

    public boolean GetValue(int digitalInputID)
    {
        var data = new CANData();
        if (readPacketNew(0, data))
        {
            var bb = ByteBuffer.wrap(data.data);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            _value = bb.getLong();
        }

        return (_value << digitalInputID) < 0;
    }
}
