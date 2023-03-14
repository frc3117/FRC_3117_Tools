package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder;

import frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.Interface.AbsoluteEncoder;

public class DoubleEncoder extends AbsoluteEncoderBase
{
    public DoubleEncoder(AbsoluteEncoder primaryEncoder, AbsoluteEncoder secondaryEncoder, double ratio) {
        _primaryEncoder = primaryEncoder;
        _secondaryEncoder = secondaryEncoder;
        _ratio = ratio;
    }

    private final AbsoluteEncoder _primaryEncoder;
    private final AbsoluteEncoder _secondaryEncoder;
    private double _ratio;

    @Override
    public boolean IsConnected() {
        return _primaryEncoder.IsConnected() && _secondaryEncoder.IsConnected();
    }

    @Override
    public double GetRawValue() {
        var p = _primaryEncoder.GetRawValue();
        var s = _secondaryEncoder.GetRawValue() * _ratio;

        s = Math.round(s - p);

        return p + s;
    }

    @Override
    public double GetRawTotalValue() {
        var p = _primaryEncoder.GetRawTotalValue();
        var s = _secondaryEncoder.GetRawTotalValue() * _ratio;

        s = Math.round(s - p);

        return p + s;
    }
}
