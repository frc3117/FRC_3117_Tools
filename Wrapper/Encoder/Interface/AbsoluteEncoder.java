package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.Interface;

public interface AbsoluteEncoder 
{
    public void Zero();

    public double GetOffset();
    public void SetOffset(double offset);

    public double GetRawValue();
    public double GetValue();

    public double GetRawAngle();
    public double GetAngle();
}