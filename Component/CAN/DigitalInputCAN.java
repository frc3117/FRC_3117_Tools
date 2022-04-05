package frc.robot.Library.FRC_3117_Tools.Component.CAN;

public class DigitalInputCAN
{
    public DigitalInputCAN(MultiDigitalInputCAN digitalInputs, int Id) 
    {
        _digitalInputs = digitalInputs;
        _id = Id;
    }

    private MultiDigitalInputCAN _digitalInputs;
    private int _id;
    private boolean _reversed;

    public boolean GetValue()
    {
        return _digitalInputs.GetValue(_id) != _reversed;
    }

    public DigitalInputCAN SetReversed(boolean reversed)
    {
        _reversed = reversed;
        
        return this;
    }
}
