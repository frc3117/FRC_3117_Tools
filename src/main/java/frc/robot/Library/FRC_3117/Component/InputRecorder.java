package frc.robot.Library.FRC_3117.Component;

import frc.robot.Library.FRC_3117.Component.Data.InputManager;
import frc.robot.Library.FRC_3117.Component.Data.InputPlayback;

/**
 * Record all the input and save it into a playback
 */
public class InputRecorder 
{
    private String[] _allButtons;
    private String[] _allAxis;

    public InputRecorder()
    {
        _current = new InputPlayback();
    }

    private InputPlayback _current;

    /**
     * Add a frame with the current input
     */
    public void AddFrame()
    {
        for (String button : _allButtons) 
        {
            _current.AddButtonValue(button, InputManager.GetButton(button));
        }

        for (String axis : _allAxis) 
        {
            _current.AddAxisValue(axis, InputManager.GetAxis(axis));
        }

        _current.SaveFrame();
    }

    /**
     * Reset the current recording
     */
    public void Reset()
    {
        _current = new InputPlayback();
    }

    /**
     * Get the current playback
     * @return The current playback
     */
    public InputPlayback GetPlayback()
    {
        return _current;
    }
}
