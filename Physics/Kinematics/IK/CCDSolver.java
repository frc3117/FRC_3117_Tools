package frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.IK;

import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.KinematicChain;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.IK.Interface.IIKSolver;

public class CCDSolver implements IIKSolver
{
    public CCDSolver(int maxIteration, double tolerency)
    {
        MaxIteration = maxIteration;
        Tolerency = tolerency;
    }

    public int MaxIteration;
    public double Tolerency;

    public void Solve(KinematicChain chain, Vector3d target)
    {

    }
}
