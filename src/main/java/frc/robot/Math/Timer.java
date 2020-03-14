package frc.robot.Math;

/**
 * The timer class fot the robot
 */
public class Timer 
{
    private static double _startTime = 0;

    private static double _lastTime = 0;
    private static double _dt = 0;

    /**
     * Initialize the clock of the timer
     */
    public static void Init()
    {
        _lastTime = System.nanoTime();
        _startTime = GetCurrentTime();
    }
    /**
     * Calculate the current delta time
     * @return The current deltat time
     */
    public static double Calculate()
    {
        //Estimate the delta time betwen the last time
        long currentTime = System.nanoTime();
        _dt = (currentTime - _lastTime) / 1e9;
        _lastTime = currentTime; 

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
        return GetCurrentTime() - _startTime;
    }
    /**
     * Get the left in the current period
     * @return The time left in the current period
     */
    public static double GetPeriodTime()
    {
        return edu.wpi.first.wpilibj.Timer.getMatchTime();
    }
}
