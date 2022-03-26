package frc.robot.Library.FRC_3117_Tools.Component.CAN;

public class AnalogInputCAN
{
    public AnalogInputCAN(MultiAnalogInputCAN analogInputs, int Id) 
    {
        _analogInputs = analogInputs;
        _id = Id;
    }

    private MultiAnalogInputCAN _analogInputs;
    private int _id;

    public double GetValue()
    {
        return _analogInputs.GetValue(_id);
    }
}