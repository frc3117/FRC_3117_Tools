package frc.robot.Library.FRC_3117_Tools.Component.Data;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;
import frc.robot.Library.FRC_3117_Tools.Math.Mathf;

/**
 * A class for a smooth color gradient
 */
public class ColorGradient 
{
    public ColorGradient()
    {
        _colorList = new ArrayList<>();
    }

    private List<Pair<Double, Color>> _colorList;

    public void AddKey(double x, Color color)
    {
        _colorList.add(new Pair<>(x, color));
    }

    public Color Evaluate(double value)
    {
        if(_colorList.size() == 0)
        {
            return Color.WHITE;
        }
        else if(_colorList.size() == 1 || value <= _colorList.get(0).Item1)
        {
            return _colorList.get(0).Item2;
        }

        for(int i = 1; i < _colorList.size(); i++)
        {
            if(value <= _colorList.get(i).Item1)
            {
                return Mathf.Lerp(_colorList.get(i - 1).Item2, _colorList.get(i).Item2, value / (_colorList.get(i).Item1 - _colorList.get(i - 1).Item1));
            }
        }

        return _colorList.get(_colorList.size() - 1).Item2;
    }
}
