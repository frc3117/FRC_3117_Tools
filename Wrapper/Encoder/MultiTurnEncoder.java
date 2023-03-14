package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder;

import edu.wpi.first.wpilibj.AnalogEncoder;
import frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.Interface.AbsoluteEncoder;

public class MultiTurnEncoder extends AbsoluteEncoderBase
{
    public MultiTurnEncoder(AbsoluteEncoder encoderSource, int turnCount) {
        _encoderSource = encoderSource;
        _turnCount = turnCount;
    }

    private final AbsoluteEncoder _encoderSource;
    private int _turnCount;

    @Override
    public boolean IsConnected() {
        return _encoderSource.IsConnected();
    }

    @Override
    public double GetRawValue() {
        return _encoderSource.GetRawValue() / _turnCount;
    }

    @Override
    public double GetRawTotalValue() {
        return _encoderSource.GetRawTotalValue() / _turnCount;
    }
}
