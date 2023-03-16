package frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.IK;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.DHParameters;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.KinematicChain;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.IK.Interface.IIKSolver;

public class JacobianIKSolver implements IIKSolver
{
    public JacobianIKSolver(int maxIteration, double tolerency)
    {
        MaxIteration = maxIteration;
        Tolerency = tolerency;
    }

    public int MaxIteration;
    public double Tolerency;

    @Override
    public Pair<Vector3d[], double[]> Solve(DHParameters[] params, Vector3d target) {
        return null;
    }
}
