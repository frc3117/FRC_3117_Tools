package frc.robot.Library.FRC_3117.Component;

import java.util.HashMap;

import edu.wpi.first.wpilibj.PWM;
import frc.robot.Library.FRC_3117.Component.Data.SolenoidValve;
import frc.robot.Library.FRC_3117.Interface.Component;
import frc.robot.Library.FRC_3117.Math.Timer;

/**
 * The LED controller
 */
public class Leds implements Component {
    public enum Mode
    {
        Solenoid,
        PWM
    }
    
    private Mode _mode;

    private SolenoidValve green;
    private SolenoidValve blue;
    private SolenoidValve red;

    private PWM greenPWM;
    private PWM bluePWM;
    private PWM redPWM;

    private HashMap<String, Color> ColorList;

    private Color _color;
    private Integer _priority = 0;

    private HashMap<String, ColorCycle> _cycle;
    private double _startTime;
    private int _currentCycleIndex = 0;
    private boolean _isCycle = false;
    private String _cycleName = "";

    public Leds(int greenChannel, int blueChannel, int redChannel, Mode mode) { 
        _cycle = new HashMap<String, ColorCycle>();
        _mode = mode;

        ColorList = new HashMap<String, Color>();

        if(mode == Mode.Solenoid)
        {
            green = SolenoidValve.CreateSingle(greenChannel, 1);
            blue = SolenoidValve.CreateSingle(blueChannel, 1);
            red = SolenoidValve.CreateSingle(redChannel, 1);

            //Default color in the list
            ColorList.put("white", new Color(true, true, true));

            ColorList.put("red", new Color(true, false, false));
            ColorList.put("green", new Color(false, true, false));
            ColorList.put("blue", new Color(false, false, true));
        }
        else
        {
            greenPWM = new PWM(greenChannel);
            bluePWM = new PWM(blueChannel);
            redPWM = new PWM(redChannel);

            //Default color in the list
            ColorList.put("white", new Color(255, 255, 255));

            ColorList.put("red", new Color(255, 0, 0));
            ColorList.put("green", new Color(0, 255, 0));
            ColorList.put("blue", new Color(0, 0, 255));
        }
    }

    public void Awake()
    {
        
    }

    public void Init() {
        _color = ColorList.get("off");
        _priority = 0;
    }

    /**
     * Set the new color of the led strip
     * @param color The new color
     * @param priority The priority of the new color
     * @param newPriority The priority to keep in memory
     */
    public void SetColor(String color, Integer priority, Integer newPriority) {
        if (priority >= _priority) {
            _priority = newPriority;
            _color = ColorList.get(color);

            if(_cycle.containsKey(color))
            {
                _startTime = Timer.GetCurrentTime();
                _currentCycleIndex = 0;

                _isCycle = true;
                _cycleName = color;
            }
            else
            {
                _isCycle = false;
            }
        }
    }
    /**
     * Set the new color of the led strip
     * @param color The new color
     * @param priority The priority of the new color
     * @param newPriority The priority to keep in memory
     */
    public void SetColor(Color color, Integer priority, Integer newPriority)
    {
        if (priority >= _priority) {
            _priority = newPriority;
            _color = color;

            _isCycle = false;
        }
    }

    /**
     * Add a new color to the color list
     * @param ColorName The name of the new color
     * @param Color The new color
     */
    public void AddColor(String ColorName, Color Color)
    {
        ColorList.put(ColorName, Color);
    }

    /**
     * Add a new color cycle
     * @param CycleName The name of the new color cycle
     * @param Cycle The new color cycle
     */
    public void AddColorCycle(String CycleName, String Cycle)
    {
        if(_cycle.containsKey(CycleName))
            return;

        String[] Split = Cycle.split(":", 0);

        Color[] color = new Color[Split.length];
        double[] time = new double[Split.length];

        for(int i = 0; i < Split.length; i++)
        {
            String[] data = Split[i].split("_", 0);

            if(_mode == Mode.Solenoid || data.length == 2)
            {
                color[i] = ColorList.get(data[0]);
                time[i] = Double.parseDouble(data[1]);
            }
            else
            {
                color[i] = new Color(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                time[i] = Double.parseDouble(data[3]);
            }

        }

        ColorCycle current = new ColorCycle();
        current.Color = color;
        current.Time = time;

        _cycle.put(CycleName, current);
    }

    public void DoSystem() {  
        Color CurrentColor;

        if(_isCycle)
        {
            ColorCycle current = _cycle.get(_cycleName);

            if(current.Time[_currentCycleIndex] <= Timer.GetCurrentTime() - _startTime)
            {
                if(++_currentCycleIndex == current.Time.length)
                {
                    _currentCycleIndex = 0;
                }

                _startTime = Timer.GetCurrentTime();
            }

            CurrentColor = current.Color[_currentCycleIndex];
        }
        else
        {
            CurrentColor = _color;
        }

        if(_mode == Mode.Solenoid)
        {
            red.SetState(CurrentColor.bool_R);
            green.SetState(CurrentColor.bool_G);
            blue.SetState(CurrentColor.bool_B);
        }
        else
        {
            redPWM.setRaw(CurrentColor.R);
            greenPWM.setRaw(CurrentColor.G);
            bluePWM.setRaw(CurrentColor.B);
        }
    }

    private class ColorCycle
    {
        public Color[] Color;
        public double[] Time;
    }
    private class Color
    {
        public Color(boolean Red, boolean Green, boolean Blue)
        {
            bool_R = Red;
            bool_G = Green;
            bool_B = Blue;
        }
        public Color(int Red, int Green, int Blue)
        {
            R = Red;
            G = Green;
            B = Blue;
        }

        public boolean bool_R;
        public boolean bool_G;
        public boolean bool_B;

        public int R;
        public int G;
        public int B;
    }
}