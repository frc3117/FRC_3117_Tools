package frc.robot.Library.FRC_3117_Tools.Math;

import frc.robot.Library.FRC_3117_Tools.Physics.TranslationMatrix;

public class Vector2d 
{
    public Vector2d()
    {
        this(0, 0);
    }
    public Vector2d(Vector3d vec)
    {
        this(vec.X, vec.Y);
    }
    public Vector2d(TranslationMatrix translation)
    {
        this(translation.GetX(), translation.GetY());
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

    public void Scale(double scale)
    {
        X *= scale;
        Y *= scale;
    }
    public Vector2d Scaled(double scale)
    {
        var copy = Copy();
        copy.Scale(scale);

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

    public void Rotate(double angle)
    {
        var cs = Math.cos(angle);
        var sn = Math.sin(angle);

        X = X * cs - Y * sn;
        Y = X * sn + Y * cs;
    }
    public Vector2d Rotated(double angle)
    {
        var copy = Copy();
        copy.Rotate(angle);

        return copy;
    }

    public Vector3d Vector3d()
    {
        return new Vector3d(this);
    }
    public TranslationMatrix TranslationMatrix()
    {
        return new TranslationMatrix(this);
    }

    public Vector2d Copy()
    {
        return new Vector2d(X, Y);
    }

    public String toString()
    {
        var builder = new StringBuilder();
        builder.append("X: ");
        builder.append(X);
        builder.append(" Y: ");
        builder.append(Y);

        return builder.toString();
    }

    public static double Dot(Vector2d v1, Vector2d v2) {
        return v1.X * v2.X + v1.Y * v2.Y;
    }
    public static Vector2d Cross(Vector2d v) {
        return new Vector2d(v.Y, v.X);
    }

    public static Vector3d ToVector3d(Vector2d vec)
    {
        return new Vector3d(vec.X, vec.Y);
    }
    public static Vector2d FromVector3d(Vector3d vec)
    {
        return new Vector2d(vec.X, vec.Y);
    }

    public static TranslationMatrix ToTranslationMatrix(Vector2d vec)
    {
        return new TranslationMatrix(vec);
    }
    public static Vector2d FromTranslationMatrix(TranslationMatrix translation)
    {
        return new Vector2d(translation);
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
