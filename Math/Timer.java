package frc.robot.Library.FRC_3117_Tools.Math;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;
import frc.robot.Library.FRC_3117_Tools.Interface.Action;

/**
 * The timer class fot the robot
 */
public class Timer 
{
    private static double _startTime = 0;

    private static double _lastTime = 0;
    private static double _dt = 0;

    private static int _frameCount = 0;

    private static List<Pair<Double, Action>> _scheduledEvents = new ArrayList<>();

    /**
     * Initialize the clock of the timer
     */
    public static void Init()
    {
        _lastTime = System.nanoTime();
        _startTime = GetCurrentTime();

        _frameCount = 0;

        _scheduledEvents = new ArrayList<>();
    }
    /**
     * Evaluate the current delta time
     * @return The current deltat time
     */
    public static double Evaluate()
    {
        //Estimate the delta time betwen the last time
        var currentTime = GetCurrentTime();
        _dt = (currentTime - _lastTime);
        _lastTime = currentTime; 

        _frameCount++;

        //Check for scheduled event
        var toRemove = new ArrayList<Pair<Double, Action>>();
        for(var event : _scheduledEvents)
        {
            if(currentTime >= event.Item1)
            {
                event.Item2.Invoke();
                toRemove.add(event);
            }
        }
        _scheduledEvents.removeAll(toRemove);

        return currentTime;
    }
    /**
     * Get the current delta time
     * @return The current delta time
     */
    public static double GetDeltaTime()
    {
        //The last estimated delta time
        return _dt;
    }
    /**
     * Get the current clock time
     * @return The current clock time
     */
    public static double GetCurrentTime()
    {
        //The current time of the RoboRio
        return System.nanoTime() / 1e9;
    }
    /**
     * Get the time since the match started
     * @return The time since the match started
     */
    public static double GetTimeSinceStart()
    {
        return GetElasped(_startTime);
    }
    /**
     * Get the left in the current period
     * @return The time left in the current period
     */
    public static double GetPeriodTime()
    {
        return edu.wpi.first.wpilibj.Timer.getMatchTime();
    }
    /**
     * Get the elapsed time since the given time
     * @param time The time to evaluate from
     * @return The elapsed time
     */
    public static double GetElasped(double time)
    {
        return GetCurrentTime() - time;
    }

    /**
     * Get the amount of frame have passed since the start
     * @return The amount of frame
     */
    public static int GetFrameCount()
    {
        return _frameCount;
    }

    /**
     * Schedule an event to be run in a specific amount of time
     * @param time The time before the event in seconds
     * @param callback The event to be called
     */
    public static void ScheduleEvent(double time, Action callback)
    {
        _scheduledEvents.add(new Pair<Double,Action>(time + GetCurrentTime(), callback));
    }
    /**
     * Clear the events list
     */
    public static void ClearEvents()
    {
        _scheduledEvents.clear();
    }
}
