package frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve;

import frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve.Data.SwerveModuleData;
import frc.robot.Library.FRC_3117_Tools.Math.Vector2d;

public class SwerveModule
{
    public SwerveModuleData Data;

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

    public void DoModule(Vector2d translation, double rotation)
    {
        
    }
}
