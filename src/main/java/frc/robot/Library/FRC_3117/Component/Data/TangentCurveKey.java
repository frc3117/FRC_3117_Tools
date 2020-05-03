package frc.robot.Library.FRC_3117.Component.Data;

public class TangentCurveKey 
{
    public TangentCurveKey(double x, double y, double inTangent, double outTangent)
    {
        X = x;
        Y = y;

        InTangent = inTangent;
        OutTangent = outTangent;
    }

    public double X;
    public double Y;
    
    public double InTangent;
    public double OutTangent;
}
