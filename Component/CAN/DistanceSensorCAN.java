package frc.robot.Library.FRC_3117_Tools.Component.CAN;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import edu.wpi.first.hal.CANData;

public class DistanceSensorCAN extends CANDevice 
{
    public DistanceSensorCAN(int deviceID) 
    {
        super(deviceID);
    }

    private double _currentDistance;

    public double GetDistance()
    {
        var data = new CANData();
        if (readPacketNew(0, data))
        {
            var bb = ByteBuffer.wrap(data.data);
            bb.order(ByteOrder.LITTLE_ENDIAN);

            _currentDistance = bb.getDouble();
        }

        return _currentDistance;
    }
}
