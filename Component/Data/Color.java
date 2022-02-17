package frc.robot.Library.FRC_3117.Component.Data;

import java.util.Random;

import frc.robot.Library.FRC_3117.Math.Mathf;

/**
 * The color class used to set the led strip color
 */
public class Color
{
    public Color(boolean Red, boolean Green, boolean Blue)
    {
        bool_R = Red;
        bool_G = Green;
        bool_B = Blue;
    }
    public Color(int Red, int Green, int Blue, int Alpha)
    {
        R = Red;
        G = Green;
        B = Blue;
        A = Alpha;
    }
    public Color(String HexColor)
    {
        R = Integer.valueOf( HexColor.substring( 1, 3 ), 16 );
        G = Integer.valueOf( HexColor.substring( 3, 5 ), 16 );
        B = Integer.valueOf( HexColor.substring( 5, 7 ), 16 );

        if(HexColor.length() >= 9)
        {
            A = Integer.valueOf( HexColor.substring( 7, 9 ), 16 );
        }
        else
        {
            A = 255;
        }
    }

    public static final Color ZERO = new Color(0, 0, 0, 0).GenerateSolenoid();
    public static final Color BLACK = new Color(0, 0, 0, 255).GenerateSolenoid();
    public static final Color WHITE = new Color(255, 255, 255, 255).GenerateSolenoid();
    public static final Color RED = new Color(255, 0 , 0, 255).GenerateSolenoid();
    public static final Color GREEN = new Color(0, 255, 0, 255).GenerateSolenoid();
    public static final Color BLUE = new Color(0, 0, 255, 255).GenerateSolenoid();

    public boolean bool_R;
    public boolean bool_G;
    public boolean bool_B;

    public int R;
    public int G;
    public int B;
    public int A;

    /**
     * Generate the solenoid version of the color from the PWM
     * @return The current color
     */
    public Color GenerateSolenoid()
    {
        bool_R = R >= 127;
        bool_G = G >= 127;
        bool_B = B >= 127;

        return this;
    }
    /**
     * Generate the PWM version of the color from the solenoid
     * @return
     */
    public Color GeneratePWM()
    {
        R = bool_R ? 255 : 0;
        G = bool_G ? 255 : 0;
        B = bool_B ? 255 : 0;

        A = 255;

        return this;
    }

    /**
     * Convert a temperature to a color
     * @param Kelvin The temperature to get the color from (Betwen 1000 and 40000)
     * @return The color from the temperature
     */
    public static Color FromTemperature(int Kelvin)
    {
        Kelvin = Mathf.Clamp(Kelvin, 1000, 40000) / 100;

        var R = 0;
        var G = 0;
        var B = 0;

        if(Kelvin <= 66)
        {
            R = 255;

            G = Kelvin;
            G = (int)(99.4708025861 * Math.log(G) - 161.1195681661);

            G = Mathf.Clamp(G, 0, 255);
        }
        else
        {
            R = Kelvin - 60;
            R = (int)(329.698727446 * Math.pow(R, -0.1332047592));

            R = Mathf.Clamp(R, 0, 255);

            G = Kelvin - 60;
            G = (int)(288.1221695283 * Math.pow(G ,-0.0755148492));

            G = Mathf.Clamp(G, 0, 255);
        }

        if(Kelvin >= 66)
        {
            B = 255;
        }
        else
        {
            B = Kelvin - 10;
            B = (int)(138.5177312231 * Math.log(B) - 305.0447927307);

            B = Mathf.Clamp(B, 0, 255);
        }

        return new Color(R, G, B, 255);
    }

    /**
     * Generate a random color
     * @return The random color
     */
    public static Color RandomColor()
    {
        var rand = new Random();

        var R = rand.nextInt(255);
        var G = rand.nextInt(255);
        var B = rand.nextInt(255);

        return new Color(R, G, B, 255);
    }
}
