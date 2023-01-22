package frc.robot.Library.FRC_3117_Tools.Physics;

import org.ejml.simple.SimpleMatrix;

import frc.robot.Library.FRC_3117_Tools.Math.Quaternion;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class RotationMatrix 
{
    public RotationMatrix()
    {
        this(Vector3d.Zero());
    }
    public RotationMatrix(Quaternion rot)
    {
        SetQuaternion(rot);
    }
    public RotationMatrix(Vector3d vec)
    {
        SetEuler(vec);
    }
    public RotationMatrix(SimpleMatrix matrix)
    {
        _rotationMatrix = matrix;
    }

    private SimpleMatrix _rotationMatrix;

    public double GetEulerX()
    {
        return GetEuler().X;
    }
    public double GetEulerY()
    {
        return GetEuler().Y;
    }
    public double GetEulerZ()
    {
        return GetEuler().Z;
    }
    public Vector3d GetEuler()
    {
        var c00 = _rotationMatrix.get(0, 0);
        var c10 = _rotationMatrix.get(1, 0);

        var sy = Math.sqrt(c00 * c00 + c10 + c10);

        double x, y, z;
        if (sy < 1e-6)
        {
            x = Math.atan2(_rotationMatrix.get(2, 1), _rotationMatrix.get(2, 2));
            y = Math.atan2(-_rotationMatrix.get(2, 0), sy);
            z = Math.atan2(_rotationMatrix.get(1, 0), _rotationMatrix.get(0, 0));
        }
        else
        {
            x = Math.atan2(-_rotationMatrix.get(1, 2), _rotationMatrix.get(1, 1));
            y = Math.atan2(-_rotationMatrix.get(2, 0), sy);
            z = 0;
        }

        return new Vector3d(x, y, z);
    }
    
    public double GetQuaternionX()
    {
        return GetQuaternion().X;
    }
    public double GetQuaternionY()
    {
        return GetQuaternion().Y;
    }
    public double GetQuaternionZ()
    {
        return GetQuaternion().Z;
    }
    public double GetQuaternionW()
    {
        return GetQuaternion().W;
    }
    public Quaternion GetQuaternion()
    {
        return Quaternion.FromEuler(GetEuler());
    }

    public void SetEulerX(double x)
    {

    }
    public void SetEulerY(double y)
    {

    }
    public void SetEulerZ(double z)
    {

    }
    public void SetEuler(Vector3d vec)
    {
        var cosX = Math.cos(vec.X);
        var sinX = Math.sin(vec.X);

        var cosY = Math.cos(vec.Y);
        var sinY = Math.sin(vec.Y);

        var cosZ = Math.cos(vec.Z);
        var sinZ = Math.sin(vec.Z);

        var xRot = new double[][] {
            new double[] { cosX, -sinX, 0 },
            new double[] { sinX, cosX, 0},
            new double[] { 0, 0, 1}
        };
        var yRot = new double[][] {
            new double[] { cosY, 0, sinY },
            new double[] { 0, 1, 0 },
            new double[] { -sinY, 0, cosY}
        };
        var zRot = new double[][] {
            new double[] { 1, 0, 0 },
            new double[] { 0, cosZ, -sinZ },
            new double[] { 0, sinZ, cosZ }
        };

        var xRotMatrix = new SimpleMatrix(xRot);
        var yRotMatrix = new SimpleMatrix(yRot);
        var zRotMatrix = new SimpleMatrix(zRot);

        _rotationMatrix = zRotMatrix.mult(yRotMatrix).mult(xRotMatrix);
    }

    public void SetQuaternionX(double x)
    {
        var rot = GetQuaternion();
        rot.X = x;

        SetQuaternion(rot);
    }
    public void SetQuaternionY(double y)
    {
        var rot = GetQuaternion();
        rot.Y = y;

        SetQuaternion(rot);
    }
    public void SetQuaternionZ(double z)
    {
        var rot = GetQuaternion();
        rot.Z = z;
    }
    public void SetQuaternionW(double w)
    {
        var rot = GetQuaternion();
        rot.W = w;

        SetQuaternion(rot);
    }
    public void SetQuaternion(Quaternion rot)
    {
        SetEuler(rot.Euler());
    }

    public SimpleMatrix GetMatrix()
    {
        return _rotationMatrix;
    }

    public RotationMatrix Copy()
    {
        return new RotationMatrix(_rotationMatrix.copy());
    }
}
