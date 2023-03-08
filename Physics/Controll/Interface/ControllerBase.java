package frc.robot.Library.FRC_3117_Tools.Physics.Controll.Interface;

import edu.wpi.first.util.sendable.Sendable;
import frc.robot.Library.FRC_3117_Tools.Math.Timer;

import java.util.HashMap;

public abstract class ControllerBase implements Sendable
{
    public ControllerBase(String name) {
        Name = name;
    }

    public static HashMap<String, ControllerBase> Controllers = new HashMap<>();


    public final String Name;
    public double Setpoint;

    public double Evaluate(double currentValue) {
        return Evaluate(currentValue, Timer.GetDeltaTime());
    }
    public abstract double Evaluate(double currentValue, double deltaTime);
}
