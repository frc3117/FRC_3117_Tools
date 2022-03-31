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

    public int GetResolution()
    {
        return _analogInputs.GetResolution();
    }

    public int GetValue()
    {
        return _analogInputs.GetValue(_id);
    }

    public double GetValueDegree()
    {
        return ((double)GetValue() / GetResolution()) * 360;
    }
    public double GetValueRadian()
    {
        return ((double)GetValue() / GetResolution()) * 6.28319;
    }
}
