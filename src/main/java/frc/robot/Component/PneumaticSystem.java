package frc.robot.Component;

import edu.wpi.first.wpilibj.Compressor;

/**
 * The pneumatic system manager class
 */
public class PneumaticSystem 
{
    private static Compressor _compressor;
    private static boolean _isRuning;

    /**
     * Initialize the pneumatic system
     */
    public static void Init()
    {
        _compressor = new Compressor();
    }

    /**
     * Check the presure on the pneumatic system and start the compresspr if needed
     */
    public static void CheckPressure()
    {
        //Only start the compressor if we neeed air
        if(_isRuning && _compressor.getPressureSwitchValue())
        {
            _compressor.stop();
            _isRuning = false;
        }
        else if (!_isRuning && !_compressor.getPressureSwitchValue())
        {
            _compressor.start();
            _isRuning = true;
        }
    }
}
