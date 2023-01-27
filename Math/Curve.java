package frc.robot.Library.FRC_3117_Tools.Math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The linear interpolation curve class
 */
public class Curve 
{
    public Curve(Vector2d... Points)
    {
        _points = new ArrayList<Vector2d>();
        Collections.addAll(_points, Points);
        Collections.sort(_points, new Vector2dSort());
    }

    private List<Vector2d> _points;

    /**
     * Evaluate the current value of x by linear interpolation
     * @param x The current value of x
     * @return The interpolated value
     */
    public double Evaluate(double x)
    {
        if(_points.size() == 0)
        {
            return 0;
        }

        if(x <= _points.get(0).X)
        {
            return _points.get(0).Y;
        }

        for(int i = 0; i < _points.size() - 1; i++)
        {
            if(_points.get(i).X >= x)
            {
                return Mathf.Lerp(_points.get(i), _points.get(i + 1), x).Y;
            }
        }

        return _points.get(_points.size() - 1).Y;
    }

    class Vector2dSort implements Comparator<Vector2d>
    {
        public int compare(Vector2d v1, Vector2d v2)
        {
            return (int)Math.signum(v1.X - v2.X);
        }
    }
}
