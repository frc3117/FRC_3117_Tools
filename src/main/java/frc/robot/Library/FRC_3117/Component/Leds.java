package frc.robot.Library.FRC_3117.Component;

import java.util.HashMap;

import edu.wpi.first.wpilibj.PWM;
import frc.robot.Library.FRC_3117.Component.Data.Color;
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
        }
        else
        {
            greenPWM = new PWM(greenChannel);
            bluePWM = new PWM(blueChannel);
            redPWM = new PWM(redChannel);            
        }

        //Default color in the list
        ColorList.put("white", Color.WHITE);

        ColorList.put("red", Color.RED);
        ColorList.put("green", Color.GREEN);
        ColorList.put("blue", Color.BLUE);
    }

    public void Awake()
    {
        
    }

    public void Init() {
        _color = ColorList.get("off");
        _priority = 0;
    }

    public void Disabled()
    {
        
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

            if(_cycle.containsKey(color))
            {
                _startTime = Timer.GetCurrentTime();
                _currentCycleIndex = 0;

                _isCycle = true;
                _cycleName = color;
            }
            else
            {
                _color = ColorList.get(color);
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
                color[i] = new Color(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), data.length == 4 ? Integer.parseInt(data[3]) : 255);
                time[i] = Double.parseDouble(data[3]);
            }

        }

        ColorCycle current = new ColorCycle();
        current.Color = color;
        current.Time = time;

        _cycle.put(CycleName, current);
    }

    public void DoComponent() {  
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
            redPWM.setRaw((int)(CurrentColor.R * (CurrentColor.A / 255.)));
            greenPWM.setRaw((int)(CurrentColor.G * (CurrentColor.A / 255.)));
            bluePWM.setRaw((int)(CurrentColor.B * (CurrentColor.A / 255.)));
        }
    }

    private class ColorCycle
    {
        public Color[] Color;
        public double[] Time;
    }
}