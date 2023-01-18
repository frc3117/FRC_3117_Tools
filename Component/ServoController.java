package frc.robot.Library.FRC_3117_Tools.Component;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import frc.robot.Library.FRC_3117_Tools.Math.Range;

public class ServoController 
{
    public ServoController(MotorController controller, AnalogEncoder encoder)
    {
        this(controller, encoder, null);
    }
    public ServoController(MotorController controller, AnalogEncoder encoder, Range range)
    {
        Controller = controller;
        Encoder = encoder;
        Range = range;

        if (range == null)
        {
            range = new Range(0, 360);
        }
    }

    public MotorController Controller;
    public AnalogEncoder Encoder;

    public Range Range;

    public void Set(double percentage)
    {

    }
    public void SetAngle(double angle)
    {

    }
}
