package frc.robot.Component.Data;

import edu.wpi.first.wpilibj.drive.Vector2d;

public class WheelData
{
    public WheelData() {}

    public WheelData(int driveChannel, int directionChannel, Vector2d driveEncoder, int directionEncoderChannel, int shifterChannel, Vector2d wheelPosition, double angleOffset)
    {
        DriveChannel = driveChannel;
        DirectionChannel = directionChannel;
        DriveEncoderA = (int)driveEncoder.x;
        DriveEncoderB = (int)driveEncoder.y;
        DirectionEncoderChannel = directionEncoderChannel;
        ShifterChannel = shifterChannel;
        WheelPosition = wheelPosition;
        AngleOffset = angleOffset;
    }

    public int DriveChannel;
    public int DirectionChannel;
    public int DriveEncoderA;
    public int DriveEncoderB;
    public int DirectionEncoderChannel;
    public int ShifterChannel;

    public Vector2d WheelPosition;

    public double AngleOffset;

    public Vector2d GetWheelRotationVector()
    {
        //Rotation vector is a normalized normal vector from the wheel position
        Vector2d vec = new Vector2d(-WheelPosition.y, WheelPosition.x);
        double mag = vec.magnitude();

        vec.x /= mag;
        vec.y /= mag;

        return vec;
    }
}