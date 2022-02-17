package frc.robot.Library.FRC_3117_Tools.Interface;

/**
 * The base of each controller
 */
public interface BaseController 
{
    public double Evaluate(double Error);
    public void SetFeedForward(double feedforward);
    public void SetDebugMode(String Name);
    public void StopDebugMode();
}
