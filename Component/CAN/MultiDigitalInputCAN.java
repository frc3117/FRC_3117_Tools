package frc.robot.Library.FRC_3117_Tools.Component.CAN;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.hal.CANData;

public class MultiDigitalInputCAN extends CANDevice
{
    public MultiDigitalInputCAN(int deviceID) 
    {
        super(deviceID);
    }
    
    private long _value = 0;

    public DigitalInputCAN GetDigitalInput(int digitalInputID)
    {
        return GetDigitalInput(digitalInputID, false);
    }
    public DigitalInputCAN GetDigitalInput(int digitalInputID, boolean reversed)
    {
        return new DigitalInputCAN(this, digitalInputID, reversed);
    }

    public boolean GetValue(int digitalInputID)
    {
        return (GetValues() << (63 - digitalInputID)) < 0;
    }
    public long GetValues()
    {
        var data = new CANData();
        if (readPacketNew(0, data))
        {
            var bb = ByteBuffer.wrap(data.data);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            _value = bb.getLong();
        }

        return _value;
    }
}
