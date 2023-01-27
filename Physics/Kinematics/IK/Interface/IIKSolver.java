package frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.IK.Interface;

import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.KinematicChain;

public interface IIKSolver 
{
    public void Solve(KinematicChain chain, Vector3d target);
}
