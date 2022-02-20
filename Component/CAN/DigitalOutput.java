package frc.robot.Library.FRC_3117_Tools.Component.CAN;

public class DigitalOutput extends CANDevice
{
    public DigitalOutput(int deviceID) 
    {
        super(deviceID);
    }
    
    private boolean _currentValue;

    public boolean GetValue()
    {
        return _currentValue;
    }
    public void SetValue()
    {
        var data = new byte[8];
        data[0] = _currentValue ? (byte)1 : (byte)0;

        writePacket(data, 0);
    }
}
