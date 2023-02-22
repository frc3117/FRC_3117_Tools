package frc.robot.Library.FRC_3117_Tools.Manifest;

import edu.wpi.first.wpilibj.interfaces.Gyro;
import frc.robot.Library.FRC_3117_Tools.Component.Data.MotorController;
import frc.robot.Library.FRC_3117_Tools.Wrapper.ADIS16448_IMU_Gyro;
import frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.AnalogAbsoluteEncoder;
import frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.DutyCycleAbsoluteEncoder;
import frc.robot.Library.FRC_3117_Tools.Wrapper.Encoder.Interface.AbsoluteEncoder;

public class RobotManifestDevices
{
    private static RobotManifestObject Devices;

    public static void Initialize() {
        Devices = RobotManifest.ManifestJson.GetSubObject("devices");
    }

    public static AbsoluteEncoder GetAbsoluteEncoder(String name) {
        var encoderObject = Devices.GetSubObject(name);

        var type = encoderObject.GetString("type");
        var id = encoderObject.GetInt("id");
        var offset = encoderObject.GetDouble("offset");
        var inverted = encoderObject.GetBoolean("inverted");

        AbsoluteEncoder encoder;
        switch (type)
        {
            case "AnalogInput":
                encoder = new AnalogAbsoluteEncoder(id);
                break;

            case "DutyCycle":
                encoder = new DutyCycleAbsoluteEncoder(id);
                break;

            default:
                return null;
        }

        encoder.SetOffset(offset);
        encoder.SetInverted(inverted);

        return encoder;
    }

    public static MotorController GetMotorController(String name) {
        var controllerObject = Devices.GetSubObject(name);

        var type = controllerObject.GetString("type");
        var id = controllerObject.GetInt("id");
        var inverted = controllerObject.GetBoolean("inverted");
        var brake = controllerObject.GetBoolean("brake");

        MotorController controller;
        switch (type)
        {
            case "CANSparkMax":
                controller = new MotorController(MotorController.MotorControllerType.SparkMax, id, true);
                break;

            case "CANSparkMaxBrushed":
                controller = new MotorController(MotorController.MotorControllerType.SparkMax, id, false);
                break;

            case "TalonFX":
                controller = new MotorController(MotorController.MotorControllerType.TalonFX, id, true);
                break;

            default:
                return null;
        }

        controller.SetInverted(inverted);
        controller.SetBrake(brake);

        return controller;
    }

    public static Gyro GetGyro(String name) {
        var gyroObject = Devices.GetSubObject(name);

        var type = gyroObject.GetString("type");
        var offset = gyroObject.GetDouble("offset");

        Gyro gyro;
        switch (type)
        {
            case "ADIS16448":
                gyro = new ADIS16448_IMU_Gyro();
                break;

            case "NavX":
                //gyro = new AHRS(SerialPort.Port.kMXP);
                return null;

            default:
                return null;
        }

        //gyro.SetOffset(offset);

        return gyro;
    }
}
