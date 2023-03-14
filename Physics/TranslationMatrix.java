package frc.robot.Library.FRC_3117_Tools.Physics;

import org.ejml.simple.SimpleMatrix;

import frc.robot.Library.FRC_3117_Tools.Math.Vector2d;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class TranslationMatrix 
{
    public TranslationMatrix()
    {
        this(0, 0, 0);
    }
    public TranslationMatrix(Vector2d vec)
    {
        this(vec.X, vec.Y, 0);
    }
    public TranslationMatrix(Vector3d vec)
    {
        this(vec.X, vec.Y, vec.Z);
    }
    public TranslationMatrix(double x)
    {
        this(x, 0, 0);
    }
    public TranslationMatrix(double x, double y)
    {
        this(x, y, 0);
    }
    public TranslationMatrix(double x, double y, double z)
    {
        _translationMatrix = new SimpleMatrix(new double[][] {
            new double[] { x },
            new double[] { y },
            new double[] { z }
        });
    }
    public TranslationMatrix(SimpleMatrix matrix)
    {
        _translationMatrix = matrix;
    }
    
    private SimpleMatrix _translationMatrix;

    public double GetX()
    {
        return _translationMatrix.get(0, 0);
    }
    public double GetY()
    {
        return _translationMatrix.get(1, 0);
    }
    public double GetZ()
    {
        return _translationMatrix.get(2, 0);
    }

    public void SetX(double x)
    {
        _translationMatrix.set(0, 0, x);
    }
    public void SetY(double y)
    {
        _translationMatrix.set(1, 0, y);
    }
    public void SetZ(double z)
    {
        _translationMatrix.set(2, 0, z);
    }

    public SimpleMatrix GetMatrix()
    {
        return _translationMatrix;
    }

    public TranslationMatrix Copy()
    {
        return new TranslationMatrix(_translationMatrix.copy());
    }
}
