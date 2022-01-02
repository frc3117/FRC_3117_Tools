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
    public static String[] AllButton;
    public static String[] AllAxis;

    private static HashMap<String, Boolean> _lastState = new HashMap<String, Boolean>();
    private static HashMap<String, Boolean> _currentState = new HashMap<String, Boolean>();

    private static HashMap<String, List<Action>> _buttonCallback = new HashMap<String, List<Action>>();

    private static HashMap<String, Double> _currentAxisValue = new HashMap<>();

    private static boolean _isPlayback = false;
    private static int _currentPlaybackTime = 0;
    private static InputPlayback _currentPlayback;

    /**
     * Initialize the input of the project
     */
    public static void Init()
    {
        AllButton = Input.GetAllButton();
        AllAxis = Input.GetAllAxis();

        for (var key : AllButton)
        {
            _lastState.put(key, false);
            _currentState.put(key, false);
        }
        for (var key : AllAxis)
        {
            _currentAxisValue.put(key, 0.);
        }
    }

    /**
     * Compute the current input manager
     */
    public static void DoInputManager()
    {
        if(_isPlayback)
        {
            if(_currentPlayback.GetFrameCount() == _currentPlaybackTime)
            {
                _isPlayback = false;
                _currentPlayback = null;
            }
            else
            {
                var currentFrame = _currentPlayback.GetFrame(_currentPlaybackTime++);

                for(var button : currentFrame.ButtonList)
                {
                    var last = _currentState.get(button.Item1);
                    var current = button.Item2;

                    _lastState.put(button.Item1, last);
                    _currentState.put(button.Item1, current);

                    if(current && _buttonCallback.containsKey(button.Item1))
                    {
                        for(var callback : _buttonCallback.get(button.Item1))
                        {
                            callback.Invoke();
                        }
                    }
                    if((current && !last) && _buttonCallback.containsKey("Down/" + button.Item1))
                    {
                        for(var callback : _buttonCallback.get("Down/" + button.Item1))
                        {
                            callback.Invoke();
                        }
                    }
                    else if((!current && last) && _buttonCallback.containsKey("Up/" + button.Item1))
                    {
                        for(var callback : _buttonCallback.get("Up/" + button.Item1))
                        {
                            callback.Invoke();
                        }
                    }
                }

                for(var axis : currentFrame.AxisList)
                {
                    _currentAxisValue.put(axis.Item1, axis.Item2);
                }

                return;
            }
        }

        for (var key : AllButton)
        {
            var last = _currentState.get(key);
            var current = Input.GetButton(key);

            _lastState.put(key, last);
            _currentState.put(key, current);

            if(current && _buttonCallback.containsKey(key))
            {
                for(var callback : _buttonCallback.get(key))
                {
                    callback.Invoke();
                }
            }
            if((current && !last) && _buttonCallback.containsKey("Down/" + key))
            {
                for(var callback : _buttonCallback.get("Down/" + key))
                {
                    callback.Invoke();
                }
            }
            else if((!current && last) && _buttonCallback.containsKey("Up/" + key))
            {
                for(var callback : _buttonCallback.get("Up/" + key))
                {
                    callback.Invoke();
                }
            }
        }

        for (String key : AllAxis)
        {
                _currentAxisValue.put(key, Input.GetAxis(key));
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

    /**
     * Get the current value of an axis
     * @param AxisName The name of the axis to get the value from
     * @return The value of the axis
     */
    public static Double GetAxis(String AxisName)
    {
        return _currentAxisValue.get(AxisName);
    }

    /**
     * Start the playback of an input sequence
     * @param Sequence The input sequence to play
     */
    public static void StartPlayback(InputPlayback Sequence)
    {
        _isPlayback = true;
        _currentPlayback = Sequence;
        _currentPlaybackTime = 0;
    }
    /**
     * Stop the current playback
     */
    public static void StopPlayback()
    {
        _isPlayback = false;
        _currentPlayback = null;
    }
}
