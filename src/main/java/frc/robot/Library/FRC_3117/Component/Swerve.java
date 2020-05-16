package frc.robot.Library.FRC_3117.Component;

import frc.robot.Library.FRC_3117.Component.Data.Input;
import frc.robot.Library.FRC_3117.Component.Data.InputManager;
import frc.robot.Library.FRC_3117.Component.Data.MotorController;
import frc.robot.Library.FRC_3117.Component.Data.SolenoidValve;
import frc.robot.Library.FRC_3117.Component.Data.WheelData;
import frc.robot.Library.FRC_3117.Component.Data.MotorController.MotorControllerType;
import frc.robot.Library.FRC_3117.Interface.Component;
import frc.robot.Library.FRC_3117.Math.Mathf;
import frc.robot.Library.FRC_3117.Math.PID;
import frc.robot.Library.FRC_3117.Math.Polar;
import frc.robot.Library.FRC_3117.Math.RateLimiter;
import frc.robot.Library.FRC_3117.Math.Timer;

import com.analog.adis16448.frc.ADIS16448_IMU;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Swerve implements Component {
    public Swerve(WheelData[] WheelsData)
    {
        _wheelCount = WheelsData.length;

        _driveMotor = new MotorController[_wheelCount];
        _directionMotor = new MotorController[_wheelCount];
        _directionEncoder = new AnalogInput[_wheelCount];
        _shifterValve = new SolenoidValve[_wheelCount];
        _rotationVector = new Vector2d[_wheelCount];
        _wheelPosition = new Vector2d[_wheelCount];
        _directionPID = new PID[_wheelCount];
        _angleOffset = new double[_wheelCount];

        _flipAngleOffset = new double[_wheelCount];
        _flipDriveMultiplicator = new double[_wheelCount];

        _lastAngle = new double[_wheelCount];

        //Set default value of the rate limiter to "Infinity" (value that will make the rate limiter go instantly to the target)
        _horizontalRateLimiter = new RateLimiter(10000, 0);
        _verticaRateLimiter = new RateLimiter(10000, 0);
        _rotationRateLimiter = new RateLimiter(10000, 0);

        //Initializing all component of the swerve swerve system
        for(int i  = 0; i < _wheelCount; i++)
        {
            _driveMotor[i] = new MotorController(MotorControllerType.TalonFX, WheelsData[i].DriveChannel, true);
            _directionMotor[i] = new MotorController(MotorControllerType.TalonSRX , WheelsData[i].DirectionChannel, false);
            _directionEncoder[i] = new AnalogInput(WheelsData[i].DirectionEncoderChannel);
            _shifterValve[i] = SolenoidValve.CreateSingle(WheelsData[i].ShifterChannel, 0);
            _shifterValve[i].SetState(false);

            _rotationVector[i] = WheelsData[i].GetWheelRotationVector();
            _wheelPosition[i] = WheelsData[i].WheelPosition;
            _angleOffset[i] = WheelsData[i].AngleOffset;

            _flipAngleOffset[i] = 0;
            _flipDriveMultiplicator[i] = 1;

            _directionPID[i] = new PID();
        }
    }

    public enum DrivingMode
    {
        Point,
        Local,
        World,
        Tank
    }
    public enum ShifterMode
    {
        Automatic,
        Manual
    }

    private int _wheelCount;

    private MotorController[] _driveMotor;
    private MotorController[] _directionMotor;
    private AnalogInput[] _directionEncoder;
    private SolenoidValve[] _shifterValve;
    private Vector2d[] _rotationVector;

    private Vector2d[] _wheelPosition;

    private ADIS16448_IMU _IMU;

    private double[] _angleOffset;

    private double[] _flipAngleOffset;
    private double[] _flipDriveMultiplicator;

    private double[] _lastAngle;

    private PID[] _directionPID;

    private DrivingMode _mode = DrivingMode.Local;
    private ShifterMode _shiftMode = ShifterMode.Manual;
    private double _speedRatio = 0.5;
    private double _roationSpeedRatio = 0.5;

    private double _pointExponent = 0;
    private double _pointDistance = 0;

    private double _headingOffset;

    private RateLimiter _horizontalRateLimiter;
    private RateLimiter _verticaRateLimiter;
    private RateLimiter _rotationRateLimiter;

    private boolean _shiftState = false;

    private double _minShiftTime;
    private double _lastAutomaticShiftTime;

    private double _downshiftThreshold;
    private double _upshiftThreshold;

    private boolean _isShiftOverriden = false;
    private boolean _overridenShiftState = false;

    private double _rotationAxisOverride = 0;
    private boolean _isRotationAxisOverriden = false;

    private double _horizontalAxisOverride = 0;
    private boolean _isHorizontalAxisOverride = false;

    private double _verticalAxisOverride = 0;
    private boolean _isVerticalAxisOverride = false;

    private double _instantHorizontal = 0;
    private double _instantVertical = 0;
    private double _instantRotation = 0;

    public void Awake()
    {
        
    }

    public void Init()
    {
        
    }

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
     * Set the current shift mode of the swerve drive
     * @param Mode The shift mode
     */
    public void SetShifterMode(ShifterMode Mode)
    {
        _shiftMode = Mode;
    }
    /**
     * Set the current shift mode of the swerve drive
     * @param Mode The shift mode
     */
    public void SetShifterMode(int Mode)
    {
        switch(Mode)
        {
            case 0:
            _shiftMode = ShifterMode.Automatic;
            break;

            case 1:
            _shiftMode = ShifterMode.Manual;
            break;
        }
    }
    /**
     * Set the minimum time between automatic shift
     * @param Time The minimum time between automatic shift
     */
    public void SetShiftMinTime(double Time)
    {
        _minShiftTime = Time;
    }
    /**
     * Set the threshold of the automatic shifter
     * @param Downshift
     * @param Upshift
     */
    public void SetShiftThreshold(double Downshift, double Upshift)
    {
        _downshiftThreshold = Downshift;
        _upshiftThreshold = Upshift;
    }
    
    /**
     * Initialize the IMU
     */
    public void InitIMU()
    {
        _IMU = new ADIS16448_IMU();

        RecalibrateIMU();
    }
    /**
     * Recalibrate the IMU
     */
    public void RecalibrateIMU()
    {
        _IMU.reset();
        _IMU.calibrate();

        _headingOffset = (_IMU.getGyroAngleZ() / 180) * 3.1415 + 3.1415;
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
     * Get the current shift state
     * @return The current shift state
     */
    public boolean GetGear()
    {
        return _shiftState;
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
     * Override the current shift state
     * @param Speed The shift state to set
     */
    public void OverrideShift(int Speed)
    {
        if(Speed == 0)
        {
            _overridenShiftState = true;
            _isShiftOverriden = true;
        }
        else if (Speed == 1)
        {
            _overridenShiftState = false;
            _isShiftOverriden = true;
        }
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
        return (_IMU.getGyroAngleZ() / 180) * 3.1415 - _headingOffset;
    }
    /**
     * Get the current estimated position of the swerve drive
     */
    public Vector2d GetPostion()
    {
        return new Vector2d();
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
        double Angle = ((_directionEncoder[ID].getValue() / 4096f) * 2 * 3.1415f) - _angleOffset[ID] - 3.1415;

        if(Angle > 3.1415)
        {
            Angle -= 2 * 3.1415;
        }
        if(Angle < -3.1415)
        {
            Angle += 2 * 3.1415;
        }

        Vector2d vec = new Polar((_driveMotor[ID].GetEncoderVelocity() / 256.) * 3.1415 * 2, Angle).vector();

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
    public void DoComponent()
    {
        /*
        Robot.Println("(0): " + ((_directionEncoder[0].getValue() / 4096f) * 2 * 3.1415f));
        Robot.Println("(1): " + ((_directionEncoder[1].getValue() / 4096f) * 2 * 3.1415f));

        Robot.Println("(2): " + ((_directionEncoder[2].getValue() / 4096f) * 2 * 3.1415f));
        Robot.Println("(3): " + ((_directionEncoder[3].getValue() / 4096f) * 2 * 3.1415f));
        */

        double dt = Timer.GetDeltaTime();

        //Override the shift state of the robot for a peculiar task
        if(_isShiftOverriden)
        {
            if(_shiftState != _overridenShiftState)
            {
                for(int i = 0; i < _wheelCount; i++)
                {
                    _shifterValve[i].SetState(_overridenShiftState);   
                }

                _shiftState = _overridenShiftState;
            }
        }
        else
        {
            switch(_shiftMode)
            {
                case Automatic:
                //Autoshift only if the delta time is reach and the robot velocity reach the threshold
                if(Timer.GetCurrentTime() - _lastAutomaticShiftTime >= _minShiftTime)
                {
                    Vector2d velocityVector = new Vector2d(0, 0);

                    for(int i = 0; i < _wheelCount; i++)
                    {
                        velocityVector = Mathf.Vector2Sum(velocityVector, GetWheelVector(i));
                    }
                    double Mag = velocityVector.magnitude();

                    if(_shiftState && Mag<= _downshiftThreshold)
                    {
                        _lastAutomaticShiftTime = Timer.GetCurrentTime();
                        _shiftState = false;

                        for(int i = 0; i < _shifterValve.length; i++)
                        {
                            _shifterValve[i].SetState(_shiftState);
                        }
                    }
                    else if (!_shiftState && Mag>= _upshiftThreshold)
                    {
                        _lastAutomaticShiftTime = Timer.GetCurrentTime();
                        _shiftState = true;

                        for(int i = 0; i < _shifterValve.length; i++)
                        {
                            _shifterValve[i].SetState(_shiftState);
                        }

                        _horizontalRateLimiter.SetCurrent(_horizontalRateLimiter.GetCurrent() * 0.4);
                        _verticaRateLimiter.SetCurrent(_verticaRateLimiter.GetCurrent() * 0.4);
                        _rotationRateLimiter.SetCurrent(_rotationRateLimiter.GetCurrent() * 0.4);
                    }
                }

                SmartDashboard.putBoolean("Gear", _shiftState);
                break;

                case Manual:
                
                if(InputManager.GetButtonDown("GearShift"))
                {
                    _shiftState = !_shiftState;

                    for(int i = 0; i < _shifterValve.length; i++)
                    {
                        _shifterValve[i].SetState(_shiftState);
                    }
                }

                SmartDashboard.putBoolean("Gear", _shiftState);

                break;
            }
        }

        _isShiftOverriden = false;

        switch(_mode)
        {
            case Local:
            case World:

            //Adding a rate limiter to the translation joystick to make the driving smoother
            double horizontal = Input.GetAxis("Horizontal");
            double vertical = Input.GetAxis("Vertical");
            double rotation = Input.GetAxis("Rotation");

            double x = _horizontalRateLimiter.Evaluate(horizontal);
            double y = _verticaRateLimiter.Evaluate(vertical);
            double z = _rotationRateLimiter.Evaluate(rotation);

            double mag = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));

            if(mag > 1)
            {
                horizontal = _horizontalRateLimiter.GetCurrent() / mag;
                vertical = _verticaRateLimiter.GetCurrent() / mag;
                rotation =  _rotationRateLimiter.GetCurrent() / mag;
            }

            //Translation vector is equal to the translation joystick axis
            Vector2d translation = new Vector2d(_isHorizontalAxisOverride ? _horizontalAxisOverride : _horizontalRateLimiter.GetCurrent() * _speedRatio * -1, (_isVerticalAxisOverride ? _verticalAxisOverride : _verticaRateLimiter.GetCurrent()) * _speedRatio);
            Polar translationPolar = Polar.fromVector(translation);

            //Remove the angle of the gyroscope to the azymuth to make the driving relative to the world
            translationPolar.azymuth -= _mode == DrivingMode.World ? (_IMU.getGyroAngleZ() % 360) * 0.01745 + 3.1415: 0;

            double rotationAxis = _isRotationAxisOverriden ? _rotationAxisOverride * _roationSpeedRatio : _rotationRateLimiter.GetCurrent() * _roationSpeedRatio;

            for(int i = 0; i < _wheelCount; i++)
            {
                //Each wheel have a predetermined rotation vector based on wheel position
                Vector2d scaledRotationVector = new Vector2d(_rotationVector[i].x * rotationAxis, _rotationVector[i].y * rotationAxis);               

                Vector2d SumVec = Mathf.Vector2Sum(scaledRotationVector, translationPolar.vector());
                Polar Sum = Polar.fromVector(SumVec);

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

                _driveMotor[i].Set(Mathf.Clamp(Sum.radius, -1, 1) * _flipDriveMultiplicator[i]);

                double deltaAngle = GetDeltaAngle(i, Sum.vector());
                if(Math.abs(deltaAngle) <= 0.5 * Mathf.DEG_2_RAD)
                {         
                    deltaAngle = 0;
                }

                _directionMotor[i].Set(Mathf.Clamp(_directionPID[i].Evaluate(deltaAngle, dt), -1, 1));
            }

            f++;
            break;

            case Point:
            Vector2d point = GetPoint(Input.GetAxis("Horizontal"), Input.GetAxis("Vertical"));

            Polar[] wheelPol = new Polar[_wheelCount];

            double average = 0;
            for(int i = 0; i < _wheelCount; i++)
            {
                wheelPol[i] = Polar.fromVector(Mathf.Vector2Sub(point, _wheelPosition[i]));
                average += wheelPol[i].radius;
            }
            average /= (double)_wheelCount;

            for(int i = 0; i < _wheelCount; i++)
            {
                wheelPol[i].radius /= average;
                wheelPol[i].radius *=  Input.GetAxis("Rotation");

                _driveMotor[i].Set(Mathf.Clamp(wheelPol[i].radius, -1, 1) * _flipDriveMultiplicator[i]);
                _directionMotor[i].Set(Mathf.Clamp(_directionPID[i].Evaluate(GetDeltaAngle(i, wheelPol[i].vector()), dt), -1, 1));
            }
            break;

            case Tank:
            for(int i = 0; i < _wheelCount; i++)
            {
                //Always Allign Wheel Forward
                _directionMotor[i].Set(Mathf.Clamp(_directionPID[i].Evaluate(GetDeltaAngle(i, new Vector2d(0, 1)), dt), -1, 1));

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

        //Reset the overriden state to false a the end of the "frame"
        _isRotationAxisOverriden = false;
        _isVerticalAxisOverride = false;
        _isHorizontalAxisOverride = false;
    }

    private double GetDeltaAngle(int ID, Vector2d Target)
    {
        double Source = ((_directionEncoder[ID].getValue() / 4096f) * 2 * 3.1415f) - _angleOffset[ID] - 3.1415;

        if(Source > 3.1415)
        {
            Source -= 2 * 3.1415;
        }
        if(Source < -3.1415)
        {
            Source += 2 * 3.1415;
        }

        double xPrim = Target.x * Math.cos(Source) - Target.y * Math.sin(Source); //Change of coordinate system
        double yPrim = Target.x * Math.sin(Source) + Target.y * Math.cos(Source);

        double angle = Math.atan2(yPrim * _flipDriveMultiplicator[ID], xPrim * _flipDriveMultiplicator[ID]); //Angle betwen Source and target

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
}