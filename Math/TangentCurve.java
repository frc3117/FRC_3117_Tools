package frc.robot.Library.FRC_3117_Tools.Math;

import frc.robot.Library.FRC_3117_Tools.Component.Data.TangentCurveKey;

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
                var dt = _points[i + 1].X - _points[i].X;
                x = (x - _points[i].X) / dt;

                var m0 = _points[i].OutTangent * dt;
                var m1 = _points[i + 1].InTangent * dt;
            
                var t2 = x * x;
                var t3 = t2 * x;
                
                var a = 2 * t3 - 3 * t2 + 1;
                var b = t3 - 2 * t2 + x;
                var c = t3 - t2;
                var d = -2 * t3 + 3 * t2;
                
                return a * _points[i].Y + b * m0 + c * m1 + d * _points[i + 1].Y;
            }
        }

        return _points[_points.length - 1].Y;
    }
}
