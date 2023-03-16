package frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.IK;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;
import frc.robot.Library.FRC_3117_Tools.Math.Quaternion;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.DHParameters;
import frc.robot.Library.FRC_3117_Tools.Physics.Kinematics.IK.Interface.IIKSolver;
import frc.robot.Library.FRC_3117_Tools.Physics.Pose3d;
import frc.robot.Library.FRC_3117_Tools.Physics.RotationMatrix;

public class CCDSolver implements IIKSolver
{
    public CCDSolver(int maxIteration, double tolerency) {
        MaxIteration = maxIteration;
        Tolerency = tolerency;
    }

    private final Vector3d Axis = new Vector3d(0, 0, 1);

    public int MaxIteration;
    public double Tolerency;

    public double[] Solve(Pose3d pose, double[] jointAngle, DHParameters[] params, Vector3d target)
    {
        var iteration = 0;
        while (true)
        {
            var positions = DHParameters.ForwardKinematics(pose, jointAngle, params);
            var error = positions[positions.length - 1].Distance(target);

            //System.out.println(error);

            if (error <= Tolerency || iteration >= MaxIteration)
                break;

            var matrices = DHParameters.GetTransformationMatrices(pose, jointAngle, params);
            var endEffectorPos = positions[positions.length - 1];

            for (var i = params.length - 1; i >= 0; i--)
            {
                //System.out.println(matrices[i].GetMatrix());
                var rotation = matrices[i].GetRotationMatrix();
                var previousRotation = i == 0 ? new RotationMatrix(pose.Rotation) : matrices[i - 1].GetRotationMatrix();
                //System.out.println(rotation.GetMatrix());

                var translation = matrices[i].GetTranslationMatrix();
                var translationVec = Vector3d.FromTranslationMatrix(translation);
                //System.out.println(translationVec);

                var deltaToTarget = target.Diff(translationVec);
                var deltaToEndEffector = endEffectorPos.Diff(translationVec);

                var axis = previousRotation.Rotate(Axis);
                System.out.println(axis);

                var angle = Vector3d.SignedAngle(deltaToEndEffector, deltaToTarget, axis);
                //System.out.println(angle);

                endEffectorPos = translationVec.Sum(Quaternion.AngleAxis(angle, axis).Rotate(deltaToEndEffector));
                //System.out.println(endEffectorPos);

                jointAngle[i] += angle;
                //System.out.println(jointAngle[i]);
            }

            iteration++;
        }

        return jointAngle;
    }

    @Override
    public Pair<Vector3d[], double[]> Solve(DHParameters[] params, Vector3d target) {
        return null;
    }
}
