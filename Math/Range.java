package frc.robot.Library.FRC_3117_Tools.Math;

public class Range 
{
    public Range(double min, double max)
    {
        this(min, max, false);
    }
    public Range(double min, double max, boolean continuous)
    {
        Min = min;
        Max = max;
        Continuous = continuous;
    }

    public double Min;
    public double Max;

    public boolean Continuous;
    
    public boolean InRange(double value)
    {
        return value >= Min && value <= Max;
    }
    public boolean OutOfRange(double value)
    {
        return !InRange(value);
    }

    public double Clamp(double value)
    {
        return Mathf.Clamp(value, Min, Max);
    }
}
