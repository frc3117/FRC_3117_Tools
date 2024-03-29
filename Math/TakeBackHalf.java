package frc.robot.Library.FRC_3117_Tools.Math;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Library.FRC_3117_Tools.Interface.BaseController;

/**
 * The take back half controller class
 */
public class TakeBackHalf implements BaseController
{
    public TakeBackHalf() {}
    public TakeBackHalf(double Gain)
    {
        SetGain(Gain);
    }
    public TakeBackHalf(double Gain, String DebugName)
    {
        SetGain(Gain);

        SetDebugMode(DebugName);
    }

    private double _gain = 0;

    private double _lastError = 0;
    private double _output = 0;
    private double _tbh = 0;

    private boolean _isDebug = false;
    private String _gainName;

    /**
     * Enable the debug mode
     * @param Name The name to show it as in the SmartDashboard
     */
    public void SetDebugMode(String Name)
    {
        if(!_isDebug)
        {
            Reset();

            _isDebug = true;
            _gainName = Name + "_TBH";

            SmartDashboard.putNumber(_gainName, _gain);
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

            SmartDashboard.getEntry(_gainName).close();
        }
    }

    /**
     * Set the current gain of the take back half controller
     * @param Gain The current gain of the take back half controller
     */
    public void SetGain(double Gain)
    {
        _gain = Gain;
    }

    /**
     * Evaluate the current take back half controller
     * @param Error The error of the controller
     * @return The current take back half controller
     */
    public double Evaluate(double Error)
    {
        _output += (_isDebug ? SmartDashboard.getNumber(_gainName, _gain) : _gain) * Error;
        if (Math.signum(Error) != Math.signum(_lastError)) 
        {
            _output = 0.5 * (_output + _tbh);
            _tbh = _output;
            _lastError = Error;
        }

        return _output;
    }

    /**
     * Reset the take back half controller
     */
    public void Reset()
    {
        _lastError = 0;
        _output = 0;
        _tbh = 0;
    }
    @Override
    public void SetFeedForward(double feedforward) {
        // TODO Implement
        
    }
}
