package frc.robot.Library.FRC_3117_Tools.Physics;

import java.util.List;

import frc.robot.Library.FRC_3117_Tools.Math.Quaternion;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class Transform 
{
    public Transform()
    {
        this(null);
    }
    public Transform(Transform parent)
    {
        Parent = parent;
    }

    public Transform Parent;
    public List<Transform> Childs;

    public Vector3d Position;
    public Vector3d LocalPosition;

    public Quaternion Rotation;
    public Quaternion LocalRotation;

    public Vector3d EulerAngles;
    public Vector3d LocalEulerAngles;

    public void SetPosition(Vector3d position)
    {

    }
    public void SetPositionLocal(Vector3d localPosition)
    {

    }

    public void SetRotation(Quaternion rotation)
    {

    }
    public void SetRotationLocal(Quaternion localRotation)
    {

    }

    public void SetEulerAngles(Vector3d eulerAngles)
    {

    }
    public void SetEulerAnglesLocal(Vector3d localEulerAngles)
    {

    }
}
