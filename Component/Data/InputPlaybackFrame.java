package frc.robot.Library.FRC_3117_Tools.Component.Data;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;

/**
 * A frame in the input playback
 */
public class InputPlaybackFrame 
{
    public InputPlaybackFrame()
    {
        ButtonList = new ArrayList<>();
        AxisList = new ArrayList<>();
    }

    public List<Pair<String, Boolean>> ButtonList;
    public List<Pair<String, Double>> AxisList;
}
