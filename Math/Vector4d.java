package frc.robot.Library.FRC_3117_Tools.Math;

public class Vector4d
{
    public Vector4d() {
        this(0, 0, 0, 0);
    }
    public Vector4d(double x, double y, double z, double w) {
        X = x;
        Y = y;
        Z = z;
        W = w;
    }

    public double X;
    public double Y;
    public double Z;
    public double W;

    public String toString() {
        var builder = new StringBuilder();
        builder.append("X: ");
        builder.append(X);
        builder.append(" Y: ");
        builder.append(Y);
        builder.append(" Z: ");
        builder.append(Z);
        builder.append(" W: ");
        builder.append(W);

        return builder.toString();
    }

    public static double Dot(Vector4d v1, Vector4d v2) {
        return v1.X * v2.X + v1.Y * v2.Y + v1.Z * v2.Z + v1.W * v2.W;
    }
}
