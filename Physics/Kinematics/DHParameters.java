package frc.robot.Library.FRC_3117_Tools.Physics.Kinematics;

import frc.robot.Library.FRC_3117_Tools.Math.Matrix4X4;
import frc.robot.Library.FRC_3117_Tools.Math.Vector4d;

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

    public static Matrix4X4[] GetMatrices(DHParameters... params) {
        var matrices = new Matrix4X4[params.length];
        for (var i = 0; i < params.length; i++)
        {
            var p = params[i];

            var cosTheta = Math.cos(p.Theta);
            var cosAlpha = Math.cos(p.Alpha);
            var sinTheta = Math.sin(p.Theta);
            var sinAlpha = Math.sin(p.Alpha);

            var row0 = new Vector4d(cosTheta, -sinTheta*cosAlpha, sinTheta*sinAlpha, p.R*cosTheta);
            var row1 = new Vector4d(sinTheta, cosTheta*cosAlpha, -cosTheta*sinAlpha, p.R*sinTheta);
            var row2 = new Vector4d(0, sinAlpha, cosAlpha, p.D);
            var row3 = new Vector4d(0, 0, 0, 1);

            matrices[i] = new Matrix4X4(row0, row1, row2, row3);
        }

        return matrices;
    }
}
