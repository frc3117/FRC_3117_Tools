package frc.robot.Library.FRC_3117_Tools.Component;

import java.nio.ByteBuffer;

public class ServoCAN extends CANDevice
{
    public ServoCAN(int deviceID) 
    {
        super(deviceID);
    }
    
    private double _currentAngle;

    public double GetAngle()
    {
        return _currentAngle;
    }
    public void SetAngle(double degrees)
    {
        var bb = ByteBuffer.allocate(8);
        bb.putDouble(degrees);

        writePacket(bb.array(), 0);

        _currentAngle = degrees;
    }
}
