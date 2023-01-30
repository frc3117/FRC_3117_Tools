package frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve;

import frc.robot.Library.FRC_3117_Tools.Component.Data.InputManager;
import frc.robot.Library.FRC_3117_Tools.Component.Drivetrain.Swerve.Data.SwerveDriveData;
import frc.robot.Library.FRC_3117_Tools.Interface.Component;
import frc.robot.Library.FRC_3117_Tools.Math.Mathf;
import frc.robot.Library.FRC_3117_Tools.Math.Vector2d;

public class SwerveDrive implements Component
{
    public SwerveDrive(SwerveDriveData data)
    {
        Data = data;
    }

    public SwerveDriveData Data;

    @Override
    public void Awake() 
    {

    }

    @Override
    public void Init()
    {

    }

    @Override
    public void Disabled() 
    {

    }

    @Override
    public void DoComponent() 
    {
        var translation = new Vector2d(InputManager.GetAxis("Horizontal"), InputManager.GetAxis("Vertical"));
        var rotation = InputManager.GetAxis("Rotation");

        for (var module : Data.Modules)
        {
            module.DoModule(this, translation, rotation);
        }
    }

    @Override
    public void Print() 
    {

    }

    public double GetHeadingRaw()
    {
        return Data.Gyro.getAngle() * Mathf.kDEG2RAD;
    }
    public double GetHeading()
    {
        return Mathf.OffsetAngle(GetHeadingRaw(), Data.GyroOffset);
    }
}