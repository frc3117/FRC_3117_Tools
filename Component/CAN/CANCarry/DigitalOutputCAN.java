package frc.robot.Library.FRC_3117_Tools.Component.CAN.CANCarry;

public class DigitalOutputCAN 
{
    public DigitalOutputCAN(CANCarry canCarry, int sensorId)
    {
        _canCarry = canCarry;
        _sensorId = sensorId;
    }

    private CANCarry _canCarry;
    private int _sensorId;

    public void SetValue(boolean value)
    {
        
    }
}
