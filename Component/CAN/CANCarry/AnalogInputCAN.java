package frc.robot.Library.FRC_3117_Tools.Component.CAN.CANCarry;

public class AnalogInputCAN 
{
    public AnalogInputCAN(CANCarry canCarry, int sensorId)
    {
        _canCarry = canCarry;
        _sensorId = sensorId;
    }

    private CANCarry _canCarry;
    private int _sensorId;

    public int GetValue()
    {
        return 0;
    }
}
