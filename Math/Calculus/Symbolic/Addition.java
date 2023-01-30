package frc.robot.Library.FRC_3117_Tools.Math.Calculus.Symbolic;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;

public class Addition extends Equation
{
    public Addition(Equation left, Equation right)
    {
        _left = left;
        _right = right;
    }

    private Equation _left;
    private Equation _right;

    @Override
    public Pair<Boolean, Scalar> TryGetScalar()
    {
        var leftScalar = _left.TryGetScalar();
        var rightScalar = _right.TryGetScalar();

        if (leftScalar.Item1 && rightScalar.Item1)
            return new Pair<>(true, new Scalar(leftScalar.Item2.GetValue() + rightScalar.Item2.GetValue()));

        return new Pair<>(false, null);
    }

    @Override
    public String toString()
    {
        return _left.toString() + " + " + _right.toString();
    }
}
