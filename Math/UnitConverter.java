package frc.robot.Library.FRC_3117.Math;

/**
 * A class to convert unit
 */
public class UnitConverter 
{
    /**
     * Convert RPM to radians per seconds
     * @param value The amount of RPM
     * @return The amount of radians per seconds
     */
    public static double RpmToRadsPerSeconds(double value)
    {
        return value * 2.0 * Math.PI / 60.0;
    }
    /**
     * Convert radians per seconds to RPM
     * @param value The amount of radians per seconds
     * @return The amount of RPM
     */
    public static double RadsPerSecondsToRpm(double value)
    {
        return value * 60.0 / (2.0 * Math.PI);
    }

    /**
     * Convert inches to meters
     * @param value The amount of inches
     * @return The amount of meters
     */
    public static double InchesToMeters(double value)
    {
        return value * 0.0254;
    }
    /**
     * Convert meters to inches
     * @param value The amount of meters
     * @return The amount of inches
     */
    public static double MetersToInches(double value)
    {
        return value / 0.0254;
    }

    /**
     * Convert feet to meters
     * @param value The amount of feet
     * @return The amount of meters
     */
    public static double FeetToMeters(double value)
    {
        return InchesToMeters(value * 12.0);
    }
    /**
     * Convert meters to feet
     * @param value The amount of meters
     * @return The amount of feet
     */
    public static double MetersToFeet(double value)
    {
        return MetersToInches(value) / 12.0;
    }

    /**
     * Convert degrees to radian
     * @param value The amount of degrees
     * @return The amount of radians
     */
    public static double DegreesToRadian(double value)
    {
        return Math.toRadians(value);
    }
    /**
     *  Convert radians to degrees
     * @param value The amount of radians
     * @return The amount of degrees
     */
    public static double RadianToDegrees(double value)
    {
        return Math.toDegrees(value);
    }
}
