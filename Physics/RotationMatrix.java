package frc.robot.Library.FRC_3117_Tools.Physics;

import org.ejml.simple.SimpleMatrix;

import frc.robot.Library.FRC_3117_Tools.Math.Quaternion;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

public class RotationMatrix 
{
    public RotationMatrix(Quaternion angle)
    {
        this(angle.Euler());
    }
    public RotationMatrix(Vector3d angle)
    {
        var cosX = Math.cos(angle.X);
        var sinX = Math.sin(angle.X);

        var cosY = Math.cos(angle.Y);
        var sinY = Math.sin(angle.Y);

        var cosZ = Math.cos(angle.Z);
        var sinZ = Math.sin(angle.Z);

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

    private SimpleMatrix _rotationMatrix;

    public SimpleMatrix GetMatrix()
    {
        return _rotationMatrix.copy();
    }
}
