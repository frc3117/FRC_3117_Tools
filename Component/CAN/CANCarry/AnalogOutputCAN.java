package frc.robot.Library.FRC_3117_Tools.Component.CAN.CANCarry;

public class AnalogOutputCAN 
{
    public AnalogOutputCAN(CANCarry canCarry, int sensorId)
    {
        _canCarry = canCarry;
        _sensorId = sensorId;
    }   
    
    private CANCarry _canCarry;
    private int _sensorId;

    public void SetValue(int value)
    {
        
    }
}
