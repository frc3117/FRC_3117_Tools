package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.Interface;

public interface AbsoluteEncoder 
{
    public boolean IsConnected();

    public void Zero();

    public double GetOffset();
    public void SetOffset(double offset);

    public boolean GetInverted();
    public void SetInverted(boolean inverted);

    public double GetRawValue();
    public double GetValue();

    public double GetRawAngle();
    public double GetAngle();

    public double GetRawTotalValue();
    public double GetTotalValue();

    public double GetRawTotalAngle();
    public double GetTotalAngle();
}