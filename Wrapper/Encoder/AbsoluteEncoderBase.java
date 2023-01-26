package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder;

import frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.Interface.AbsoluteEncoder;

public abstract class AbsoluteEncoderBase implements AbsoluteEncoder
{
    private double _offset;

    @Override
    public void Zero()
    {
        _offset = GetRawValue();
    }
    @Override
    public double GetOffset()
    {
        return _offset;
    }
    @Override
    public void SetOffset(double offset)
    {
        _offset = offset;
    }

    @Override
    public abstract double GetRawValue();
    @Override
    public double GetValue()
    {
        var value = GetRawValue() - _offset;

        if (value >= 1)
            value -= 1;
        else if (value < 0)
            value += 1;

        return value;
    }

    @Override
    public double GetRawAngle()
    {
        return GetRawValue() * 2 * Math.PI;
    }
    @Override
    public double GetAngle()
    {
        return GetValue() * 2 * Math.PI;
    }
}
