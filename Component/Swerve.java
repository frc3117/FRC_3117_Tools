package frc.robot.Library.FRC_3117_Tools.Component;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Input;
import frc.robot.Library.FRC_3117_Tools.Component.Data.MotorController;
import frc.robot.Library.FRC_3117_Tools.Component.Data.Tupple.Pair;
import frc.robot.Library.FRC_3117_Tools.Component.Data.WheelData;
import frc.robot.Library.FRC_3117_Tools.Interface.Component;
import frc.robot.Library.FRC_3117_Tools.Interface.FromManifest;
import frc.robot.Library.FRC_3117_Tools.Manifest.RobotManifestDevices;
import frc.robot.Library.FRC_3117_Tools.Manifest.RobotManifestObject;
import frc.robot.Library.FRC_3117_Tools.Math.AdvancedPID;
import frc.robot.Library.FRC_3117_Tools.Math.Mathf;
import frc.robot.Library.FRC_3117_Tools.Math.Polar;
import frc.robot.Library.FRC_3117_Tools.Math.RateLimiter;
import frc.robot.Library.FRC_3117_Tools.Math.Timer;
import frc.robot.Library.FRC_3117_Tools.Math.UnitConverter;
import frc.robot.Library.FRC_3117_Tools.Math.Vector2d;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@FromManifest(EntryName = "swerveDrive")
public class Swerve implements Component, Sendable 
{
    public Swerve(WheelData[] WheelsData, Gyro imu)
    {
        _wheelCount = WheelsData.length;

        _directionPID = new AdvancedPID[_wheelCount];

        _flipAngleOffset = new double[_wheelCount];
        _flipDriveMultiplicator = new double[_wheelCount];

        _lastAngle = new double[_wheelCount];
        _lastDirectionCommand = new double[_wheelCount];

        //Set default value of the rate limiter to "Infinity" (value that will make the rate limiter go instantly to the target)
        _horizontalRateLimiter = new RateLimiter(10000, 0);
        _verticaRateLimiter = new RateLimiter(10000, 0);
        _rotationRateLimiter = new RateLimiter(10000, 0);

        //Initializing all component of the swerve system
        for(int i  = 0; i < _wheelCount; i++)
        {
            _flipAngleOffset[i] = 0;
            _flipDriveMultiplicator[i] = 1;

            _directionPID[i] = new AdvancedPID();

            _lastDirectionCommand[i] = 0;
        }

        Modules = WheelsData;
        IMU = imu;

        SmartDashboard.putData("SwerveDrive", this);
    }

    public static Pair<String, Component> CreateFromManifest(RobotManifestObject manifestObject) {
        var imu = RobotManifestDevices.GetGyro(manifestObject.GetString("IMU"));

        var modulesManifestObject = manifestObject.GetSubObjectArray("modules");
        var modules = new WheelData[modulesManifestObject.length];
        for (var i = 0; i < modules.length; i++)
        {
            var module = new WheelData();
            var moduleManifestObject = modulesManifestObject[i];

            var dirEncoderName = moduleManifestObject.GetString("headingEncoder");
            module.DirectionEncoder = RobotManifestDevices.GetAbsoluteEncoder(dirEncoderName);

            var dirMotorName = moduleManifestObject.GetString("headingMotor");
            module.DirectionController = RobotManifestDevices.GetMotorController(dirMotorName);

            var driveMotorName = moduleManifestObject.GetString("driveMotor");
            module.DriveController = RobotManifestDevices.GetMotorController(driveMotorName);

            module.WheelPosition = moduleManifestObject.GetVector2d("position");

            modules[i] = module;
        }

        var swerve = new Swerve(modules, imu);
        DrivingMode.valueOf(manifestObject.GetString("driveMode"));

        return new Pair<>("SwerveDrive", swerve);
    }

    public enum DrivingMode
    {
        Point,
        Local,
        World,
        Tank
    }

    public WheelData[] Modules;
    public Gyro IMU;

    private int _wheelCount;

    private double[] _flipAngleOffset;
    private double[] _flipDriveMultiplicator;

    private double[] _lastAngle;

    private AdvancedPID[] _directionPID;

    private DrivingMode _mode = DrivingMode.Local;
    private double _speedRatio = 0.5;
    private double _roationSpeedRatio = 0.5;

    private double _pointExponent = 0;
    private double _pointDistance = 0;

    private double _headingOffset;
    private double _headingOffsetManual;

    private RateLimiter _horizontalRateLimiter;
    private RateLimiter _verticaRateLimiter;
    private RateLimiter _rotationRateLimiter;

    private double _rotationAxisOverride = 0;
    private boolean _isRotationAxisOverriden = false;

    private double _horizontalAxisOverride = 0;
    private boolean _isHorizontalAxisOverride = false;

    private double _verticalAxisOverride = 0;
    private boolean _isVerticalAxisOverride = false;

    private double _instantHorizontal = 0;
    private double _instantVertical = 0;
    private double _instantRotation = 0;

    private double[] _lastDirectionCommand;

    @Override
    public void Awake()
    {
        
    }

    @Override
    public void Init()
    {
        InitIMU();
    }

    @Override
    public void Disabled()
    {
        
    }


    /**
     * Set the current drive mode of the swerve drive
     * @param Mode The drive mode
     */
    public void SetCurrentMode(DrivingMode Mode)
    {
        _mode = Mode;
    }
    /**
     * Set the current drive mode of the swerve drive
     * @param Mode The drive mode
     */
    public void SetCurrentMode(int Mode)
    {
        switch(Mode)
        {
            case 0:
            _mode = DrivingMode.Point;
            break;

            case 1:
            _mode = DrivingMode.Local;
            break;

            case 2:
            _mode = DrivingMode.World;
            break;

            case 3:
            _mode = DrivingMode.Tank;
            break;
        }
    }
    /**
     * Set the exponent parameter of the point drive
     * @param Exponent The exponent parameter
     */
    public void SetPointDriveExponent(double Exponent)
    {
        _pointExponent = Exponent;
    }
    /**
     * Set the distance parameter of the point drive
     * @param Distance The distance parameter
     */
    public void SetPointDriveDistance(double Distance)
    {
        _pointDistance = Distance;
    }
    
    /**
     * Initialize the IMU
     */
    public void InitIMU()
    {
        RecalibrateIMU();
    }
    /**
     * Recalibrate the IMU
     */
    public void RecalibrateIMU()
    {
        IMU.reset();
        IMU.calibrate();

        _headingOffset = IMU.getAngle() * Mathf.kDEG2RAD + 3.1415;
    }

    /**
     * Set the speed ratio of the swerve drive
     * @param Speed The speed ratio
     */
    public void SetSpeed(double Speed)
    {
        _speedRatio = Speed;
    }
    /**
     * Set the PID gain of the wheel direction motor
     * @param ID The id of the wheel
     * @param Kp The proportional gain
     * @param Ki The integral gain
     * @param Kd The derivative gain
     */
    public void SetPIDGain(int ID, double Kp, double Ki, double Kd)
    {
        _directionPID[ID].SetGain(Kp, Ki, Kd);
        _directionPID[ID].SetDebugMode("Swerve");
    }

    /**
     * Set the rate limiter max speed
     * @param MaxSpeed The max speed of the rate limiter
     */
    public void SetRateLimiter(double MaxSpeed)
    {
        _horizontalRateLimiter.SetVelocity(MaxSpeed);
        _verticaRateLimiter.SetVelocity(MaxSpeed);
    }
    /**
     * Set the rotation rate limiter max speed
     * @param MaxSpeed The max speed of the rotation rate limiter
     */
    public void SetRotationRateLimiter(double MaxSpeed)
    {
        _rotationRateLimiter.SetVelocity(MaxSpeed);
    }

    /**
     * Override the current rotation axis value
     * @param AxisValue The rotation value to set
     */
    public void OverrideRotationAxis(double AxisValue)
    {
        _rotationAxisOverride = Mathf.Clamp(AxisValue, -1, 1);
        _isRotationAxisOverriden = true;
    }
    /**
     * Override the current horizontal axis value
     * @param AxisValue The horizontal value to set
     */
    public void OverrideHorizontalAxis(double AxisValue)
    {
        _horizontalAxisOverride = Mathf.Clamp(AxisValue, -1, 1);
        _isHorizontalAxisOverride = true;
    }
    /**
     * Override the current vertical axis value
     * @param AxisValue The vertical value to set
     */
    public void OverrideVerticalAxis(double AxisValue)
    {
        _verticalAxisOverride = Mathf.Clamp(AxisValue, -1, 1);
        _isVerticalAxisOverride = true;
    }

    /**
     * Get the current heading of the swerve drive
     * @return
     */
    public double GetHeading()
    {
        var heading = IMU.getAngle() * Mathf.kDEG2RAD;
        var totalOffset = _headingOffset + _headingOffsetManual;

        if (heading >= totalOffset)
            heading -= totalOffset;
        else
            heading += totalOffset;

        return heading;
    }
    /**
     * Get the current estimated position of the swerve drive
     */
    public Vector2d GetPostion()
    {
        return new Vector2d();
    }

    public void SetHeadingOffset(double offset)
    {
        _headingOffsetManual = offset;
    }

    /**
     * Set the current estimated position of the swerve drive
     * @param Position The position to set
     */
    public void SetPosition(Vector2d Position)
    {
        //Do nothing yes
    }

    /**
     * Get the current vector of a wheel
     * @param ID The id of the wheel
     * @return The current vector of the wheel
     */
    public Vector2d GetWheelVector(int ID)
    {
        var Angle = GetWheelAngle(ID) - 3.1415;

        if(Angle > 3.1415)
        {
            Angle -= 2 * 3.1415;
        }
        if(Angle < -3.1415)
        {
            Angle += 2 * 3.1415;
        }

        var vec = new Polar((Modules[ID].DriveController.GetEncoderVelocity() / 256.) * 3.1415 * 2, Angle).vector();

        return vec;
    }
    /**
     * Get the amount of wheel
     * @return The amount of wheel
     */
    public int GetWheelCount()
    {
        return _wheelCount;
    }

    public double GetWheelAngleRaw(int ID)
    {
        return Modules[ID].DirectionEncoder.GetRawAngle();
    }
    public double GetWheelAngle(int ID)
    {
        return Modules[ID].DirectionEncoder.GetAngle();
    }

    /**
     * Get the current horizontal axis
     * @return The horizontal axis value
     */
    public double GetInstantHorizontalAxis()
    {
        return _instantHorizontal;
    }
    /**
     * Get the current vertical axis
     * @return The vertical axis value
     */
    public double GetInstanVerticalAxis()
    {
        return _instantVertical;
    }
    /**
     * Get the current rotation axis
     * @return The rotation axis value
     */
    public double GetInstantRotationAxis()
    {
        return _instantRotation;
    }

    int f = 0;

    @Override
    public void DoComponent()
    {
        double dt = Timer.GetDeltaTime();
 
        switch(_mode)
        {
            case Local:
            case World:

            //Adding a rate limiter to the translation joystick to make the driving smoother
            var horizontal = Input.GetAxis("Horizontal");
            var vertical = Input.GetAxis("Vertical");
            var rotation = Input.GetAxis("Rotation");

            var x = _horizontalRateLimiter.Evaluate(horizontal);
            var y = _verticaRateLimiter.Evaluate(vertical);
            var z = _rotationRateLimiter.Evaluate(rotation);

            var mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

            if(mag > 1)
            {
                horizontal = _horizontalRateLimiter.GetCurrent() / mag;
                vertical = _verticaRateLimiter.GetCurrent() / mag;
                rotation =  _rotationRateLimiter.GetCurrent() / mag;
            }

            //Translation vector is equal to the translation joystick axis
            var translation = new Vector2d(_isHorizontalAxisOverride ? _horizontalAxisOverride : _horizontalRateLimiter.GetCurrent() * _speedRatio * -1, (_isVerticalAxisOverride ? _verticalAxisOverride : _verticaRateLimiter.GetCurrent()) * _speedRatio);
            var translationPolar = Polar.fromVector(translation);

            translationPolar.azymuth -= _headingOffset + _headingOffsetManual;

            //Remove the angle of the gyroscope to the azymuth to make the driving relative to the world
            translationPolar.azymuth -= _mode == DrivingMode.World ? ((IMU.getAngle() - _headingOffsetManual) % 360) * 0.01745: 0;

            var rotationAxis = _isRotationAxisOverriden ? _rotationAxisOverride * _roationSpeedRatio : _rotationRateLimiter.GetCurrent() * _roationSpeedRatio;

            for(int i = 0; i < _wheelCount; i++)
            {
                //Each wheel have a predetermined rotation vector based on wheel position
                var rotationVector = Modules[i].RotationVector;
                var scaledRotationVector = new Vector2d(rotationVector.X * rotationAxis, rotationVector.Y * rotationAxis);

                var SumVec = Mathf.Vector2Sum(scaledRotationVector, translationPolar.vector());
                var Sum = Polar.fromVector(SumVec);

                //Radius = Wheel Speed
                //Azymuth = Wheel Heading
          
                //mag cannot be smaller than 0.2 since deadzone is 0.2 
                //So if mag is smaller that mean there is currently no input
                if(mag <= 0.19 && !_isHorizontalAxisOverride && !_isVerticalAxisOverride && !_isRotationAxisOverriden)
                {
                    Sum.radius = 0.01;
                    Sum.azymuth = _lastAngle[i];
                }
                else
                {
                    _lastAngle[i] = Sum.azymuth;
                }

                var deltaAngle = GetDeltaAngle(i, Sum.vector());
                if(Math.abs(deltaAngle) <= UnitConverter.DegreesToRadian(0.5))
                {         
                    deltaAngle = 0;
                }

                var directionCommand = Mathf.Clamp(_directionPID[i].Evaluate(deltaAngle, dt), -1, 1);
                Modules[i].DirectionController.Set(directionCommand);
                _lastDirectionCommand[i] = directionCommand;

                Modules[i].DriveController.Set(Mathf.Clamp(Sum.radius, -1, 1) * _flipDriveMultiplicator[i]);
            }

            f++;
            break;

            case Point:
            var point = GetPoint(Input.GetAxis("Horizontal"), Input.GetAxis("Vertical"));

            var wheelPol = new Polar[_wheelCount];

            var average = 0;
            for(int i = 0; i < _wheelCount; i++)
            {
                wheelPol[i] = Polar.fromVector(Mathf.Vector2Sub(point, Modules[i].WheelPosition));
                average += wheelPol[i].radius;
            }
            average /= (double)_wheelCount;

            for(int i = 0; i < _wheelCount; i++)
            {
                wheelPol[i].radius /= average;
                wheelPol[i].radius *=  Input.GetAxis("Rotation");

                Modules[i].DriveController.Set(Mathf.Clamp(wheelPol[i].radius, -1, 1) * _flipDriveMultiplicator[i]);
                Modules[i].DirectionController.Set(Mathf.Clamp(_directionPID[i].Evaluate(GetDeltaAngle(i, wheelPol[i].vector()), dt), -1, 1));
            }
            break;

            case Tank:
            for(int i = 0; i < _wheelCount; i++)
            {
                //Always Allign Wheel Forward
                Modules[i].DirectionController.Set(Mathf.Clamp(_directionPID[i].Evaluate(GetDeltaAngle(i, new Vector2d(0, 1)), dt), -1, 1));

                if(i + 1 <= _wheelCount / 2)
                {
                    //Right
                }
                else
                {
                    //Left
                }
            }
            break;
        }

        _instantHorizontal = _isHorizontalAxisOverride ? _horizontalAxisOverride : _horizontalRateLimiter.GetCurrent();
        _instantVertical = _isVerticalAxisOverride ? _verticalAxisOverride : _verticaRateLimiter.GetCurrent();
        _instantRotation = _isRotationAxisOverriden ? _rotationAxisOverride : _rotationRateLimiter.GetCurrent();

        //Reset the override state to false at the end of the "frame"
        _isRotationAxisOverriden = false;
        _isVerticalAxisOverride = false;
        _isHorizontalAxisOverride = false;
    }

    @Override
    public void Print()
    {
        System.out.println("(0): " + GetWheelAngleRaw(0) * 2 * Math.PI);
        System.out.println("(1): " + GetWheelAngleRaw(1) * 2 * Math.PI);

        System.out.println("(2): " + GetWheelAngleRaw(2) * 2 * Math.PI);
        System.out.println("(3): " + GetWheelAngleRaw(3) * 2 * Math.PI);
    }

    private double GetDeltaAngle(int ID, Vector2d Target)
    {
        var Source = GetWheelAngle(ID) - 3.1415;

        if(Source > 3.1415)
        {
            Source -= 2 * 3.1415;
        }
        if(Source < -3.1415)
        {
            Source += 2 * 3.1415;
        }

        var xPrim = Target.X * Math.cos(Source) - Target.Y * Math.sin(Source); //Change of coordinate system
        var yPrim = Target.X * Math.sin(Source) + Target.Y * Math.cos(Source);

        var angle = Math.atan2(yPrim * _flipDriveMultiplicator[ID], xPrim * _flipDriveMultiplicator[ID]); //Angle betwen Source and target

        if(Math.abs(angle) > (3.1415 / 2)) //Check if it's faster to just flip the drive motor instead of doing 180° turn
        {
            _flipDriveMultiplicator[ID] *= -1;
        }

        return angle;
    }
    private Vector2d GetPoint(double xAxis, double yAxis)
    {
        return new Vector2d(Math.pow(xAxis, _pointExponent) * _pointDistance, Math.pow(yAxis, _pointExponent) * _pointDistance);
    }

    @Override
    public void initSendable(SendableBuilder builder) 
    {
        for (var i = 0; i < _wheelCount; i++)
        {
            var currentId = i;
            builder.addDoubleProperty("SteerAngle_" + i, () -> GetWheelAngle(currentId), null);
            builder.addDoubleProperty("SteerTargetAngle_" + i, () -> _lastAngle[currentId], null);
            builder.addDoubleProperty("SteerCommand_" + i, () -> _lastDirectionCommand[currentId], null);
        }
    }
}