package frc.robot.Math;

import edu.wpi.first.wpilibj.drive.Vector2d;

import java.lang.Math;

/**
 * The custom math fuction usefull for robot
 */
public class Mathf 
{
    /**
     * Convert degree to radian
     */
    public final static double DEG_2_RAD = 0.0174533;
    /**
     * Convert radian to degree
     */
    public final static double RAD_2_DEG = 57.2958;

    /**
     * Convert meter to feet
     */
    public final static double METER_2_FEET = 3.28084;
    /**
     * Convert feet to meter
     */
    public final static double FEET_2_METER = 0.3048;

    /**
     * Round the current number to the closest base
     * @param value The number to round
     * @param base The base to round the number to
     * @return The current number to the closest base
     */
    public static double RoundTo(double value, double base)
    {
        return Math.round(value / base) * base;
    }
    /**
     * Floor the current number to the closest base
     * @param value The number to round
     * @param base The base to round the number to
     * @return The current number to the closest base
    */
    public static double FloorTo(double value, double base)
    {
        return Math.floor(value / base) * base;
    }
    /**
     * Ceil the current number to the closest base
     * @param value The number to round
     * @param base The base to round the number to
     * @return The current number to the closest base
     */
    public static double CeilTo(double value, double base)
    {
        return Math.ceil(value / base) * base;
    }

    /**
     * Force your value to stay betwen the min and the max value
     * @param value The value to clamp
     * @param min The min value
     * @param max The max value
     * @return The clamped value
     */
    public static double Clamp(double value, double min, double max)
    {
        return Math.max(min, Math.min(max, value));
    }
    
    /**
     * Make a linear interpolation between to value
     * @param v1 The first value
     * @param v2 The second value
     * @param t The interpolant
     * @return The interpolated value
     */
    public static double Lerp(double v1, double v2, double t)
    {
        return (t * (v2 - v1)) + v1;
    }
    /**
     * Get the interpolated value at a given x
     * @param p1 The first point
     * @param p2 The second point
     * @param x The x value to interpolate
     * @return The interpolated point at a given x
     */
    public static Vector2d Lerp(Vector2d p1, Vector2d p2, double x)
    {
        return new Vector2d(x, (p1.y + ((p2.y - p1.y)/(p2.x - p1.x)) * (x - p1.x)));
    }

    /**
     * Get the smalest delta angle between 2 angle
     * @param Source The first angle
     * @param Target The second angle
     * @return The delta angle between 2 angle
     */
    public static double DeltaAngle(double Source, double Target)
    {
        return DeltaAngle(new Polar(1, Source).vector(), new Polar(1, Target).vector());
    }
    /**
     * Get the smallest delta angle between 2 point
     * @param Source The first point
     * @param Target The second point
     * @return The smallest delts angle between 2 points
     */
    public static double DeltaAngle(Vector2d Source, Vector2d Target)
    {
        double SourceAngle = Polar.fromVector(Source).azymuth;

        double xPrim = Target.x * Math.cos(SourceAngle) - Target.y * Math.sin(SourceAngle); //Change of coordinate system
        double yPrim = Target.x * Math.sin(SourceAngle) + Target.y * Math.cos(SourceAngle);

        double angle = Math.atan2(yPrim, xPrim); //Angle betwen Source and target

        return angle;
    }
    
    /**
     * Get the angle between 2 points
     * @param p1 The first point
     * @param p2 The second point
     * @return The angle between 2 point
     */
    public static double GetAngle(Vector2d p1, Vector2d p2)
    {
        return Math.atan2(p2.y - p1.y, p2.x - p1.x);
    }

    /**
     * Rotate a vector by an angle
     * @param Vector The vector to rotate
     * @param angle The angle to rotate the vector
     * @return The rotated vector
     */
    public static Vector2d RotatePoint(Vector2d Vector, double angle)
    {
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        
        double tx = Vector.x;
        double ty = Vector.y;

        return new Vector2d((cos * tx) - (sin * ty), (sin * tx) + (cos * ty));
    }

    /**
     * Scale the vector by a gain
     * @param v1 The vector to scale
     * @param value The gain to scale the vector with
     * @return The scaled vector
     */
    public static Vector2d Vector2Scale(Vector2d v1, double value)
    {
        Vector2d vec = new Vector2d(v1.x, v1.y);

        vec.x *= value;
        vec.y *= value;

        return vec;
    }
    /**
     * Scale the vector by a gain
     * @param value The gain to scale the vector with
     * @param v1 The gain to scale the vector with
     * @return The scaled vector
     */
    public static Vector2d Vector2Scale(double value, Vector2d v1)
    {
        return Vector2Scale(v1, value);
    }

    /**
     * Add 2 vector together
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The sum of the 2 vector
     */
    public static Vector2d Vector2Sum(Vector2d v1, Vector2d v2)
    {
        Vector2d vec = new Vector2d(v1.x, v1.y);

        vec.x += v2.x;
        vec.y += v2.y;

        return vec;
    }
    /**
     * Add a value to each component of the vector
     * @param v1 The vector
     * @param val The value to add to each component of the vector
     * @return The sum of the vector and the value
     */
    public static Vector2d Vector2Sum(Vector2d v1, double val)
    {
        Vector2d vec = new Vector2d(v1.x, v1.y);

        vec.x += val;
        vec.y += val;

        return vec;
    }
    /**
     * Add a value to each component of the vector
     * @param val The value to add to each component of the vector
     * @param v1 The vector
     * @return The sum of the vector and the value
     */
    public static Vector2d Vector2Sum(double val, Vector2d v1)
    {
        return Vector2Sum(v1, val);
    }

    /**
     * Substract 2 vector together
     * @param v1 The first vector
     * @param v2 The second vector
     * @return The substraction of the 2 vector
     */
    public static Vector2d Vector2Sub(Vector2d v1, Vector2d v2)
    {
        Vector2d vec = new Vector2d(v1.x, v1.y);

        vec.x -= v2.x;
        vec.y -= v2.y;

        return vec;
    }
    /**
     * Substract a value to each component of the vector
     * @param v1 The vector
     * @param val The value to substract to each component of the vector
     * @return The substraction of the vector and the value
     */
    public static Vector2d Vector2Sub(Vector2d v1, double val)
    {
        Vector2d vec = new Vector2d(v1.x, v1.y);

        vec.x -= val;
        vec.y -= val;

        return vec;
    }
    /**
     * Substract a vector (val, val) by the vector
     * @param val The value to be substracted by the vector
     * @param v1 The vector
     * @return The substraction of the value vector and the vector
     */
    public static Vector2d Vector2Sub(double val, Vector2d v1)
    {
        Vector2d vec = new Vector2d(val, val);

        vec.x -= v1.x;
        vec.y -= v1.y;

        return vec;
    }

    /**
     * Make the vector have a magnitude of 1
     * @param v The vector to normalize
     * @return The normalized vector
     */
    public static Vector2d Normalize(Vector2d v)
    {
        double mag = v.magnitude();

        return new Vector2d(v.x / mag, v.y / mag);
    }

    /**
     * Evaluate the secant of x (1 / cos(x))
     * @param x The value of x to evaluate
     * @return The secant of x
     */
    public static double Sec(double x)
    {
        return 1 / Math.cos(x);
    }

    /**
     * Evaluate the cosecant of x (1 / sin(x))
     * @param x The value to evaluate
     * @return The cosecant of x
     */
    public static double csc(double x)
    {
        return 1 / Math.sin(x);
    }

    /**Evaluate the cotangent of x (1 / tan(x))
     * @param x The value of x to evaluate
     * @return The Cotangent of x
     */
    public static double Cot(double x)
    {
        return 1 / Math.tan(x);
    }
}
