package frc.robot.Library.FRC_3117.Component.Data.Tupple;

public class Trio<X, Y, Z> 
{
    public Trio() {}
    public Trio(X item1, Y item2, Z item3)
    {
        Item1 = item1;
        Item2 = item2;
        Item3 = item3;
    }

    public X Item1;
    public Y Item2;
    public Z Item3;
}
