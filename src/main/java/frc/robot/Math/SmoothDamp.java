package frc.robot.Math;

public class SmoothDamp
{
    
    //Code found in Unity's forum (https://answers.unity.com/questions/24756/formula-behind-smoothdamp.html)
    
    public SmoothDamp(double smoothTime)
    {
        _smoothTime = smoothTime;
    }
    
    private double _smoothTime = 0;
    private double _currentVelocity = 0;
    
    public double Evaluate(double current, double target, double deltaTime)
    {
        double num = 2.0 / _smoothTime;
        double num2 = num * deltaTime;
        double num3 = 1.0 / (1.0 + num2 + 0.48 * num2 * num2 + 0.235 * num2 * num2 * num2);
        double num4 = current - target;
        double num5 = target;
        target = current - num4;
        double num6 = (_currentVelocity + num * num4) * deltaTime;
        _currentVelocity = (_currentVelocity - num * num6) * num3;
        double num7 = target + (num4 + num6) * num3;
        if (num5 - current > 0.0 == num7 > num5)
        {
            num7 = num5;
            _currentVelocity = (num7 - num5) / deltaTime;
        }
        return num7;
    }
    
    public void SetSmoothTime(double smoothTime)
    {
        _smoothTime = smoothTime;
    }
    
    public void Reset()
    {
        _currentVelocity = 0;
    }
}