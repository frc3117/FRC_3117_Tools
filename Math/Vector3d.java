package frc.robot.Library.FRC_3117_Tools.Math;

public class Vector3d 
{
    public Vector3d()
    {
        this(0, 0, 0);
    }
    public Vector3d(double x)
    {
        this(x, 0, 0);
    }
    public Vector3d(double x, double y)
    {
        this(x, y, 0);
    }
    public Vector3d(double x, double y, double z)
    {
        X = x;
        Y = y;
        Z = z;
    }

    public static final Vector3d ZERO = new Vector3d(0, 0, 0);
    public static final Vector3d ONE = new Vector3d(1, 1, 1);
    public static final Vector3d MINUS = new Vector3d(-1, -1, -1);
    public static final Vector3d RIGHT = new Vector3d(1, 0, 0);
    public static final Vector3d LEFT = new Vector3d(-1, 0, 0);
    public static final Vector3d FRONT = new Vector3d(0, 1, 0);
    public static final Vector3d BACK =  new Vector3d(0, -1, 0);
    public static final Vector3d UP = new Vector3d(0, 0, 1);
    public static final Vector3d DOWN = new Vector3d(0, 0, -1);

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

    public Vector2d Vector2d()
    {
        return new Vector2d(X, Y);
    }

    public Vector3d Copy()
    {
        return new Vector3d(X, Y, Z);
    }

    public static Vector2d ToVector2d(Vector3d vec)
    {
        return new Vector2d(vec.X, vec.Y);
    }

    public static Vector3d FromVector2d(Vector2d vec)
    {
        return new Vector3d(vec.X, vec.Y);
    }
}
