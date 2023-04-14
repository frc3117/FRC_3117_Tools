package frc.robot.Library.FRC_3117_Tools.Physics.Control;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Library.FRC_3117_Tools.Manifest.RobotManifestObject;
import frc.robot.Library.FRC_3117_Tools.Physics.Control.Interface.ControllerBase;

public class PID extends ControllerBase
{
    public PID(String name) {
        this(name, 0, 0, 0);
    }
    public PID(String name, double kp, double ki, double kd) {
        super(name);

        Kp = kp;
        Ki = ki;
        Kd = kd;

        SmartDashboard.putData(name, this);
    }

    public static ControllerBase CreateFromManifest(String name, RobotManifestObject entry) {
        var kp = entry.GetDouble("kp");
        var ki = entry.GetDouble("ki");
        var kd = entry.GetDouble("kd");

        return new PID(name, kp, ki, kd);
    }

    public double Kp;
    public double Ki;
    public double Kd;

    private double _proportional;
    private double _integral;
    private double _derivative;

    private double _previousProportional;

    @Override
    public double EvaluateError(double error, double deltaTime) {
        _proportional = error;

        _derivative = (_previousProportional - _proportional) / deltaTime;
        _integral += _proportional * deltaTime;

        _previousProportional = _proportional;

        return Kp * _proportional +
                Ki * _integral +
                Kd * _derivative;
    }

    @Override
    public void Reset() {
        _integral = 0;
    }

    @Override
    public void initSendable(SendableBuilder builder)
    {
        builder.addDoubleProperty("Kp", () -> Kp, (val) -> Kp = val);
        builder.addDoubleProperty("Ki", () -> Ki, (val) -> Ki = val);
        builder.addDoubleProperty("Kd", () -> Kd, (val) -> Kd = val);

        builder.addDoubleProperty("Proportional", () -> _proportional, null);
        builder.addDoubleProperty("Integral", () -> _integral, null);
        builder.addDoubleProperty("Derivative", () -> _derivative, null);
    }
}
