package frc.robot.Library.FRC_3117.Math;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Library.FRC_3117.Interface.BasePID;

/**
 * The universal pid class
 */
public class PID implements BasePID {
    
    public PID() {}
    public PID(double KP, double KI, double KD)
    {
        SetGain(KP, KI, KD);
    }
    public PID(double KP, double KI, double KD, String DebugName)
    {
        SetGain(KP, KI, KD);
        
        SetDebugMode(DebugName);
    }

    public double Kp = 0;
    public double Ki = 0;
    public double Kd = 0;

    private double Tolerancy = 0;

    private double _feedFoward;

    private double _previousError = 0;
    private double _previousAverageError = 0;
    private double _integral = 0;

    private boolean _isDebug = false;
    private String _kpName;
    private String _kiName;
    private String _kdName;
    private String _integralSaturationMinName;
    private String _integralSaturationMaxName;

    private double _saturationIntegralMin = -999999;
    private double _staturationIntegralMax = 999999;

    private double _min;
    private double _max;
    private boolean _useMinMax;

    private RateLimiter _rateLimiter;
    private boolean _useRateLimiter;

    /**
     * Enable the debug mode
     * @param Name The name to show it as in the SmartDashboard
     */
    public void SetDebugMode(String Name)
    {
        //Make the pid gain appear in the SmartDashboard
        if(!_isDebug)
        {
            Reset();

            _isDebug = true;

            _kpName = Name + "_Kp";
            _kiName = Name + "_Ki";
            _kdName = Name + "_Kd";

            _integralSaturationMinName = Name + "_i_SaturationMin";
            _integralSaturationMaxName = Name + "_i_SaturationMax";

            SmartDashboard.putNumber(_kpName, Kp);
            SmartDashboard.putNumber(_kiName, Ki);
            SmartDashboard.putNumber(_kdName, Kd);

            SmartDashboard.putNumber(_integralSaturationMinName, _saturationIntegralMin);
            SmartDashboard.putNumber(_integralSaturationMaxName, _staturationIntegralMax);
        }
    }
    /**
     * Disable the debug mode
     */
    public void StopDebugMode()
    {
        if(_isDebug)
        {
            Reset();

            _isDebug = false;
            
            SmartDashboard.delete(_kpName);
            SmartDashboard.delete(_kiName);
            SmartDashboard.delete(_kdName);

            SmartDashboard.delete(_integralSaturationMinName);
            SmartDashboard.delete(_integralSaturationMaxName);
        }
    }

    /**
     * Set the min and the max value of the pid
     * @param Min The min value of the pid
     * @param Max The max value of the pid
     */
    public void SetMinMax(double Min, double Max)
    {
        _min = Min;
        _max = Max;

        _useMinMax = true;
    }
    /**
     * Stop using the min and the max of the pid
     */
    public void RemoveMinMax()
    {
        _useMinMax = false;
    }

    /**
     * Set the rate limiter of the pid
     * @param Velocity The velocity of the rate limiter
     */
    public void SetRateLimiter(double Velocity)
    {
        _rateLimiter.SetVelocity(Velocity);

        _useRateLimiter = true;
    }
    /**
     * Set the rate limiter of the pid
     * @param Velocity The velocity of the rate limiter
     * @param Current The current value of the rate limiter
     */
    public void SetRateLimiter(double Velocity, double Current)
    {
        _rateLimiter.SetVelocity(Velocity);
        _rateLimiter.SetCurrent(Current);

        _useRateLimiter = true;
    }
    /**
     * Stop using the rate limiter of the pid
     */
    public void RemoveRateLimiter()
    {
        _rateLimiter.Reset();

        _useRateLimiter = false;
    }

    /**
     * Set the pid gain
     * @param Kp The proportional gain
     * @param Ki The integral gain
     * @param Kd The derivative gain
     */
    public void SetGain(double KP, double KI, double KD)
    {
        Kp = KP;
        Ki = KI;
        Kd = KD;
    }
    /**
     * Set the tolerancy to consider the error as 0
     * @param tolerancy The tolerancy
     */
    public void SetTolerancy(double tolerancy)
    {
        Tolerancy = tolerancy;
    }

    /**
     * Set the current feed foward value
     * @param FeedForward The current feed foward value
     */
    public void SetFeedForward(double FeedForward)
    {
        _feedFoward = FeedForward;
    }

    public void SetSaturationIntegral(double Min, double Max)
    {
        _saturationIntegralMin = Min;
        _staturationIntegralMax = Max;
    }

    /**
     * Evaluate the current pid
     * @param Error The error of the system
     * @return The value to put in the system
     */
    public double Evaluate(double Error)
    {
        return Evaluate(Error, Timer.GetDeltaTime());
    }
    /**
     * Evaluate the current pid
     * @param Error The error of the system
     * @param Dt The delta time between the last evaluation and the current time
     * @return The value to put in the system
     */
    public double Evaluate(double Error, double Dt)
    {
        if(Tolerancy > Math.abs(Error))
        {
            Error = 0;
        }

        _integral += Error * Dt;
        if(_useMinMax)
        {
            _integral = Mathf.Clamp(_integral, _min, _max);
        }

        if(_isDebug)
        {
            _integral = Mathf.Clamp(_integral,  SmartDashboard.getNumber(_integralSaturationMinName, -999999), SmartDashboard.getNumber(_integralSaturationMaxName, 999999));
        }
        else
        {
            _integral = Mathf.Clamp(_integral,  _saturationIntegralMin, _staturationIntegralMax);
        }

        double averageError = (Error + _previousError) / 2;
        double derivative = (averageError - _previousAverageError) / Dt;

        _previousError = Error;
        _previousAverageError = averageError;

        double target;

        if(_isDebug)
        {
            target = SmartDashboard.getNumber(_kpName, 0) * Error + SmartDashboard.getNumber(_kiName, 0) * _integral + SmartDashboard.getNumber(_kdName, 0) * derivative + _feedFoward;
        }
        else
        {
            target = Kp * Error + Ki * _integral + Kd * derivative + _feedFoward;
        }

        if(_useMinMax)
        {
            target = Mathf.Clamp(target, _min, _max);
        }
        if(_useRateLimiter)
        {
            target = _rateLimiter.Evaluate(target, Dt);
        }

        return target;
    }

    /**
     * Reset the integral and the last error
     */
    public void Reset()
    {
        _previousError = 0;
        _integral = 0;
    }
}
