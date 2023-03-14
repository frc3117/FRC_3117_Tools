package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder;

import edu.wpi.first.wpilibj.*;
import frc.robot.Robot;

public class DutyCycleAbsoluteEncoder extends AbsoluteEncoderBase
{
    public DutyCycleAbsoluteEncoder(int channel) {
        this(channel, 0);
    }
    public DutyCycleAbsoluteEncoder(int channel, double offset) {
        var dutyCycle = new DutyCycle(new DigitalInput(channel));

        _encoder = new DutyCycleEncoder(dutyCycle);

        if (Robot.isSimulation())
            _counter = null;
        else
            _counter = null;//new Counter(new AnalogTrigger(dutyCycle));

        SetOffset(offset);
    }

    private final DutyCycleEncoder _encoder;
    private final Counter _counter;

    @Override
    public boolean IsConnected() {
        return _encoder.isConnected();
    }

    @Override
    public double GetRawValue() {
        return _encoder.getAbsolutePosition();
    }

    @Override
    public double GetRawTotalValue() {
        return GetRawValue() + (_counter == null ? 0 : _counter.get());
    }
}
