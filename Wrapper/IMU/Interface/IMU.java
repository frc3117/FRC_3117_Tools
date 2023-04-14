package frc.robot.Library.FRC_3117_Tools.Wrapper.IMU.Interface;

import edu.wpi.first.wpilibj.interfaces.Gyro;

public interface IMU extends Gyro {
    double GetTiltX();
    double GetTiltY();
}
