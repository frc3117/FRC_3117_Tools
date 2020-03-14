package frc.robot.Math;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The bang bang controller class
 */
public class BangBang 
{
    public BangBang() {}
    public BangBang(double PositiveResult, double NeurtralResult, double NegativeResult)
    {
        SetGain(PositiveResult, NeurtralResult, NegativeResult);
    }
    public BangBang(double PositiveResult, double NeurtralResult, double NegativeResult, String DebugName)
    {
        SetGain(PositiveResult, NeurtralResult, NegativeResult);

        SetDebugMode(DebugName);
    }

    private double _positive = 0;
    private double _neutral = 0;
    private double _negative = 0;

    private double _tolerency = 0;

    private double _target = 0;

    public boolean _isDebug = false;
    public String _positiveName;
    public String _neutralName;
    public String _negativeName;

    /**
     * Enable the debug mode
     * @param Name The name to show it as in the SmartDashboard
     */
    public void SetDebugMode(String Name)
    {
        if(!_isDebug)
        {
            _isDebug = true;

            _positiveName = Name + "_Positive";
            _neutralName = Name + "_Neutral";
            _negativeName = Name + "_Negative";
        }
    }
    /**
     * Disable the debug mode
     */
    public void StopDebugMode()
    {
        if(_isDebug)
        {
            _isDebug = false;

            SmartDashboard.delete(_positiveName);
            SmartDashboard.delete(_neutralName);
            SmartDashboard.delete(_negativeName);
        }
    }

    /**
     * Set the gain of the bang bang controller
     * @param PositiveResult The result when the error is positive
     * @param NeurtralResult The result when the error in is the tolerancy range
     * @param NegativeResult The result when the error is negative
     */
    public void SetGain(double PositiveResult, double NeurtralResult, double NegativeResult)
    {
        _positive = PositiveResult;
        _neutral = NeurtralResult;
        _negative = NegativeResult;
    }

    /**
     * Set the tolerency of the bang bang controller (Range in wich controller will be at neutral)
     * @param Tolerency The tolerency of the bang bang controller
     */
    public void SetTolerency(double Tolerency)
    {
        _tolerency = Tolerency;
    }

    /**
     * Set the target of the bang bang controller
     * @param Target The target of the bang bang controller
     */
    public void SetTarget(double Target)
    {
        _target = Target;
    }

    /**
     * Get the current value of the bang bang controller
     * @param Current The current value to evaluate
     * @return The current value of the bang bang controller
     */
    public double Evaluate(double Current)
    {
        double error = _target - Current;

        if(Math.abs(error) <= _tolerency)
        {
            return _isDebug ? SmartDashboard.getNumber(_neutralName, _neutral) : _neutral;
        }

        return Math.signum(error) == 1 ? (_isDebug ? SmartDashboard.getNumber(_positiveName, _positive) : _positive) : (_isDebug ? SmartDashboard.getNumber(_negativeName, _negative) : _negative);
    }
}
