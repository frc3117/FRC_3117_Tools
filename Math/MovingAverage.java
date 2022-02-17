package frc.robot.Library.FRC_3117.Math;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to do moving average
 */
public class MovingAverage 
{
    public MovingAverage(int NumberCount)
    {
        _numberCount = NumberCount;
        _numbers = new ArrayList<>();
    }

    private int _numberCount;
    private List<Double> _numbers;

    private double _current;

    /**
     * Evaluate the new value of the moving average
     * @param NewNumber The new number to add to the moving average
     * @return The current value of the moving average
     */
    public double Evaluate(double NewNumber)
    {
        _numbers.add(NewNumber);
        if(_numbers.size() > _numberCount)
        {
            _numbers.remove(0);
        }

        var sum = 0.;
        for(var num : _numbers)
        {
            sum += num;
        }

        _current = sum / _numbers.size();
        return _current;
    }
    /**
     * Get the current value of the moving average
     * @return The current value of the moving average
     */
    public double GetCurrent()
    {
        return _current;
    }

    /**
     * Get the current size of the moving average
     * @return
     */
    public int GetSize()
    {
        return _numbers.size();
    }

    /**
     * Clear the moving average
     */
    public void Clear()
    {
        _current = 0;
        _numbers.clear();
    }
}
