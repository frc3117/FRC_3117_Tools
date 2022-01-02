package frc.robot.Library.FRC_3117.Math;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Library.FRC_3117.Interface.BasePID;

/**
 * The simple PID class
 */
public class SimplePID implements BasePID
{
    public SimplePID() {}
    public SimplePID(double KP, double KI, double KD)
    {
        SetGain(KP, KI, KD);
    }
    public SimplePID(double KP, double KI, double KD, String DebugName)
    {
        SetGain(KP, KI, KD);
        
        SetDebugMode(DebugName);
    }

    public double Kp = 0;
    public double Ki = 0;
    public double Kd = 0;

    private double _feedFoward;

    private double _previousError = 0;
    private double _integral = 0;

    private boolean _isDebug = false;
    private String _kpName;
    private String _kiName;
    private String _kdName;

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

            SmartDashboard.putNumber(_kpName, Kp);
            SmartDashboard.putNumber(_kiName, Ki);
            SmartDashboard.putNumber(_kdName, Kd);

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
        }
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
        var derivative = (_previousError - Error) / Dt;
        var target = 0.;

        _integral += Error * Dt;
        _previousError = Error;

        if(_isDebug)
        {
            target = SmartDashboard.getNumber(_kpName, 0) * Error + SmartDashboard.getNumber(_kiName, 0) * _integral + SmartDashboard.getNumber(_kdName, 0) * derivative + _feedFoward;
        }
        else
        {
            target = Kp * Error + Ki * _integral + Kd * derivative + _feedFoward;
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
