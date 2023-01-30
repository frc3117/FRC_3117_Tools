package frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve.Data;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve.SwerveModule;

public class SwerveDriveData 
{
    public SwerveModule[] Modules;
    
    public Gyro Gyro;
    public double GyroOffset;

    public void SetModules(SwerveModule... modules)
    {
        Modules = modules;
    }
}
