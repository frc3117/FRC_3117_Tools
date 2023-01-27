package frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve.Data;

import frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve.SwerveModule;

public class SwerveDriveData 
{
    public SwerveModule[] Modules;
    
    public void SetModules(SwerveModule... modules)
    {
        Modules = modules;
    }
}
