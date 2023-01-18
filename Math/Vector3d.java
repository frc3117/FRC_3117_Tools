package frc.robot.Library.FRC_3117_Tools.Math;

public class Vector3d 
{
    public Vector3d()
    {
        X = 0;
        Y = 0;
        Z = 0;
    }
    public Vector3d(double x, double y, double z)
    {
        X = x;
        Y = y;
        Z = z;
    }

    public double X;
    public double Y;
    public double Z;

    public void Add(Vector3d vec)
    {
        X += vec.X;
        Y += vec.Y;
        Z += vec.Z;
    }
    public Vector3d Sum(Vector3d vec)
    {
        var copy = Copy();
        copy.Add(vec);

        return copy;
    }

    public void Sub(Vector3d vec)
    {
        X -= vec.X;
        Y -= vec.Y;
        Z -= vec.Z;
    }
    public Vector3d Diff(Vector3d vec)
    {
        var copy = Copy();
        copy.Sub(vec);

        return copy;
    }

    public double SqrMagnitude()
    {
        return Math.pow(X, 2) + Math.pow(Y, 2) + Math.pow(Z, 2);
    }
    public double Magnitude()
    {
        return Math.sqrt(SqrMagnitude());
    }

    public void Normalize()
    {
        var mag = Magnitude();

        X /= mag;
        Y /= mag;
        Z /= mag;
    }
    public Vector3d Normalized()
    {
        var copy = Copy();
        copy.Normalize();

        return copy;
    }

    public double Distance(Vector3d vec)
    {
        return Diff(vec).Magnitude();
    }

    public Vector3d Copy()
    {
        return new Vector3d(X, Y, Z);
    }
}
