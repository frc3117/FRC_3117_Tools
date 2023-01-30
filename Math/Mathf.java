package frc.robot.Library.FRC_3117_Tools.Math;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Color;

import java.lang.Math;

/**
 * The custom math fuction usefull for robot
 */
public class Mathf 
{
    /**
     * The representation of the smallest number
     */
    public static final double kEPSILON = 1e-12;

    /**
     * Convertion from radian to degree
     */
    public static final double kRAD2DEG = 57.2957;
    /**
     * Convertion from degree to radian
     */
    public static final double kDEG2RAD = 0.0174;

    public static final double TAU = 2 * Math.PI;

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
     * Force your value to stay betwen the min and the max value
     * @param value The value to clamp
     * @param min The min value
     * @param max The max value
     * @return The clamped value
     */
    public static int Clamp(int value, int min, int max)
    {
        return Math.max(min, Math.min(max, value));
    }
    
    public static int Lerp(int v1, int v2, double t)
    {
        return (int)((t * (v2 - v1)) + v1);
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
        return new Vector2d(x, (p1.Y + ((p2.Y - p1.Y)/(p2.X - p1.X)) * (x - p1.X)));
    }
    /**
     * Get the interpolated color at a given x
     * @param c1 The first color
     * @param c2 The second color
     * @param x The x value to interpolate
     * @return The interpolated color at a given x
     */
    public static Color Lerp(Color c1, Color c2, double x)
    {
        var R = (int)Lerp(c1.R, c2.R, x);
        var G = (int)Lerp(c1.R, c2.R, x);
        var B = (int)Lerp(c1.R, c2.R, x);
        var A = (int)Lerp(c1.R, c2.R, x);

        return new Color(R, G, B, A);
    }

    public static double OffsetAngle(double angle, double offset)
    {
        var offsetAngle = angle - offset;
        if (offsetAngle >= TAU)
            offsetAngle -= TAU;
        else if (offsetAngle <= 0)
            offsetAngle += TAU;

        return offsetAngle * TAU;
    }

    /**
     * Get the smalest delta angle between 2 angle
     * @param Source The first angle
     * @param Target The second angle
     * @return The delta angle between 2 angle
     */
    public static double DeltaAngle(double Source, double Target)
    {
        var a = (Source - Target) % TAU;
        var b = (Target - Source) % TAU;

        if (a < b)
            return -a;
        
        return b;
    }
    /**
     * Get the smallest delta angle between 2 point
     * @param Source The first point
     * @param Target The second point
     * @return The smallest delts angle between 2 points
     */
    public static double DeltaAngle(Vector2d Source, Vector2d Target)
    {
        var SourceAngle = Polar.fromVector(Source).azymuth;

        var xPrim = Target.X * Math.cos(SourceAngle) - Target.Y * Math.sin(SourceAngle); //Change of coordinate system
        var yPrim = Target.X * Math.sin(SourceAngle) + Target.Y * Math.cos(SourceAngle);

        var angle = Math.atan2(yPrim, xPrim); //Angle betwen Source and target

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
        return Math.atan2(p2.Y - p1.Y, p2.X - p1.X);
    }

    /**
     * Rotate a vector by an angle
     * @param Vector The vector to rotate
     * @param angle The angle to rotate the vector
     * @return The rotated vector
     */
    public static Vector2d RotatePoint(Vector2d Vector, double angle)
    {
        var sin = Math.sin(angle);
        var cos = Math.cos(angle);
        
        var tx = Vector.X;
        var ty = Vector.Y;

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
        var vec = new Vector2d(v1.X, v1.Y);

        vec.X *= value;
        vec.Y *= value;

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
        var vec = new Vector2d(v1.X, v1.Y);

        vec.X += v2.X;
        vec.Y += v2.Y;

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
        var vec = new Vector2d(v1.X, v1.Y);

        vec.X += val;
        vec.Y += val;

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
        var vec = new Vector2d(v1.X, v1.Y);

        vec.X -= v2.X;
        vec.Y -= v2.Y;

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
        var vec = new Vector2d(v1.X, v1.Y);

        vec.X -= val;
        vec.Y -= val;

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
        var vec = new Vector2d(val, val);

        vec.X -= v1.X;
        vec.Y -= v1.Y;

        return vec;
    }

    /**
     * Make the vector have a magnitude of 1
     * @param v The vector to normalize
     * @return The normalized vector
     */
    public static Vector2d Normalize(Vector2d v)
    {
        var mag = v.Magnitude();

        return new Vector2d(v.X / mag, v.Y / mag);
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

    /**
     * Compare if 2 number a close enough to be equal
     * @param a The first number
     * @param b The second number
     * @return If the number are close enough
     */
    public static boolean EpsilonEqual(double a, double b)
    {
        return EpsilionEqual(a, b, kEPSILON);
    }
    /**
     * Compare if 2 number a close enough to be equal
     * @param a The first number
     * @param b The second number
     * @param Epsilon The representation of the smallest number
     * @return If the number are close enough
     */
    public static boolean EpsilionEqual(double a, double b, double Epsilon)
    {
        return (a - Epsilon <= b) && (a + Epsilon >= b);
    }

    /**
     * Get the next power of two bigger than the number
     * @param number The number to get the next power of two from
     * @return The next power of two
     */
    public static int NextPowerOfTwo(int number)
    {
        return (int)Math.ceil(Math.log(number)/Math.log(2));
    }
    /**
     * Get the closest number of two
     * @param number The number to get the closest power of two from
     * @return The closest power of two
     */
    public static int ClosestPowerOfTwo(int number)
    {
        if(number <= 2)
        {
            return 2;
        }
        else
        {
            var Next = NextPowerOfTwo(number);
            var Previous = Next / 2;

            if(Next - number >= number - Previous)
            {
                return Next;
            }
            else
            {
                return Previous;
            }
        }
    }
}
