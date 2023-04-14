package frc.robot.Library.FRC_3117_Tools.Wrapper.IMU;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.ADIS16448_IMU.CalibrationTime;
import edu.wpi.first.wpilibj.ADIS16448_IMU.IMUAxis;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Library.FRC_3117_Tools.Wrapper.IMU.Interface.IMU;

public class ADIS16448_IMU implements IMU, Sendable
{
    public ADIS16448_IMU()
    {
        _imu = new edu.wpi.first.wpilibj.ADIS16448_IMU(IMUAxis.kZ, edu.wpi.first.wpilibj.SPI.Port.kMXP, CalibrationTime._1s);
        SmartDashboard.putData("ADIS16448", this);
    }

    private edu.wpi.first.wpilibj.ADIS16448_IMU _imu;

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
        return _imu.getAngle();
    }

    @Override
    public double getRate() 
    {
        return _imu.getRate();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("angle", this::getAngle, null);
    }

    @Override
    public double GetTiltX() {
        return _imu.getXComplementaryAngle();
    }
    @Override
    public double GetTiltY() {
        return _imu.getYComplementaryAngle();
    }
}