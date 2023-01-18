package frc.robot.Library.FRC_3117_Tools.Physics;

import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class RigidBody 
{
    public RigidBody(Transform transform)
    {
        this(transform, 1, new Vector3d());
    }
    public RigidBody(Transform transform, double mass)
    {
        this(transform, mass, new Vector3d());
    }
    public RigidBody(Transform transform, double mass, Vector3d centerOfMass)
    {
        Transform = transform;
        Mass = mass;
        CenterOfMass = centerOfMass;
    }

    public Transform Transform;
    
    public double Mass;
    public Vector3d CenterOfMass;
}
