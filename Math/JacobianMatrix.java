package frc.robot.Library.FRC_3117_Tools.Math;

import org.ejml.simple.SimpleMatrix;

import frc.robot.Library.FRC_3117_Tools.Math.Interface.PartialDerivative;

public class JacobianMatrix
{
    public JacobianMatrix(PartialDerivative[][] partialDerivates)
    {
        _partialDerivatives = partialDerivates;
        Solve();
    }

    private PartialDerivative[][] _partialDerivatives;
    private SimpleMatrix _currentMatrix;

    public SimpleMatrix Get()
    {
        return _currentMatrix;
    }
    public SimpleMatrix GetAndSolve()
    {
        Solve();
        return _currentMatrix;
    }

    public void Solve()
    {
        var newMatrix = new double[_partialDerivatives.length][];

        for (var i = 0; i < _partialDerivatives.length; i++)
        {
            var i_partialDerivatives = _partialDerivatives[i];
                  
            var i_dim = new double[i_partialDerivatives.length];
            newMatrix[i] = i_dim;
            
            for (var j = 0; j < i_dim.length; j++)
            {
                i_dim[j] = i_partialDerivatives[j].Read();
            }
        }

        _currentMatrix = new SimpleMatrix(newMatrix);
    }
}
