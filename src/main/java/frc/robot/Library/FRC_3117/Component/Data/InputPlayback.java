package frc.robot.Library.FRC_3117.Component.Data;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Library.FRC_3117.Component.Data.Tupple.Pair;

/**
 * A sequence of input to playback
 */
public class InputPlayback 
{
    public InputPlayback()
    {
        _frames = new ArrayList<>();
        _currentFrame = new InputPlaybackFrame();
    }
    
    private List<InputPlaybackFrame> _frames;
    private InputPlaybackFrame _currentFrame;

    /**
     * Add the value of a button to the playback sequence
     * @param ButtonName The name of the button to add
     * @param Value The value of the button to add
     */
    public void AddButtonValue(String ButtonName, boolean Value)
    {
        _currentFrame.ButtonList.add(new Pair<>(ButtonName, Value));
    }
    /**
     * Add the value of an axis to the playback sequence
     * @param AxisName The name of the axis to add
     * @param Value The name of the sequence to add
     */
    public void AddAxisValue(String AxisName, Double Value)
    {
        _currentFrame.AxisList.add(new Pair<>(AxisName, Value));
    }

    /**
     * Save the current frame into the sequence
     */
    public void SaveFrame()
    {
        _frames.add(_currentFrame);
        _currentFrame = new InputPlaybackFrame();
    }

    /**
     * Get a frame at a current time
     * @param Id The id of the frame
     * @return The requested frame
     */
    public InputPlaybackFrame GetFrame(int Id)
    {
        return _frames.get(Id);
    }
    /**
    * Get the amount of frame in the sequence
    * @return The amount of frame in the sequence
    */
    public int GetFrameCount()
    {
        return _frames.size();
    }

    /**
     * Save the current sequence to a file
     */
    public void SaveToFile()
    {
        //TODO
    }
    /**
     * Load a sequence from a file
     * @param Path The path of the fle
     * @return The loaded sequence
     */
    public static InputPlayback LoadFromFile(String Path)
    {
        InputPlayback current = new InputPlayback();

        //TODO

        return current;
    }
}
