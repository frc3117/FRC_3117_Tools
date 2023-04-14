package frc.robot.Library.FRC_3117_Tools.Physics.Control.Interface;

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
        return EvaluateError(Setpoint - currentValue, Timer.GetDeltaTime());
    }
    public double Evaluate(double currentValue, double deltaTime) {
        return EvaluateError(Setpoint - currentValue, deltaTime);
    }

    public double EvaluateError(double error) {
        return EvaluateError(error, Timer.GetDeltaTime());
    }
    public abstract double EvaluateError(double error, double deltaTime);

    public abstract void Reset();
}
