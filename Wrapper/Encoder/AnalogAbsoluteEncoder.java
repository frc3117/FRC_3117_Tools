package frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;

public class AnalogAbsoluteEncoder extends AbsoluteEncoderBase
{
    public AnalogAbsoluteEncoder(int channel)
    {
        _encoder = new AnalogInput(channel);
    }

    private AnalogInput _encoder;

    @Override
    public boolean IsConnected() {
        return true;
    }

    @Override
    public double GetRawValue() 
    {
        return _encoder.getVoltage() / RobotController.getVoltage5V();
    }
}
