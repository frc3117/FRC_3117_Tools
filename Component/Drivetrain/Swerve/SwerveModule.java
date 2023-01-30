package frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve;

import frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve.Data.SwerveModuleData;
import frc.robot.Library.FRC_3117_Tools.Math.Mathf;
import frc.robot.Library.FRC_3117_Tools.Math.Vector2d;

public class SwerveModule
{
    public SwerveModule(SwerveModuleData data)
    {
        Data = data;

        // Compute the rotation vector from the module position
        var rotationAngle = (Math.PI / 2.) - Math.atan2(data.Position.Y, data.Position.X);
        _rotationVector = new Vector2d(
            data.Position.Magnitude() * Math.cos(rotationAngle),
            data.Position.Magnitude() * Math.sin(rotationAngle)
        );
    }

    public SwerveModuleData Data;

    private Vector2d _rotationVector;
    private boolean _isDriveInverted = false;

    public double GetSteerAngle()
    {
        return Data.SteerController.Encoder.GetAngle();
    }
    public double GetDriveVelocity()
    {
        return Data.DriveController.GetEncoderVelocity();
    }

    public Vector2d GetInstantVector()
    {
        var angle = GetSteerAngle();
        var vel = GetDriveVelocity();

        return new Vector2d(
            vel * Math.cos(angle),
            vel * Math.sin(angle)
        );
    }

    public void DoModule(SwerveDrive swerve, Vector2d translation, double rotation)
    {
        // Compute the target wheel vector based on the joystick input and the module's rotation vector
        var targetWheelVector = translation.Sum(_rotationVector.Scaled(rotation));
        targetWheelVector.Normalize();

        // Compute the error vector from the target vector and the current vector
        var errorVector = targetWheelVector.Diff(GetInstantVector());

        // Extract each component from the vector
        var velocityError = errorVector.Magnitude();
        var angleError = Math.atan2(errorVector.Y, errorVector.X);

        if (_isDriveInverted)
            angleError = Mathf.OffsetAngle(angleError, Math.PI);

        if (Math.abs(angleError) >= 0.5 * Math.PI)
        {
            angleError = Mathf.OffsetAngle(angleError, Math.PI);
            _isDriveInverted = !_isDriveInverted;
        }
    }
}
