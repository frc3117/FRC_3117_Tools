package frc.robot.Component.Data;

/**
 * The current data of the limelight
 */
public class LimeLightData {
    public LimeLightData(double tx, double ty, double ta, double tv)
    {
        _tx = tx;
        _ty = ty;
        _ta = ta;
        _tv = tv;
    }

    private double _tx;
    private double _ty;
    private double _ta;
    private double _tv;

    /**
     * Get the horizontal angle between the camera and the target
     * @return The horizontal angle between the camera and the target  
     */
    public double GetAngleX()
    {
        return _tx;
    }
    /**
     * Get the vertical angle between the camera and the target
     * @return The vertical angle between the camera and the target  
     */
    public double GetAngleY()
    {
        return _ty;
    }
    /**
     * Get the ammount (in %) of screen space that the target take
     * @return The ammount (in %) of screen space that the target take
     */
    public double GetScreenSpace()
    {
        return _ta;
    }
    /**
     * Check if there is currently a target visible
     * @return If there is currently a target visible
     */
    public boolean IsTarget()
    {
        return _tv == 1;
    }
}
