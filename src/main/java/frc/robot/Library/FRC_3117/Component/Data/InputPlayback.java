package frc.robot.Library.FRC_3117.Component.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    public void SaveToFile(String Path)
    {
        var file = new File(Path + ".playback");

        if(file.delete())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e) { }
        }

        try
        {
            var writer = new FileWriter(file);
            var fileContent = new StringBuilder();

            for(int i = 0; i < GetFrameCount(); i++)
            {
                var axis = _frames.get(i).AxisList;
                for(int o = 0; o < axis.size(); o++)
                {
                    fileContent.append("A/");
                    fileContent.append(axis.get(o).Item1);
                    fileContent.append("/");
                    fileContent.append(axis.get(o).Item2);
                    fileContent.append(System.lineSeparator());
                }

                var button = _frames.get(i).ButtonList;
                for(int o = 0; o < button.size(); o++)
                {
                    fileContent.append("B/");
                    fileContent.append(button.get(o).Item1);
                    fileContent.append("/");
                    fileContent.append(button.get(o).Item2);
                    fileContent.append(System.lineSeparator());
                }

                fileContent.append("#");
                fileContent.append(System.lineSeparator()); 
            }

            writer.write(fileContent.toString());
            writer.close();
        }
        catch (IOException e) { }
    }
    /**
     * Load a sequence from a file
     * @param Path The path of the fle
     * @return The loaded sequence
     */
    public static InputPlayback LoadFromFile(String Path)
    {
        var current = new InputPlayback();

        var file = new File(Path + ".playback");
        if(!file.exists())
            return null;

        try
        {
            var reader = new BufferedReader(new FileReader(file));
            var currentLine = "";

            while ((currentLine = reader.readLine()) != null) 
            {
                var Split = currentLine.split("/", 0);

                switch(Split[0])
                {
                    case "#":
                    current.SaveFrame();
                    break;

                    case "A":
                    current.AddAxisValue(Split[1], Double.parseDouble(Split[2]));
                    break;

                    case "B":
                    current.AddButtonValue(Split[1], Boolean.parseBoolean(Split[2]));
                    break;
                }
            }  
            reader.close();
        }
        catch (IOException e) { return null; }

        return current;
    }
}
