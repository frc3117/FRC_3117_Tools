package frc.robot.Library.FRC_3117.Component;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Library.FRC_3117.Math.Timer;

/**
 * A boolean sequence tool
 */
public class BooleanSequence 
{
    /**
     * 
     * @param Resolution The resolution in Milliseconds
     */
    public BooleanSequence(int Resolution)
    {
        _values = new ArrayList<>();
        _resolution = Resolution / 1000.;
    }

    private double _resolution;
    private List<Boolean> _values;

    private double _startTime = 0;
    private boolean _currentValue = false;

    /**
     * Add a step to the sequence
     * @param Time How much time the step is going to last
     * @param Value The value of the step
     */
    public void AddStep(double Time, boolean Value)
    {
        for(int i = 0; i < Math.floor(Time / _resolution); i++)
        {
            _values.add(Value);
        }
    }
    /**
     * Clear the sequence
     */
    public void Clear()
    {
        _values.clear();
    }

    /**
     * Set the start time of the sequence
     */
    public void Start()
    {
        _startTime = Timer.GetCurrentTime();
    }

    /**
     * Evaluate the current value of the sequence
     * @return The current value of the sequence
     */
    public boolean Evaluate()
    {
        int currentIndex = (int)((Timer.GetCurrentTime() - _startTime) / _resolution) % _values.size();
        _currentValue = _values.get(currentIndex);

        return _currentValue;
    }
    /**
     * Get the current value of the sequence
     * @return The current value of the sequence
     */
    public boolean GetCurrent()
    {
        return _currentValue;
    }
}
