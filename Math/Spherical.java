package frc.robot.Library.FRC_3117_Tools.Math;

import frc.robot.Library.FRC_3117_Tools.Math.Interface.PartialDerivative;

public class Spherical 
{
    public Spherical()
    {
        this(0, 0, 0);
    }
    public Spherical(Vector3d vec)
    {
        Radius = vec.Magnitude();
        Polar = Math.atan2(vec.Y, vec.X);
        Azymuth = Math.acos(vec.Z / Radius);
    }
    public Spherical(double radius, double polar, double azymuth)
    {
        Radius = radius;
        Polar = polar;
        Azymuth = azymuth;
    }

    public double Radius;
    public double Polar;
    public double Azymuth;

    public Vector3d Vector3d()
    {
        return new Vector3d(this);
    }
    
    /*public JacobianMatrix JacobianToVector3d()
    {

    }*/
    public JacobianMatrix JacobianFromVector3d()
    {
        // d(x, y, x)
        // -----------
        // d(ρ, θ, ϕ)
        var partialDerivaties = new PartialDerivative[][]
        {
            new PartialDerivative[]
            {
                () -> Math.sin(Polar) * Math.cos(Azymuth),
                () -> Radius * Math.cos(Polar) * Math.cos(Azymuth),
                () -> -Radius * Math.sin(Polar) * Math.sin(Azymuth)
            },
            new PartialDerivative[]
            {
                () -> Math.sin(Polar) * Math.sin(Azymuth),
                () -> Radius * Math.cos(Polar) * Math.sin(Azymuth),
                () -> Radius * Math.sin(Polar) * Math.cos(Azymuth)
            },
            new PartialDerivative[]
            {
                () -> Math.cos(Polar),
                () -> -Radius * Math.sin(Azymuth),
                () -> 0.
            }
        };

        return new JacobianMatrix(partialDerivaties);
    }

    public static Vector3d ToVector3d(Spherical sph)
    {
        return new Vector3d(sph);
    }
    public static Spherical FromVector3d(Vector3d vec)
    {
        return new Spherical(vec);
    }
}
