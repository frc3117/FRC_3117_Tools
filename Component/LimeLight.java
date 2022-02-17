package frc.robot.Library.FRC_3117.Component;

import edu.wpi.first.networktables.*;
import frc.robot.Library.FRC_3117.Component.Data.LimeLightData;

/**
 * The limelight vision class
 */
public class LimeLight {

    public static final String LIMELIGHT_TABLE = "limelight";
    public static final String X_ANGLE_ENTRY = "tx";
    public static final String Y_ANGLE_ENTRY = "ty";
    public static final String SCREE_SPACE_ENTRY = "ta";
    public static final String IS_TARGET_ENTRY = "tv";
    public static final String CAM_MODE_ENTRY = "camMode";
    public static final String LED_MODE_ENTRY = "ledMode";
    public static final String PIPELINE_ENTRY = "pipeline";
    public static final String GET_PIPELINE_ENTRY = "getPipe";

    private static NetworkTable _table = NetworkTableInstance.getDefault().getTable(LIMELIGHT_TABLE);
    private static boolean isUpsideDown = false;

    /**
     * Get the current data from the limelight
     * @return The data from the limelight
     */
    public static LimeLightData GetCurrent()
    {        
        var tx = _table.getEntry(X_ANGLE_ENTRY); //X Angle (degree)
        var ty = _table.getEntry(Y_ANGLE_ENTRY); //Y Angle (degree)
        var ta = _table.getEntry(SCREE_SPACE_ENTRY); //Screen Space (Percent)
        var tv = _table.getEntry(IS_TARGET_ENTRY); //Is Target (1 or 0)

        return new LimeLightData(tx.getDouble(0) * (isUpsideDown ? -1 : 1), ty.getDouble(0) * (isUpsideDown ? -1 : 1), ta.getDouble(0), tv.getDouble(0));
    }

    /**
     * Get the current raw data 
     * @return The raw data from the limelight
     */
    public static LimeLightData GetCurrentRaw()
    {
        var tx = _table.getEntry(X_ANGLE_ENTRY); //X Angle (degree)
        var ty = _table.getEntry(Y_ANGLE_ENTRY); //Y Angle (degree)
        var ta = _table.getEntry(SCREE_SPACE_ENTRY); //Screen Space (Percent)
        var tv = _table.getEntry(IS_TARGET_ENTRY); //Is Target (1 or 0)

        return new LimeLightData(tx.getDouble(0), ty.getDouble(0), ta.getDouble(0), tv.getDouble(0));
    }

    /**
     * Set the limelight in drive mode
     */
    public static void SetDriveMode()
    {
        _table.getEntry(CAM_MODE_ENTRY).setNumber(1);

        TurnOffLight();
    }
    /**
     * Set the limelight in recognition mode
     */
    public static void SetRecognitionMode()
    {
        _table.getEntry(CAM_MODE_ENTRY).setNumber(0);

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
        _table.getEntry(LED_MODE_ENTRY).setNumber(2);
    }
    /**
     * Turn on the light on the limelight
     */
    public static void TurnOnLight()
    {
        _table.getEntry(LED_MODE_ENTRY).setNumber(3);
    }
    /**
     * Turn off the light on the limelight
     */
    public static void TurnOffLight()
    {
        _table.getEntry(LED_MODE_ENTRY).setNumber(1);
    }
    /**
     * Make the light be on the state specified by the current pipeline
     */
    public static void SetLightDefault()
    {
        _table.getEntry(LED_MODE_ENTRY).setNumber(0);
    }

    /**
     * Set the current pipeline to run on the limelight
     * @param id The pipeline to set on the limelight
     */
    public static void SetPipeline(int id)
    {
        _table.getEntry(PIPELINE_ENTRY).setNumber(id);
    }
    /**
     * Get the current pipeline running on the limelight
     * @return
     */
    public static int GetCurrentPipeline()
    {
        return (int)_table.getEntry(GET_PIPELINE_ENTRY).getDouble(0);
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
