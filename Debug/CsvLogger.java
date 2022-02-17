package frc.robot.Library.FRC_3117_Tools.Debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import frc.robot.Library.FRC_3117_Tools.Math.Timer;

/**
 * A logger to save data in a csv form
 */
public class CsvLogger 
{
    private static int _columnCount = 0;
    private static HashMap<String, Integer> _columnIndex ;

    private static int _currentFrame = 0;
    private static List<String> _currentRow;

    private static StringBuilder _fileContent;

    /**
     * Initialize the CsvLogger
     */
    public static void Init()
    {
        _columnIndex = new HashMap<>();
        _currentRow = new ArrayList<>();
        _fileContent = new StringBuilder();

        _columnCount = 0;
        _currentFrame = 0;

        _currentRow.add("0");
    }
    /**
     * Add a new column to the csv
     * @param ColumnName The name of the new column
     */
    public static void AddColumn(String ColumnName)
    {
        _columnIndex.put(ColumnName, ++_columnCount);
    }

    /**
     * Set the value of the column on the current row
     * @param ColumnName The name of the column
     * @param Value The value of the column
     */
    public static void SetValue(String ColumnName, Object Value)
    {
        if(_currentFrame < Timer.GetFrameCount())
        {
            _fileContent.append(String.join(";", _currentRow));
            _fileContent.append(System.lineSeparator());

            _currentFrame = Timer.GetFrameCount();

            _currentRow = new ArrayList<>(_columnCount + 1);
            _currentRow.set(0, String.valueOf(_currentFrame * 0.02));
        }

        _currentRow.set(_columnIndex.get(ColumnName), String.valueOf(Value));
    }

    /**
     * Save the current csv to a file
     * @param FileName The name of the created file
     */
    public static void SaveToFile(String FileName)
    {
        var env = System.getenv();

        var directory = new File(env.get("HOME") + "/Debug");
        if(!directory.exists())
        {
            directory.mkdir();
        }

        var file = new File(env.get("Home") + "/Debug/" + FileName + ".csv");
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
            writer.write(_fileContent.toString());
            writer.close();
        }
        catch (IOException e) { }
    }
}
