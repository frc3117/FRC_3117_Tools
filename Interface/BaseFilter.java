package frc.robot.Library.FRC_3117.Interface;

/**
 * The base of each filter
 */
public interface BaseFilter 
{
    public double Update(double newInput);
    public double GetCurrent();
}
