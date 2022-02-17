package frc.robot.Library.FRC_3117_Tools.Component.Data;

import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;

public class WheelData
{
    public WheelData() {}

    public WheelData(MotorController driveController, MotorController directionController, Pair<Integer, Integer> driveEncoder, int directionEncoderChannel, Vector2d wheelPosition, double angleOffset)
    {
        DriveController = driveController;
        DirectionController = directionController;
        DriveEncoderA = driveEncoder.Item1;
        DriveEncoderB = driveEncoder.Item2;
        DirectionEncoderChannel = directionEncoderChannel;
        WheelPosition = wheelPosition;
        AngleOffset = angleOffset;
    }
    
    public MotorController DriveController;
    public MotorController DirectionController;
    public int DriveEncoderA;
    public int DriveEncoderB;
    public int DirectionEncoderChannel;

    public Vector2d WheelPosition;

    public double AngleOffset;

    public Vector2d GetWheelRotationVector()
    {
        //Rotation vector is a normalized normal vector from the wheel position
        var vec = new Vector2d(-WheelPosition.y, WheelPosition.x);
        var mag = vec.magnitude();

        vec.x /= mag;
        vec.y /= mag;

        return vec;
    }
}