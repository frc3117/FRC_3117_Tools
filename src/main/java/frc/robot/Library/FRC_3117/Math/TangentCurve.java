package frc.robot.Library.FRC_3117.Math;

import frc.robot.Library.FRC_3117.Component.Data.TangentCurveKey;

public class TangentCurve 
{
    public TangentCurve(TangentCurveKey... Points)
    {
        _points = Points.clone();
    }

    private TangentCurveKey[] _points;

    public double Evaluate(double x)
    {
        if(_points.length == 0)
        {
            return 0;
        }

        if(x <= _points[0].X)
        {
            return _points[0].Y;
        }

        for(int i = 0; i < _points.length - 1; i++)
        {
            if(_points[i].X <= x)
            {
                double dt = _points[i + 1].X - _points[i].X;
                x = (x - _points[i].X) / dt;

                double m0 = _points[i].OutTangent * dt;
                double m1 = _points[i + 1].InTangent * dt;
            
                double t2 = x * x;
                double t3 = t2 * x;
                
                double a = 2 * t3 - 3 * t2 + 1;
                double b = t3 - 2 * t2 + x;
                double c = t3 - t2;
                double d = -2 * t3 + 3 * t2;
                
                return a * _points[i].Y + b * m0 + c * m1 + d * _points[i + 1].Y;
            }
        }

        return _points[_points.length - 1].Y;
    }
}
