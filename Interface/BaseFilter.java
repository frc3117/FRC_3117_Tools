package frc.robot.Library.FRC_3117_Tools.Interface;

/**
 * The base of each filter
 */
public interface BaseFilter 
{
    public double Update(double newInput);
    public double GetCurrent();
}
