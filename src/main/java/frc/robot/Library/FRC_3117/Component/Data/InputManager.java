package frc.robot.Library.FRC_3117.Component.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import frc.robot.Library.FRC_3117.Interface.Action;;

/**
 * A class that contain the initialization of the input
 */
public class InputManager 
{
    private static String[] _allButton;

    private static HashMap<String, Boolean> _lastState = new HashMap<String, Boolean>();
    private static HashMap<String, Boolean> _currentState = new HashMap<String, Boolean>();

    private static HashMap<String, List<Action>> _buttonCallback = new HashMap<String, List<Action>>();
    /**
     * Initialize the input of the project
     */
    public static void Init()
    {
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
                for(Action callback : _buttonCallback.get(key))
                {
                    callback.Invoke();
                }
            }
            if((current && !last) && _buttonCallback.containsKey("Down/" + key))
            {
                for(Action callback : _buttonCallback.get("Down/" + key))
                {
                    callback.Invoke();
                }
            }
            else if((!current && last) && _buttonCallback.containsKey("Up/" + key))
            {
                for(Action callback : _buttonCallback.get("Up/" + key))
                {
                    callback.Invoke();
                }
            }
        }
    }

    /**
     * Add a callback for a button when the button is pressed
     * @param ButtonName The name of the button to add a callback to
     * @param Callback The callback of the button
     */
    public static void AddAction(String ButtonName, Action Callback)
    {
        if(!_buttonCallback.containsKey(ButtonName))
        {
            _buttonCallback.put(ButtonName, new ArrayList<Action>());
        }

        _buttonCallback.get(ButtonName).add(Callback);
    }
    /**
     * Add a callback for a button when the button is pressed down
     * @param ButtonName The name of the button to add a callback to
     * @param Callback The callback of the button
     */
    public static void AddButtonDownCallback(String ButtonName, Action Callback)
    {
        if(!_buttonCallback.containsKey("Down/" + ButtonName))
        {
            _buttonCallback.put(ButtonName, new ArrayList<Action>());
        }

        _buttonCallback.get("Down/" + ButtonName).add(Callback);
    }
    /**
     * Add a callback for a button when the button is released
     * @param ButtonName The name of the button to add a callback to
     * @param Callback The callback of the button
     */
    public static void AddButtonUpCallback(String ButtonName, Action Callback)
    {
        if(!_buttonCallback.containsKey("Up/" + ButtonName))
        {
            _buttonCallback.put(ButtonName, new ArrayList<Action>());
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
}
