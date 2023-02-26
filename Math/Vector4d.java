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
}
