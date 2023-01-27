package frc.robot.Library.FRC_3117_Tools.Component.CAN.CANCarry;

public class DigitalInputCAN 
{
    public DigitalInputCAN(CANCarry canCarry, int sensorId) 
    {
        _canCarry = canCarry;
        _sensorId = sensorId;
    }

    private CANCarry _canCarry;
    private int _sensorId;
    private boolean _reversed;

    public boolean GetValue()
    {
        return false;
    }

    public DigitalInputCAN SetReversed(boolean reversed)
    {
        _reversed = reversed;
        
        return this;
    }    
}
