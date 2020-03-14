package frc.robot.Component;

import edu.wpi.first.networktables.*;
import frc.robot.Component.Data.LimeLightData;

/**
 * The limelight vision class
 */
public class LimeLight {

    private static NetworkTable _table = NetworkTableInstance.getDefault().getTable("limelight");

    /**
     * Get the current data from the limelight
     * @return
     */
    public static LimeLightData GetCurrent()
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
}
