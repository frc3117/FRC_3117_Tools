package frc.robot.Library.FRC_3117.Component;

import edu.wpi.first.networktables.*;
import frc.robot.Library.FRC_3117.Component.Data.LimeLightData;

/**
 * The limelight vision class
 */
public class LimeLight {

    private static NetworkTable _table = NetworkTableInstance.getDefault().getTable("limelight");
    private static boolean isUpsideDown = false;

    /**
     * Get the current data from the limelight
     * @return The data from the limelight
     */
    public static LimeLightData GetCurrent()
    {        
        NetworkTableEntry tx = _table.getEntry("tx"); //X Angle (degree)
        NetworkTableEntry ty = _table.getEntry("ty"); //Y Angle (degree)
        NetworkTableEntry ta = _table.getEntry("ta"); //Screen Space (Percent)
        NetworkTableEntry tv = _table.getEntry("tv"); //Is Target (1 or 0)

        return new LimeLightData(tx.getDouble(0) * (isUpsideDown ? -1 : 1), ty.getDouble(0) * (isUpsideDown ? -1 : 1), ta.getDouble(0), tv.getDouble(0));
    }

    /**
     * Get the current raw data 
     * @return The raw data from the limelight
     */
    public static LimeLightData GetCurrentRaw()
    {
        NetworkTableEntry tx = _table.getEntry("tx"); //X Angle (degree)
        NetworkTableEntry ty = _table.getEntry("ty"); //Y Angle (degree)
        NetworkTableEntry ta = _table.getEntry("ta"); //Screen Space (Percent)
        NetworkTableEntry tv = _table.getEntry("tv"); //Is Target (1 or 0)

        return new LimeLightData(tx.getDouble(0), ty.getDouble(0), ta.getDouble(0), tv.getDouble(0));
    }

    /**
     * Set the limelight in drive mode
     */
    public static void SetDriveMode()
    {
        _table.getEntry("camMode").setNumber(1);

        TurnOffLight();
    }
    /**
     * Set the limelight in recognition mode
     */
    public static void SetRecognitionMode()
    {
        _table.getEntry("camMode").setNumber(0);

        TurnOnLight();
    }

    /**
     * Set if the limelight is instaled upside down
     * @param state If the limelight is upside down
     */
    public void SetUpsideDown(boolean state)
    {
        isUpsideDown = state;
    }

    public static void Zoom()
    {
        //haven't found how yet
    }
    public static void UnZoom()
    {
        //haven't found how yet
    }

    // LedMode
    //
    // (0) State From Current Pipeline
    // (1) Turn Off
    // (2) Force Blink
    // (3) Turn On

    /**
     * Make the light on the limelight bink
     */
    public static void BlinkLight()
    {
        _table.getEntry("ledMode").setNumber(2);
    }
    /**
     * Turn on the light on the limelight
     */
    public static void TurnOnLight()
    {
        _table.getEntry("ledMode").setNumber(3);
    }
    /**
     * Turn off the light on the limelight
     */
    public static void TurnOffLight()
    {
        _table.getEntry("ledMode").setNumber(1);
    }
    /**
     * Make the light be on the state specified by the current pipeline
     */
    public static void SetLightDefault()
    {
        _table.getEntry("ledMode").setNumber(0);
    }

    /**
     * Set the current pipeline to run on the limelight
     * @param id The pipeline to set on the limelight
     */
    public static void SetPipeline(int id)
    {
        _table.getEntry("pipeline").setNumber(id);
    }
    /**
     * Get the current pipeline running on the limelight
     * @return
     */
    public static int GetCurrentPipeline()
    {
        return (int)_table.getEntry("getpipe").getDouble(0);
    }

    /**
     * Get the network table instance of the limelight
     * @return The network table instance of the limelight
     */
    public static NetworkTable GetTable()
    {
        return _table;
    }
    /**
     * Get an entry from the limelight network table
     * @param key The key of the entry
     * @return The entry from the network table
     */
    public static NetworkTableEntry GetEntry(String key)
    {
        return _table.getEntry(key);
    }
}
