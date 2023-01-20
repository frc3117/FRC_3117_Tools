package frc.robot.Library.FRC_3117_Tools.Math;

public class Vector2d 
{
    public Vector2d()
    {
        this(0, 0);
    }
    public Vector2d(double x)
    {
        this(x, 0);
    }
    public Vector2d(double x, double y)
    {
        X = x;
        Y = y;
    }

    private static final Vector2d ZERO = new Vector2d(0, 0);
    private static final Vector2d ONE = new Vector2d(1, 1);
    private static final Vector2d MINUS = new Vector2d(-1, -1);
    private static final Vector2d RIGHT = new Vector2d(1, 0);
    private static final Vector2d LEFT = new Vector2d(-1, 0);
    private static final Vector2d FRONT = new Vector2d(0, 1);
    private static final Vector2d BACK = new Vector2d(0, -1);

    public double X;
    public double Y;

    public void Add(Vector2d vec)
    {
        X += vec.X;
        Y += vec.Y;
    }
    public Vector2d Sum(Vector2d vec)
    {
        var copy = Copy();
        copy.Add(vec);

        return copy;
    }

    public void Sub(Vector2d vec)
    {
        X -= vec.X;
        Y -= vec.Y;
    }
    public Vector2d Diff(Vector2d vec)
    {
        var copy = Copy();
        copy.Sub(vec);

        return copy;
    }

    public double SqrMagnitude()
    {
        return Math.pow(X, 2) + Math.pow(Y, 2);
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
    }
    public Vector2d Normalized()
    {
        var copy = Copy();
        copy.Normalize();

        return copy;
    }

    public double Distance(Vector2d vec)
    {
        return Diff(vec).Magnitude();
    }

    public Vector3d Vector3d()
    {
        return new Vector3d(X, Y);
    }

    public Vector2d Copy()
    {
        return new Vector2d(X, Y);
    }

    public static Vector3d ToVector3d(Vector2d vec)
    {
        return new Vector3d(vec.X, vec.Y);
    }

    public static Vector2d FromVector3d(Vector3d vec)
    {
        return new Vector2d(vec.X, vec.Y);
    }

    public static Vector2d Zero()
    {   
        return ZERO.Copy();
    }
    public static Vector2d One()
    {
        return ONE.Copy();
    }
    public static Vector2d Minus()
    {
        return MINUS.Copy();
    }
    public static Vector2d Right()
    {
        return RIGHT.Copy();
    }
    public static Vector2d Left()
    {
        return LEFT.Copy();
    }
    public static Vector2d Front()
    {
        return FRONT.Copy();
    }
    public static Vector2d Back()
    {
        return BACK.Copy();
    }
}
