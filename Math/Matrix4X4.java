package frc.robot.Library.FRC_3117_Tools.Math;

import frc.robot.Library.FRC_3117_Tools.Physics.RotationMatrix;
import frc.robot.Library.FRC_3117_Tools.Physics.TransformationMatrix;
import org.ejml.simple.SimpleMatrix;

public class Matrix4X4
{
    public Matrix4X4() {
        this(
                new Vector4d(1, 0, 0, 0),
                new Vector4d(0, 1, 0, 0),
                new Vector4d(0, 0, 1, 0),
                new Vector4d(0, 0, 0, 1)
        );
    }
    public Matrix4X4(Vector4d row0, Vector4d row1, Vector4d row2, Vector4d row3) {
        this(new SimpleMatrix(
                new double[][] {
                        new double[] { row0.X, row0.Y, row0.Z, row0.W },
                        new double[] { row1.X, row1.Y, row1.Z, row1.W},
                        new double[] { row2.X, row2.Y, row2.Z, row2.W},
                        new double[] { row3.X, row3.Y, row3.Z, row3.W}
                }
        ));
    }
    public Matrix4X4(TransformationMatrix transformationMatrix) {
        _matrix = transformationMatrix.GetMatrix().copy();
    }
    private Matrix4X4(SimpleMatrix matrix) {
        _matrix = matrix;
    }

    private final SimpleMatrix _matrix;

    public Vector3d GetPosition() {
        return new Vector3d(
                _matrix.get(0, 3),
                _matrix.get(1, 3),
                _matrix.get(2, 3)
        );
    }
    public Quaternion GetRotation() {
        var rotationMatrix = new RotationMatrix(_matrix.extractMatrix(0, 3, 0, 3));
        return rotationMatrix.GetQuaternion();
    }

    public Matrix4X4 mult(TransformationMatrix other) {
        return new Matrix4X4(_matrix.mult(other.GetMatrix()));
    }
    public Matrix4X4 rmult(TransformationMatrix other) {
        return new Matrix4X4(other.GetMatrix().mult(_matrix));
    }

    public Matrix4X4 mult(Matrix4X4 other) {
        return new Matrix4X4(_matrix.mult(other._matrix));
    }

    public String toString() {
        return _matrix.toString();
    }
}
