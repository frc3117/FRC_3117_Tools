package frc.robot.Library.FRC_3117_Tools.Math.Calculus.Symbolic;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;

public abstract class Equation 
{
    public Equation()
    {

    }   
    
    public Equation Add(double scalar)
    {
        return Add(new Scalar(scalar));
    }
    public Equation Add(Equation eq)
    {
        return new Addition(this, eq);
    }
    public Equation Sub(double scalar)
    {
        return Sub(new Scalar(scalar));
    }
    public Equation Sub(Equation eq)
    {
        return new Substract(this, eq);
    }

    public Equation Mult(Equation eq)
    {
        return new Multiply(this, eq);
    }
    public Equation Div(Equation eq)
    {
        return new Divide(this, eq);
    }

    public abstract Pair<Boolean, Scalar> TryGetScalar();
    public Equation Solve()
    {
        var asScalar = TryGetScalar();

        if (asScalar.Item1)
            return asScalar.Item2;

        return this;
    }
}
