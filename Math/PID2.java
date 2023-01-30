package frc.robot.Library.FRC_3117_Tools.Math;

import frc.robot.Library.FRC_3117_Tools.Interface.BasePID;

public class PID2 implements BasePID
{
    public PID2()
    {

    }

    private double _kp;
    private double _ki;
    private double _kd;

    private double _setpoint;

    public void Setpoint(double setpoint)
    {
        _setpoint = setpoint;
    }

    @Override
    public double Evaluate(double current) 
    {
        return 0;
    }

    @Override
    public void SetFeedForward(double feedforward) 
    {

    }

    @Override
    public void SetDebugMode(String Name) 
    {

    }

    @Override
    public void StopDebugMode() 
    {

    }

    @Override
    public double Evaluate(double Error, double Dt) 
    {
        return 0;
    }

    @Override
    public void SetGain(double Kp, double Ki, double Kd) 
    {

    }

    @Override
    public void Reset() 
    {

    }
}
