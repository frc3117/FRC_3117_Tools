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
        var r21 = _rotationMatrix.get(2, 1);
        var r22 = _rotationMatrix.get(2, 2);

        var r20 = _rotationMatrix.get(2, 0);

        var r10 = _rotationMatrix.get(1, 0);
        var r00 = _rotationMatrix.get(0, 0);

        return new Vector3d(
                Math.atan2(r21, r22),
                Math.atan2(-r20, Math.sqrt(r21*r21 + r22*r22)),
                Math.atan2(r10, r00));
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
        var cosZ = Math.cos(vec.Z);
        var sinZ = Math.sin(vec.Z);

        var cosY = Math.cos(vec.Y);
        var sinY = Math.sin(vec.Y);

        var cosX = Math.cos(vec.X);
        var sinX = Math.sin(vec.X);

        var zRot = new double[][] {
            new double[] { cosZ, -sinZ, 0 },
            new double[] { sinZ, cosZ, 0},
            new double[] { 0, 0, 1}
        };
        var yRot = new double[][] {
            new double[] { cosY, 0, sinY },
            new double[] { 0, 1, 0 },
            new double[] { -sinY, 0, cosY}
        };
        var xRot = new double[][] {
            new double[] { 1, 0, 0 },
            new double[] { 0, cosX, -sinX },
            new double[] { 0, sinX, cosX }
        };

        var zRotMatrix = new SimpleMatrix(zRot);
        var yRotMatrix = new SimpleMatrix(yRot);
        var xRotMatrix = new SimpleMatrix(xRot);

        var mat = SimpleMatrix.identity(3);
        mat = mat.mult(zRotMatrix);
        mat = mat.mult(yRotMatrix);
        mat = mat.mult(xRotMatrix);

        _rotationMatrix = mat;
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

    public TranslationMatrix Rotate(TranslationMatrix translation) {
        return new TranslationMatrix(GetMatrix().mult(translation.GetMatrix()));
    }
    public Vector3d Rotate(Vector3d vec) {
        return Vector3d.FromTranslationMatrix(Rotate(vec.TranslationMatrix()));
    }

    public RotationMatrix Copy()
    {
        return new RotationMatrix(_rotationMatrix.copy());
    }
}
