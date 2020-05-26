package frc.robot.Library.FRC_3117.Component.Data;

import java.util.Random;

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

        if(HexColor.length() == 9)
        {
            A = Integer.valueOf( HexColor.substring( 7, 9 ), 16 );
        }
        else
        {
            A = 255;
        }
    }

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
     * Generate a random color
     * @return The random color
     */
    public static Color RandomColor()
    {
        Random rand = new Random();

        int R = rand.nextInt(255);
        int G = rand.nextInt(255);
        int B = rand.nextInt(255);

        return new Color(R, G, B, 255);
    }
}
