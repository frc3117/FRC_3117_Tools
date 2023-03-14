package frc.robot.Library.FRC_3117_Tools.Math;

import frc.robot.Library.FRC_3117_Tools.Math.Enum.RotationOrder;

public class Quaternion 
{
    public Quaternion()
    {
        this(0, 0, 0, 1);
    }
    public Quaternion(double x, double y, double z, double w)
    {
        W = w;
        X = x;
        Y = y;
        Z = z;
    }

    private static final Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);

    public double X;
    public double Y;
    public double Z;
    public double W;

    public Vector3d Rotate(Vector3d vec) {
        var x = X * 2;
        var y = Y * 2;
        var z = Z * 2;
        var xx = X * x;
        var yy = Y * y;
        var zz = Z * z;
        var xy = X * y;
        var xz = X * z;
        var yz = Y * z;
        var wx = W * x;
        var wy = W * y;
        var wz = W * z;

        return new Vector3d(
                (1f - (yy + zz)) * vec.X + (xy - wz) * vec.Y + (xz + wy) * vec.Z,
                (xy + wz) * vec.X + (1f - (xx + zz)) * vec.Y + (yz - wx) * vec.Z,
                (xz - wy) * vec.X + (yz + wx) * vec.Y + (1f - (xx + yy)) * vec.Z
        );
    }

    public Vector3d Euler()
    {
        return Quaternion.ToEuler(this);
    }
    public Vector3d Euler(RotationOrder order)
    {
        return Quaternion.ToEuler(this, order);
    }

    public Quaternion Copy()
    {
        return new Quaternion(X, Y, Z, W);
    }

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

    public static Quaternion AngleAxis(double angle, Vector3d axis) {
        var s = Math.sin(angle / 2);

        return new Quaternion(
                axis.X * s,
                axis.Y * s,
                axis.Z * s,
                Math.cos(angle / 2)
        );
    }

    public static Vector3d ToEuler(Quaternion quaternion)
    {
        return ToEuler(quaternion, RotationOrder.ZYX);
    }
    public static Vector3d ToEuler(Quaternion quaternion, RotationOrder order)
    {
        switch(order)
        {
            case ZYX:
                return ToEulerZYX(quaternion);

            case ZXY:
                return new Vector3d();

            default:
                return new Vector3d();
        }
    }
    private static Vector3d ToEulerZYX(Quaternion quaternion)
    {
        var y0 = 2 * ((quaternion.W * quaternion.X) + (quaternion.Y * quaternion.Z));
        var x0 = 1 - 2 * ((quaternion.X * quaternion.X) + (quaternion.Y * quaternion.Y));

        var y1 = Math.sqrt(1 + 2 * ((quaternion.W * quaternion.Y) - (quaternion.X * quaternion.Z)));
        var x1 = Math.sqrt(1 - 2 * ((quaternion.W * quaternion.Y) - (quaternion.X * quaternion.Z)));

        var y2 = 2 * ((quaternion.W * quaternion.Z) + (quaternion.X * quaternion.Y));
        var x2 = 1 - 2 * ((quaternion.Y * quaternion.Y) + (quaternion.Z * quaternion.Z));

        var euler = new Vector3d();
        euler.X = Math.atan2(y0, x0);
        euler.Y = 2 * Math.atan2(y1, x1) - (Math.PI / 2);
        euler.Z = Math.atan2(y2, x2);

        return euler;
    }

    public static Quaternion FromEuler(Vector3d euler)
    {
        return FromEuler(euler, RotationOrder.ZYX);
    }
    public static Quaternion FromEuler(Vector3d euler, RotationOrder order) //ZYX
    {
        switch(order)
        {
            case ZYX:
                return FromEulerZYX(euler);

            case ZXY:
                return new Quaternion();

            default:
                return new Quaternion();
        }
    }
    private static Quaternion FromEulerZYX(Vector3d euler)
    {
        var sinEuler = new Vector3d();
        sinEuler.X = Math.sin(euler.X / 2);
        sinEuler.Y = Math.sin(euler.Y / 2);
        sinEuler.Z = Math.sin(euler.Z / 2);

        var cosEuler = new Vector3d();
        cosEuler.X = Math.cos(euler.X / 2);
        cosEuler.Y = Math.cos(euler.Y / 2);
        cosEuler.Z = Math.cos(euler.Z / 2);

        var quaternion = new Quaternion();
        quaternion.W = (cosEuler.X * cosEuler.Y * cosEuler.Z) + (sinEuler.X * sinEuler.Y * sinEuler.Z);
        quaternion.X = (sinEuler.X * cosEuler.Y * cosEuler.Z) - (cosEuler.X * sinEuler.Y * sinEuler.Z);
        quaternion.Y = (cosEuler.X * sinEuler.Y * cosEuler.Z) + (sinEuler.X * cosEuler.Y * sinEuler.Z);
        quaternion.Z = (cosEuler.X * cosEuler.Y * sinEuler.Z) - (sinEuler.X * sinEuler.Y * cosEuler.Z);
    
        return quaternion;
    }

    public static Quaternion Identity()
    {
        return Quaternion.IDENTITY.Copy();
    }
}
