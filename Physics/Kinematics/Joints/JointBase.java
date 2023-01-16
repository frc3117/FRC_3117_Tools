package frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.Joints;

import frc.robot.Library.FRC_3117_Tools.Physics.RigidBody;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.Joints.Interface.IJoint;

public class JointBase implements IJoint
{
    public RigidBody CurrentBody;
    public RigidBody ConnectedBody;

    public double GetLength()
    {
        return CurrentBody.Transform.Position.Distance(ConnectedBody.Transform.Position);
    }
}
