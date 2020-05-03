package frc.robot.Library.FRC_3117.Math;

import edu.wpi.first.wpilibj.drive.Vector2d;

/**
 * The polar coordinate system
 */
public class Polar 
{
    public Polar(double Radius, double Azymuth)
    {
        radius = Radius;
        azymuth = Azymuth;
    }

    public double radius;
    public double azymuth;

    /**
     * Add a polar to the current one
     * @param pol The polar to add
     * @return The sum of the 2 polar
     */
    public Polar add(Polar pol)
    {
        Vector2d vec1 = vector();
        Vector2d vec2 = pol.vector();

        return Polar.fromVector(new Vector2d(vec1.x + vec2.x, vec1.y + vec2.y));
    }
    /**
     * Substract a polar to the current one
     * @param pol The polar to substract
     * @return The substraction of the 2 polar
     */
    public Polar sub(Polar pol)
    {
        Vector2d vec1 = vector();
        Vector2d vec2 = pol.vector();

        return Polar.fromVector(new Vector2d(vec1.x - vec2.x, vec1.y - vec2.y));
    }

    /**
     * Convert the current polar into a vector
     * @return The vector made from the current polar
     */
    public Vector2d vector()
    {
        return new Vector2d(radius * Math.cos(azymuth), radius * Math.sin(azymuth));
    }
    /**
     * Convert a vector to a polar
     * @param vec The vector to convert
     * @return The polar made from the vector
     */
    public static Polar fromVector(Vector2d vec)
    {
        return new Polar(vec.magnitude(), Math.atan2(vec.x, vec.y));
    }
}
