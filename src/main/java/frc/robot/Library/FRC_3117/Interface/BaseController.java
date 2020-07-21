package frc.robot.Library.FRC_3117.Interface;

/**
 * The base of each controller
 */
public interface BaseController 
{
    public double Evaluate(double Error);
    public void SetDebugMode(String Name);
    public void StopDebugMode();
}
