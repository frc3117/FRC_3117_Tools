package frc.robot.Library.FRC_3117_Tools.Physics;

import frc.robot.Library.FRC_3117_Tools.Math.Quaternion;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class Pose3d {
    public Pose3d() {
        Position = Vector3d.Zero();
        Rotation = Quaternion.Identity();
    }
    public Pose3d(Vector3d position, Quaternion rotation) {
        Position = position;
        Rotation = rotation;
    }
    public Pose3d(Vector3d position, Vector3d rotation) {
        Position = position;
        Rotation = Quaternion.FromEuler(rotation);
    }

    public Vector3d Position;
    public Quaternion Rotation;
}
