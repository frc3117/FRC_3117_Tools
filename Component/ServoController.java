package frc.robot.Library.FRC_3117_Tools.Component;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.Library.FRC_3117_Tools.Interface.BaseController;
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

    public BaseController Control;

    private double _targetAngle;

    public void SetControl(BaseController control)
    {
        Control = control;
    }

    public void Set(double percentage)
    {
        SetAngle(Mathf.Lerp(percentage, Range.Min, Range.Max));
    }
    public void SetAngle(double targetAngle)
    {
        _targetAngle = targetAngle;
    }

    public void DoControlLoop()
    {
        var currentAngle = Encoder.GetAngle();
        var error = 0.;

        if (Continuous)
        {
            error = Mathf.DeltaAngle(currentAngle, _targetAngle);
        }
        else
        {
            _targetAngle = Mathf.Clamp(_targetAngle, Range.Min, Range.Max);
            error = _targetAngle - Encoder.GetAngle();
        }

        Controller.set(Control.Evaluate(error));
    }
}
