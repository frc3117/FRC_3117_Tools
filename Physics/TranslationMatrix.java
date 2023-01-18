package frc.robot.Library.FRC_3117_Tools.Physics;

import org.ejml.simple.SimpleMatrix;

import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class TranslationMatrix 
{
    public TranslationMatrix(Vector3d translation)
    {
        _translationMatrix = new SimpleMatrix(new double[][] {
            new double[] { translation.X },
            new double[] { translation.Y },
            new double[] { translation.Z }
        });
    }    

    private SimpleMatrix _translationMatrix;

    public SimpleMatrix GetMatrix()
    {
        return _translationMatrix.copy();
    }
}
