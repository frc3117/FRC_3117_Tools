package frc.robot.Library.FRC_3117_Tools.Physics;

import org.ejml.simple.SimpleMatrix;

import frc.robot.Library.FRC_3117_Tools.Math.Quaternion;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class TransformationMatrix 
{
    public TransformationMatrix(Vector3d translation, Vector3d rotation)
    {
        this(new TranslationMatrix(translation), new RotationMatrix(rotation));
    }
    public TransformationMatrix(Vector3d translation, Quaternion rotation)
    {
        this(new TranslationMatrix(translation), new RotationMatrix(rotation));
    }
    public TransformationMatrix(TranslationMatrix translationMatrix, RotationMatrix rotationMatrix)
    {
        _transformationMatrix = new SimpleMatrix(4, 4);

        _transformationMatrix.insertIntoThis(0, 0, rotationMatrix.GetMatrix());
        _transformationMatrix.insertIntoThis(0, 3, _transformationMatrix);
    }  
    
    private SimpleMatrix _transformationMatrix;

    public SimpleMatrix GetMatrix()
    {
        return _transformationMatrix.copy();
    }
}
