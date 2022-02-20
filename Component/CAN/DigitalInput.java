package frc.robot.Library.FRC_3117_Tools.Component.CAN;

import edu.wpi.first.hal.CANData;

public class DigitalInput extends CANDevice
{
    public DigitalInput(int deviceID) 
    {
        super(deviceID);
    }
    
    private boolean _currentValue;

    public boolean GetValue()
    {
        var data = new CANData();
        if (readPacketNew(0, data))
        {
            _currentValue = data.data[0] == 1;
        }

        return _currentValue;
    }
}
