package frc.robot.Library.FRC_3117.Interface;

/**
 * The base of each pid
 */
public interface BasePID extends BaseController
{
    public double Evaluate(double Error, double Dt);
    public void SetGain(double Kp, double Ki, double Kd);
    public void Reset();
}
