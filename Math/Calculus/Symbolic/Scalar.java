package frc.robot.Library.FRC_3117_Tools.Math.Calculus.Symbolic;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;

public class Scalar extends Equation
{
    public Scalar(double value)
    {
        _value = value;
    }

    private double _value;

    public double GetValue()
    {
        return _value;
    }
    public void SetValue(double value)
    {
        _value = value;
    }

    @Override
    public Pair<Boolean, Scalar> TryGetScalar()
    {
        return new Pair<Boolean,Scalar>(true, this);
    }

    @Override
    public Equation Solve()
    {
        return this;
    }

    @Override
    public String toString()
    {
        return Double.toString(_value);
    }
}
