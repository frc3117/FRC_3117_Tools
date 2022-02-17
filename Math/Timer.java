package frc.robot.Library.FRC_3117.Math;

/**
 * The timer class fot the robot
 */
public class Timer 
{
    private static double _startTime = 0;

    private static double _lastTime = 0;
    private static double _dt = 0;

    private static int _frameCount = 0;

    /**
     * Initialize the clock of the timer
     */
    public static void Init()
    {
        _lastTime = System.nanoTime();
        _startTime = GetCurrentTime();

        _frameCount = 0;
    }
    /**
     * Evaluate the current delta time
     * @return The current deltat time
     */
    public static double Evaluate()
    {
        //Estimate the delta time betwen the last time
        long currentTime = System.nanoTime();
        _dt = (currentTime - _lastTime) / 1e9;
        _lastTime = currentTime; 

        _frameCount++;

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
}
