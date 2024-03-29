package frc.robot.Library.FRC_3117_Tools.Component.Data;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;
import frc.robot.Library.FRC_3117_Tools.Math.Vector2d;
import frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.Interface.AbsoluteEncoder;

public class WheelData
{
    public WheelData() {}

    public WheelData(MotorController driveController, MotorController directionController, Pair<Integer, Integer> driveEncoder, AbsoluteEncoder directionEncoder, Vector2d wheelPosition, double angleOffset)
    {
        DriveController = driveController;
        DirectionController = directionController;
        DriveEncoderA = driveEncoder.Item1;
        DriveEncoderB = driveEncoder.Item2;
        DirectionEncoder = directionEncoder;
        WheelPosition = wheelPosition;
        AngleOffset = angleOffset;

        RotationVector = GetWheelRotationVector();
    }
    
    public MotorController DriveController;
    public MotorController DirectionController;
    public int DriveEncoderA;
    public int DriveEncoderB;
    public AbsoluteEncoder DirectionEncoder;

    public Vector2d WheelPosition;
    public Vector2d RotationVector;

    public double AngleOffset;

    public Vector2d GetWheelRotationVector()
    {
        return new Vector2d(WheelPosition.X, WheelPosition.Y).Normalized();

        //Rotation vector is a normalized normal vector from the wheel position
        /*var vec = new Vector2d(-WheelPosition.Y, WheelPosition.X);
        return vec.Normalized();*/
    }
}