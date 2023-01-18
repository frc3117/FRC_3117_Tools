package frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve;

import frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve.Data.SwerveModuleData;

public class SwerveModule 
{
    public SwerveModuleData Data;

    private double _targetAngle;
    private double _targetSpeed;

    public void SetDirection(double angle)
    {
        _targetAngle = angle;
    }
    public void SetSpeed(double speed)
    {
        _targetSpeed = speed;
    }

    public void DoModule()
    {
        
    }
}
