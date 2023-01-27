package frc.robot.Library.FRC_3117_Tools.Component;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.Library.FRC_3117_Tools.Math.Mathf;
import frc.robot.Library.FRC_3117_Tools.Math.Range;
import frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.Interface.AbsoluteEncoder;

public class ServoController 
{
    public ServoController(MotorController controller, AbsoluteEncoder encoder)
    {
        this(controller, encoder, null);
    }
    public ServoController(MotorController controller, AbsoluteEncoder encoder, Range range)
    {
        Controller = controller;
        Encoder = encoder;
        Range = range;

        if (range == null)
        {
            Continuous = false;
        }
        else
            Continuous = true;
    }

    public MotorController Controller;
    public AbsoluteEncoder Encoder;

    public boolean Continuous;
    public Range Range;

    public void Set(double percentage)
    {

    }
    public void SetAngle(double targetAngle)
    {
        var currentAngle = Encoder.GetAngle();
        var error = 0.;

        if (Continuous)
        {
            error = Mathf.DeltaAngle(currentAngle, targetAngle);
        }
        else
        {
            targetAngle = Mathf.Clamp(targetAngle, Range.Min, Range.Max);
            error = Encoder.GetAngle() - targetAngle;
        }
    }
}
