package frc.robot.Library.FRC_3117_Tools.Math;

public class Vector2d 
{
    public Vector2d()
    {
        X = 0;
        Y = 0;
    }
    public Vector2d(double x, double y)
    {
        X = x;
        Y = y;
    }

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

    public double Magnitude()
    {
        return Math.sqrt(SqrMagnitude());
    }
    public double SqrMagnitude()
    {
        return Math.pow(X, 2) + Math.pow(Y, 2);
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

    public Vector2d Copy()
    {
        return new Vector2d(X, Y);
    }
}
