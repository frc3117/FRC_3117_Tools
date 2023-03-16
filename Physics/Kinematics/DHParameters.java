package frc.robot.Library.FRC_3117_Tools.Physics.Kinematics;

import frc.robot.Library.FRC_3117_Tools.Math.Matrix4X4;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;
import frc.robot.Library.FRC_3117_Tools.Physics.Pose3d;
import frc.robot.Library.FRC_3117_Tools.Physics.TransformationMatrix;
import org.ejml.simple.SimpleMatrix;

public class DHParameters {
    public DHParameters(double theta, double alpha, double r, double d) {
        Theta = theta;
        Alpha = alpha;
        R = r;
        D = d;
    }

    public double Theta;
    public double Alpha;
    public double R;
    public double D;

    private final static SimpleMatrix _zAxisMatrix = new SimpleMatrix(new double[][] {
            new double[] { 0 },
            new double[] { 0 },
            new double[] { 1 }
    });

    public SimpleMatrix GetMatrix(double angle) {
        var cosTheta = Math.cos(Theta + angle);
        var cosAlpha = Math.cos(Alpha);
        var sinTheta = Math.sin(Theta + angle);
        var sinAlpha = Math.sin(Alpha);

        return new SimpleMatrix(new double[][] {
                new double[] { cosTheta, -sinTheta*cosAlpha, sinTheta*sinAlpha, R*cosTheta },
                new double[] { sinTheta, cosTheta*cosAlpha, -cosTheta*sinAlpha, R*sinTheta },
                new double[] { 0, sinAlpha, cosAlpha, D },
                new double[] { 0, 0, 0, 1 }
        });
    }

    public static SimpleMatrix GetJacobianMatrix(Pose3d pose, double[] jointAngles, DHParameters[] params) {
        var transformMatrices = GetTransformationMatrices(pose, jointAngles, params);
        var J = new SimpleMatrix(params.length, 6);

        var endEffectorTranslation = transformMatrices[transformMatrices.length - 1].GetTranslationMatrix().GetMatrix();
        for (var i = 0; i < transformMatrices.length; i++)
        {
            var mat = transformMatrices[i];

            var rotation = mat.GetRotationMatrix().GetMatrix();
            rotation = rotation.mult(_zAxisMatrix);

            var translation = mat.GetTranslationMatrix().GetMatrix();
            translation = endEffectorTranslation.minus(translation);
            translation = rotation.elementMult(translation);

            //System.out.println(translation);
            //System.out.println(rotation);

            J.insertIntoThis(0, i, translation);
            J.insertIntoThis(3, i, rotation);
        }

        return J;
    }

    public static TransformationMatrix[] GetTransformationMatrices(Pose3d pose, double[] jointAngles, DHParameters[] params) {
        var dhMatrices = GetMatrices(jointAngles, params);
        var transformMatrices = new TransformationMatrix[dhMatrices.length];

        var mat = new TransformationMatrix(pose.Position, pose.Rotation);
        for (var i = 0; i < params.length; i++)
        {
            transformMatrices[i] = dhMatrices[i].rmult(mat);
        }

        return transformMatrices;
    }

    public static Matrix4X4[] GetMatrices(double[] jointAngles, DHParameters[] params) {
        var matrices = new Matrix4X4[params.length];
        for (var i = 0; i < params.length; i++)
            matrices[i] = new Matrix4X4(params[i].GetMatrix(jointAngles[i]));

        return matrices;
    }
    public static Vector3d[] ForwardKinematics(Pose3d startPose, double[] jointAngles, DHParameters[] params) {
        var dhMatrices = GetMatrices(jointAngles, params);
        var jointsPosition = new Vector3d[dhMatrices.length];

        var mat = new Matrix4X4(new TransformationMatrix(startPose.Position, startPose.Rotation));
        for (var i = 0; i < dhMatrices.length; i++)
        {
            mat = mat.mult(dhMatrices[i]);
            jointsPosition[i] = mat.GetPosition();
        }

        return jointsPosition;
    }
}
