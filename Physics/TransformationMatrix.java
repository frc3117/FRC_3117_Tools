package frc.robot.Library.FRC_3117_Tools.Physics;

import org.ejml.simple.SimpleMatrix;

import frc.robot.Library.FRC_3117_Tools.Math.Quaternion;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class TransformationMatrix 
{
    public TransformationMatrix()
    {
        this(new TranslationMatrix(), new RotationMatrix());
    }
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
        _transformationMatrix.insertIntoThis(0, 3, translationMatrix.GetMatrix());
        _transformationMatrix.setRow(3, 0, 0, 0, 0, 1);
    }  
    public TransformationMatrix(SimpleMatrix matrix)
    {
        _transformationMatrix = matrix;
    }

    private SimpleMatrix _transformationMatrix;

    public TranslationMatrix GetTranslationMatrix()
    {
        return new TranslationMatrix(_transformationMatrix.extractMatrix(0, 4, 3, 4));
    }
    public RotationMatrix GetRotationMatrix()
    {
        return new RotationMatrix(_transformationMatrix.extractMatrix(0, 3, 0, 3));
    }

    public void SetTranslationMatrix(TransformationMatrix translation)
    {
        _transformationMatrix.insertIntoThis(0, 3, translation.GetMatrix());
    }
    public void SetRotationMatrix(RotationMatrix rotation)
    {
        _transformationMatrix.insertIntoThis(0, 0, rotation.GetMatrix());
    }

    public SimpleMatrix GetMatrix()
    {
        return _transformationMatrix;
    }

    public TransformationMatrix Copy()
    {
        return new TransformationMatrix(_transformationMatrix.copy());
    }
}
