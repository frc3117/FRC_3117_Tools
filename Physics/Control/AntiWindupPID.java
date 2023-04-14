package frc.robot.Library.FRC_3117_Tools.Physics.Control;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Library.FRC_3117_Tools.Manifest.RobotManifestObject;
import frc.robot.Library.FRC_3117_Tools.Math.Mathf;
import frc.robot.Library.FRC_3117_Tools.Math.Range;
import frc.robot.Library.FRC_3117_Tools.Physics.Control.Interface.ControllerBase;

public class AntiWindupPID extends ControllerBase {

    public AntiWindupPID(String name) {
        this(name, 0, 0, 0);
    }
    public AntiWindupPID(String name, double kp, double ki, double kd) {
        super(name);

        Kp = kp;
        Ki = ki;
        Kd = kd;

        _integralRange = new Range(-99999, -99999);

        SmartDashboard.putData(name, this);
    }

    public static ControllerBase CreateFromManifest(String name, RobotManifestObject entry) {
        var kp = entry.GetDouble("kp");
        var ki = entry.GetDouble("ki");
        var kd = entry.GetDouble("kd");

        var resetOnFlip = entry.GetBoolean("resetOnFlip", false);
        var min = entry.GetDouble("minIntegral", -9999);
        var max = entry.GetDouble("maxIntegral", 9999);

        var pid = new AntiWindupPID(name, kp, ki, kd);
        pid.SetResetOnFlip(resetOnFlip);
        pid.SetRange(min, max);

        return pid;
    }

    public double Kp;
    public double Ki;
    public double Kd;

    private double _proportional;
    private double _integral;
    private double _derivative;

    private double _previousProportional;

    private boolean _resetOnSignFlip;
    private Range _integralRange;

    @Override
    public double EvaluateError(double error, double deltaTime) {
        _proportional = error;

        if (!Mathf.EpsilonEqual(Math.signum(_proportional), Math.signum(_previousProportional)))
            Reset();

        _derivative = (_previousProportional - _proportional) / deltaTime;
        _integral = _integralRange.Clamp(_integral + (_proportional * deltaTime));

        _previousProportional = _proportional;

        return Kp * _proportional +
                Ki * _integral +
                Kd * _derivative;
    }

    @Override
    public void Reset() {
        _integral = 0;
    }

    public boolean GetResetOnFlip() {
        return _resetOnSignFlip;
    }

    public void  SetResetOnFlip(boolean value) {
        _resetOnSignFlip = value;
    }

    public double GetMaxIntegral() {
        return _integralRange.Max;
    }
    public double GetMinIntegral() {
        return _integralRange.Min;
    }
    public Range GetIntegralRange() {
        return _integralRange;
    }

    public void SetMaxIntegral(double value) {
        _integralRange.Max = Math.max(value, 0);
    }
    public void SetMinIntegral(double value) {
        _integralRange.Min = Math.min(value, 0);
    }
    public void SetRange(double min, double max) {
        SetMinIntegral(min);
        SetMaxIntegral(max);
    }
    public void SetRange(Range range) {
        SetRange(range.Min, range.Max);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("Kp", () -> Kp, (val) -> Kp = val);
        builder.addDoubleProperty("Ki", () -> Ki, (val) -> Ki = val);
        builder.addDoubleProperty("Kd", () -> Kd, (val) -> Kd = val);

        builder.addBooleanProperty("ResetOnFlip", this::GetResetOnFlip, this::SetResetOnFlip);
        builder.addDoubleProperty("MinIntegral", this::GetMinIntegral, this::SetMinIntegral);
        builder.addDoubleProperty("MaxIntegral", this::GetMaxIntegral, this::SetMaxIntegral);

        builder.addDoubleProperty("Proportional", () -> _proportional, null);
        builder.addDoubleProperty("Integral", () -> _integral, null);
        builder.addDoubleProperty("Derivative", () -> _derivative, null);
    }
}
