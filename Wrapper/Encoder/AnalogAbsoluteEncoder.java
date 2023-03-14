package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder;

import edu.wpi.first.wpilibj.*;

public class AnalogAbsoluteEncoder extends AbsoluteEncoderBase
{
    public AnalogAbsoluteEncoder(int channel) {
        _encoder = new AnalogInput(channel);
        _counter = null;//new Counter(new AnalogTrigger(_encoder));
    }

    private final AnalogInput _encoder;
    private final Counter _counter;

    @Override
    public boolean IsConnected() {
        return true;
    }

    @Override
    public double GetRawValue() {
        return _encoder.getVoltage() / RobotController.getVoltage5V();
    }

    @Override
    public double GetRawTotalValue() {
        return GetRawValue() + _counter.get();
    }
}
