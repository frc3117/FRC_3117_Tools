package frc.robot.Library.FRC_3117_Tools.Math;

/**
 * The rate limiter class
 */
public class RateLimiter 
{
    public RateLimiter(double Velocity, double InitValue)
    {
        _velocity = Velocity;
        _current = InitValue;
    }

    private double _velocity = 0;
    private double _current = 0;

    /**
     * Set the new velocity of the rate limiter
     * @param Velocity The new velocity of the rate limiter
     */
    public void SetVelocity(double Velocity)
    {
        _velocity = Velocity;
    }

    /**
     * Evaluate the current value of the rate limiter
     * @param TargetValueThe target value of the rate limiter
     * @return The evaluate current value of the rate limiter
     */
    public double Evaluate(double TargetValue)
    {
        return Evaluate(TargetValue, Timer.GetDeltaTime());
    }
    /**
     * Evaluate the current value of the rate limiter
     * @param TargetValue The target value of the rate limiter
     * @param Dt The delta time since the last evaluation
     * @return The evaluate current value of the rate limiter
     */
    public double Evaluate(double TargetValue, double Dt)
    {
        if(Math.abs(TargetValue - _current) <= _velocity * Dt)
        {
            _current = TargetValue;
        }
        else
        {
            _current += (Math.signum(TargetValue - _current)) * _velocity * Dt;
        }

        return _current;
    }

    /**
     * Override the current value of the rate limiter
     * @param Value The new value of the rate limiter
     */
    public void SetCurrent(double Value)
    {
        _current = Value;
    }

    /**
     * Get the current value of the rate limiter
     * @return The current value of the rate limiter
     */
    public double GetCurrent()
    {
        return _current;
    }

    /**
     * Reset the current rate limiter
     */
    public void Reset()
    {
        _current = 0;
        _velocity = 0;
    }
}
