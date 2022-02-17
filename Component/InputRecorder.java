package frc.robot.Library.FRC_3117_Tools.Component;

import frc.robot.Library.FRC_3117_Tools.Component.Data.InputManager;
import frc.robot.Library.FRC_3117_Tools.Component.Data.InputPlayback;

/**
 * Record all the input and save it into a playback
 */
public class InputRecorder 
{
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
        for (var button : InputManager.AllButton) 
        {
            _current.AddButtonValue(button, InputManager.GetButton(button));
        }

        for (var axis : InputManager.AllAxis) 
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
