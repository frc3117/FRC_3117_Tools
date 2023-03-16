package frc.robot.Library.FRC_3117_Tools.Wrapper;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.ADIS16448_IMU.CalibrationTime;
import edu.wpi.first.wpilibj.ADIS16448_IMU.IMUAxis;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ADIS16448_IMU_Gyro implements Gyro, Sendable
{
    public ADIS16448_IMU_Gyro()
    {
        _imu = new ADIS16448_IMU(IMUAxis.kZ, edu.wpi.first.wpilibj.SPI.Port.kMXP, CalibrationTime._1s);
        SmartDashboard.putData("ADIS16448", this);
    }

    private ADIS16448_IMU _imu;

    @Override
    public void close()
    {
        _imu.close();
    }

    @Override
    public void calibrate() 
    {
        _imu.calibrate();
    }

    @Override
    public void reset() 
    {
        _imu.reset();
    }

    @Override
    public double getAngle() 
    {
        return _imu.getGyroAngleZ();
    }

    @Override
    public double getRate() 
    {
        return _imu.getGyroRateZ();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("angle", this::getAngle, null);
    }
}