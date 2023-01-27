package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder;

import edu.wpi.first.wpilibj.DutyCycleEncoder;

public class DutyCycleAbsoluteEncoder extends AbsoluteEncoderBase
{
    public DutyCycleAbsoluteEncoder(int channel)
    {
        this(channel, 0);
    }
    public DutyCycleAbsoluteEncoder(int channel, double offset)
    {
        _encoder = new DutyCycleEncoder(channel);
        SetOffset(offset);
    }

    private DutyCycleEncoder _encoder;

    @Override
    public double GetRawValue() 
    {
        return _encoder.getAbsolutePosition();
    }
}
