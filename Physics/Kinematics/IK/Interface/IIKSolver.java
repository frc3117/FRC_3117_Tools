package frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.IK.Interface;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;
import frc.robot.Library.FRC_3117_Tools.Math.Matrix4X4;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.DHParameters;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.KinematicChain;

public interface IIKSolver 
{
    public Pair<Vector3d[], double[]> Solve(DHParameters[] params, Vector3d target);
}
