package frc.robot.Library.FRC_3117.Component.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class that contain the initialization of the input
 */
public class InputManager 
{
    private static String[] _allButton;

    private static HashMap<String, Boolean> _lastState = new HashMap<String, Boolean>();
    private static HashMap<String, Boolean> _currentState = new HashMap<String, Boolean>();

    private static HashMap<String, List<ButtonCallback>> _buttonCallback = new HashMap<String, List<ButtonCallback>>();
    /**
     * Initialize the input of the project
     */
    public static void Init()
    {
        //Swerve
        Input.CreateAxis("Horizontal", 1, 0, false);
        Input.CreateAxis("Vertical", 1, 1, false);
        Input.CreateAxis("Rotation", 1, 3, true);

        Input.SetAxisNegative("Rotation", 1, 2, false);

        Input.SetAxisDeadzone("Horizontal", 0.2);
        Input.SetAxisDeadzone("Vertical", 0.2);

        Input.CreateButton("GearShift", 1, 4);

        //Intake
        Input.CreateButton("ToggleIntake", 1, 4);
        Input.CreateButton("StartFeeder", 1, 1);
        Input.CreateButton("ReverseFeeder", 1, 2);

        //Thrower
        Input.CreateButton("Align", 0, 1);
        Input.CreateButton("Shoot", 0, 2);

        //Climber
        Input.CreateButton("ClimberUp", 0, 8);
        Input.CreateButton("ClimberDown", 0, 7);

        Input.CreateButton("Temp", 0, 5);

        _allButton = Input.GetAllButton();
        for (String key : _allButton)
        {
            _lastState.put(key, false);
            _currentState.put(key, false);
        }
    }

    /**
     * Compute the current input manager
     */
    public static void DoInputManager()
    {
        for (String key : _allButton)
        {
            boolean last = _currentState.get(key);
            boolean current = Input.GetButton(key);

            _lastState.put(key, last);
            _currentState.put(key, current);

            if(current && _buttonCallback.containsKey(key))
            {
                for(ButtonCallback callback : _buttonCallback.get(key))
                {
                    callback.OnPressed();
                }
            }
            if((current && !last) && _buttonCallback.containsKey("Down/" + key))
            {
                for(ButtonCallback callback : _buttonCallback.get("Down/" + key))
                {
                    callback.OnPressed();
                }
            }
            else if((!current && last) && _buttonCallback.containsKey("Up/" + key))
            {
                for(ButtonCallback callback : _buttonCallback.get("Up/" + key))
                {
                    callback.OnPressed();
                }
            }
        }
    }

    /**
     * Add a callback for a button when the button is pressed
     * @param ButtonName The name of the button to add a callback to
     * @param Callback The callback of the button
     */
    public static void AddButtonCallback(String ButtonName, ButtonCallback Callback)
    {
        if(!_buttonCallback.containsKey(ButtonName))
        {
            _buttonCallback.put(ButtonName, new ArrayList<ButtonCallback>());
        }

        _buttonCallback.get(ButtonName).add(Callback);
    }
    /**
     * Add a callback for a button when the button is pressed down
     * @param ButtonName The name of the button to add a callback to
     * @param Callback The callback of the button
     */
    public static void AddButtonDownCallback(String ButtonName, ButtonCallback Callback)
    {
        if(!_buttonCallback.containsKey("Down/" + ButtonName))
        {
            _buttonCallback.put(ButtonName, new ArrayList<ButtonCallback>());
        }

        _buttonCallback.get("Down/" + ButtonName).add(Callback);
    }
    /**
     * Add a callback for a button when the button is released
     * @param ButtonName The name of the button to add a callback to
     * @param Callback The callback of the button
     */
    public static void AddButtonUpCallback(String ButtonName, ButtonCallback Callback)
    {
        if(!_buttonCallback.containsKey("Up/" + ButtonName))
        {
            _buttonCallback.put(ButtonName, new ArrayList<ButtonCallback>());
        }

        _buttonCallback.get("Up/" + ButtonName).add(Callback);
    }

    /**
     * Get if the button is currently being pressed
     * @param ButtonName The name of the button to check
     * @return If the button nis currently being pressed
     */
    public static boolean GetButton(String ButtonName)
    {
        return _currentState.get(ButtonName);
    }
    /**
     * Get if the button just got pressed down
     * @param ButtonName The name of the button to check
     * @return If the button just got pressed down
     */
    public static boolean GetButtonDown(String ButtonName)
    {
        return !_lastState.get(ButtonName) && _currentState.get(ButtonName);
    }
    /**
     * Get if the button just got release
     * @param ButtonName The name of the button to check
     * @return If the button just got release
     */
    public static boolean GetButtonUp(String ButtonName)
    {
        return _lastState.get(ButtonName) && !_currentState.get(ButtonName);
    }

    public interface ButtonCallback
    {
        public void OnPressed();
    }
}
