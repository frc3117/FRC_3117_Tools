package frc.robot.Library.FRC_3117_Tools.Physics;

import java.util.List;

import frc.robot.Library.FRC_3117_Tools.Math.Quaternion;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class Transform 
{
    public Transform()
    {
        this(new Vector3d(), new Quaternion(), null);
    }
    public Transform(Transform parent)
    {
        this(new Vector3d(), new Quaternion(), parent);
    }
    public Transform(Vector3d position)
    {
        this(position, new Quaternion(), null);
    }
    public Transform(Vector3d position, Transform parent)
    {
        this(position, new Quaternion(), parent);
    }
    public Transform(Vector3d position, Vector3d rotation)
    {
        this(position, rotation, null);
    }
    public Transform(Vector3d position, Quaternion rotation)
    {
        this(position, rotation, null);
    }
    public Transform(Vector3d position, Vector3d rotation, Transform parent)
    {
        this(position, Quaternion.FromEuler(rotation), parent);
    }
    public Transform(Vector3d position, Quaternion rotation, Transform parent)
    {
        Parent = parent;

        LocalPosition = position;
        LocalRotation = rotation;
        LocalEulerAngles = rotation.Euler();

        if (parent != null)
        {
            //TODO: Implement
        }
    }

    public Transform Parent;
    public List<Transform> Childs;

    public Vector3d Position;
    public Vector3d LocalPosition;

    public Quaternion Rotation;
    public Quaternion LocalRotation;

    public Vector3d EulerAngles;
    public Vector3d LocalEulerAngles;

    private TransformationMatrix _localToWorldMatrix;
    private TransformationMatrix _worldToLocalMatrix;

    public void SetPosition(Vector3d position)
    {
        Position = position.Copy();

        if (Parent == null)
        {
            LocalPosition = Position.Copy();
        }
        else
        {

        }
    }
    public void SetPositionLocal(Vector3d localPosition)
    {
        LocalPosition = localPosition.Copy();

        if (Parent == null)
        {
            Position = LocalPosition.Copy();
        }
        else
        {

        }
    }

    public void SetRotation(Quaternion rotation)
    {
        Rotation = rotation.Copy();
        EulerAngles = rotation.Euler();

        if (Parent == null)
        {
            LocalRotation = Rotation.Copy();
            LocalEulerAngles = EulerAngles.Copy();
        }
        else
        {

        }
    }
    public void SetRotationLocal(Quaternion localRotation)
    {
        LocalRotation = localRotation.Copy();
        LocalEulerAngles = localRotation.Euler();

        if (Parent == null)
        {
            Rotation = LocalRotation.Copy();
            EulerAngles = LocalEulerAngles.Copy();
        }
        else
        {

        }
    }

    public void SetEulerAngles(Vector3d eulerAngles)
    {
        Rotation = Quaternion.FromEuler(eulerAngles);
        EulerAngles = eulerAngles.Copy();

        if (Parent == null)
        {
            LocalRotation = Rotation.Copy();
            LocalEulerAngles = EulerAngles.Copy();
        }
        else
        {

        }
    }
    public void SetEulerAnglesLocal(Vector3d localEulerAngles)
    {
        LocalRotation = Quaternion.FromEuler(localEulerAngles);
        LocalEulerAngles = localEulerAngles.Copy();

        if (Parent == null)
        {
            Rotation = LocalRotation.Copy();
            EulerAngles = LocalEulerAngles.Copy();
        }
        else
        {

        }
    }
}
