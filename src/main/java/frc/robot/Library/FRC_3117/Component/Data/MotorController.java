package frc.robot.Library.FRC_3117.Component.Data;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import frc.robot.Library.FRC_3117.Math.PID;

/**
 * The universal motor controller
 */
public class MotorController 
{
    public MotorController(MotorControllerType type, int Channel, boolean IsBrushless)
    {
        _controllerType = type;

        switch(type)
        {
            case SparkMax:
            _sparkMax = new CANSparkMax(Channel, IsBrushless ? MotorType.kBrushless : MotorType.kBrushed);
            break;

            case TalonSRX:
            _talonSRX = new WPI_TalonSRX(Channel);
            break;

            case TalonFX:
            _talonFX = new TalonFX(Channel);
            _encoderResolution = 2048;
            break;
        }

        _pid = new PID(0, 0, 0);
        _usePID = false;
    }
    public MotorController(MotorControllerType type, int Channel, boolean IsBrushless, int EncoderChannelA, int EncoderChannelB, double Kp, double Ki, double Kd)
    {
        _controllerType = type;

        switch(type)
        {
            case SparkMax:
            _sparkMax = new CANSparkMax(Channel, IsBrushless ? MotorType.kBrushless : MotorType.kBrushed);
            break;

            case TalonSRX:
            _talonSRX = new WPI_TalonSRX(Channel);
            break;

            case TalonFX:
            _talonFX = new TalonFX(Channel);
            _encoderResolution = 2048;
            break;
        }

        _encoder = new Encoder(EncoderChannelA, EncoderChannelB);

        _pid = new PID(Kp, Ki, Kd);
        _usePID = true;
    }

    public enum MotorControllerType
    {
        SparkMax,
        TalonSRX,
        TalonFX
    }

    private MotorControllerType _controllerType;
    private PID _pid;

    private boolean _usePID;

    private Encoder _encoder;
    private int _encoderResolution = 2048;

    private CANSparkMax _sparkMax;
    private WPI_TalonSRX _talonSRX;
    private TalonFX _talonFX;

    /**
     * Set the pid gain of the motor controller
     * @param Kp The proportional gain
     * @param Ki The integral gain
     * @param Kd The derivative gain
     */
    public void SetPID(double Kp, double Ki, double Kd)
    {
        _pid.SetGain(Kp, Ki, Kd);
    }
    /**
     * If the pid is used
     * @param state The new state of the pid usage
     */
    public void UsePID(boolean state)
    {
        _usePID = state;
    }
    /**
     * Set the pid in debug mode
     * @param Name The name to show it as in the SmartDashboard
     */
    public void SetDebugPID(String Name)
    {
        _pid.SetDebugMode(Name);
    }
    /**
     * Stop the pid debug mode
     */
    public void StopDebugPID()
    {
        _pid.StopDebugMode();
    }

    /**
     * Set the resolution of the encoder
     * @param Resolution The resolution of the encoder
     */
    public void SetEncoderResolution(int Resolution)
    {
        _encoderResolution = Resolution;
    }

    /**
     * Get the current velocity of the encoder connected to the motor controller
     * @return The current volocity of the encoder
     */
    public double GetEncoderVelocity()
    {
        switch(_controllerType)
        {
            case SparkMax:
            return _sparkMax.getEncoder().getVelocity() / 60.;

            case TalonSRX:
            return _talonSRX.getSelectedSensorVelocity() / _encoderResolution;

            case TalonFX:
            return _talonFX.getSelectedSensorVelocity();
        }

        return 0;
    }

    /**
     * Set the value to send to the motor contoller
     * @param Value The value to send to the motor controller
     */
    public void Set(double Value)
    {
        switch(_controllerType)
        {
            case SparkMax:
            if(_usePID)
            {
                _sparkMax.set(_pid.Evaluate(Value - (_encoder.getRate() / _encoderResolution)));
            }
            else
            {
                _sparkMax.set(Value);
            }
            break;

            case TalonSRX:
            if(_usePID)
            {
                _talonSRX.set(_pid.Evaluate(Value - (_encoder.getRate() / _encoderResolution)));
            }
            else
            {
                _talonSRX.set(Value);
            }
            break;

            case TalonFX:
            if(_usePID)
            {
                _talonFX.set(TalonFXControlMode.PercentOutput, _pid.Evaluate(Value - (_encoder.getRate() / _encoderResolution)));
            }
            else
            {
                _talonFX.set(TalonFXControlMode.PercentOutput, Value);
            }
            break;
        }
    }
    /**
     * Get the instantaneous velocity of the encoder
     * @return The instantaneous velocity of the encoder
     */
    /*public double GetEncoderVelocity()
    {
        return _encoder.getRate() / _encoderResolution;
    }*/

    /**
     * Get the current voltage of the motor controller
     * @return The voltage of the motor controller
     */
    public double GetVoltage()
    {
        switch(_controllerType)
        {
            case SparkMax:
            return _sparkMax.getBusVoltage();

            case TalonSRX:
            return _talonSRX.getBusVoltage();

            case TalonFX:
            return _talonFX.getBusVoltage();
        }
        return 0;
    }

    /**
     * Get the current temperature of the motor
     * @return The temperature of the motor
     */
    public double GetTemperature()
    {
        switch(_controllerType)
        {
            case SparkMax:
            return _sparkMax.getMotorTemperature();

            case TalonSRX:
            return _talonSRX.getTemperature();

            case TalonFX:
            return _talonFX.getTemperature();
        }
        return 0;
    }

    /**
     * Set the brake state of the current motor controller
     * @param state The brake state
     */
    public void SetBrake(boolean state)
    {
        switch(_controllerType)
        {
            case SparkMax:
            _sparkMax.setIdleMode(state ? IdleMode.kBrake : IdleMode.kCoast);
            return;

            case TalonSRX:
            _talonSRX.setNeutralMode(state ? NeutralMode.Brake : NeutralMode.Coast);
            break;

            case TalonFX:
            _talonFX.setNeutralMode(state ? NeutralMode.Brake : NeutralMode.Coast);
            break;
        }
    }

    /**
    * Set the inverted state of the motor controller
    * @param state The inverted state of the motor controller
    */
    public void SetInverted(boolean state)
    {
        switch(_controllerType)
        {
            case SparkMax:
            _sparkMax.setInverted(state);
            break;

            case TalonSRX:
            _talonSRX.setInverted(state);
            break;

            case TalonFX:
            _talonFX.setInverted(state);
            break;
        }
    }
}
