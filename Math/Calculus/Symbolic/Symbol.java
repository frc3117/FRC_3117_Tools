package frc.robot.Library.FRC_3117_Tools.Math.Calculus.Symbolic;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;

public class Symbol extends Equation
{
    public Symbol(String sym)
    {
        _symbol = sym;
    }
    public Symbol(String sym, double value)
    {
        _symbol = sym;
        SetValue(value);
    }
    public Symbol(String sym, Equation value)
    {
        _symbol = sym;
        SetValue(value);
    }

    private String _symbol;
    private Equation _value;

    public Equation GetValue()
    {
        return _value;
    }
    public void SetValue(double value)
    {
        _value = new Scalar(value);
    }
    public void SetValue(Equation value)
    {
        _value = value;
    }

    @Override 
    public Pair<Boolean, Scalar> TryGetScalar()
    {
        if (_value == null)
            return new Pair<>(false, null);

        return _value.TryGetScalar();
    }

    @Override
    public Equation Solve()
    {
        if (_value == null)
            return this;

        return _value.Solve();
    }

    @Override
    public String toString()
    {
        if (_value == null)
            return _symbol;

        return _value.toString();
    }
}
