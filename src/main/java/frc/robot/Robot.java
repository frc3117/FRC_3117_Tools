package frc.robot;

import java.util.LinkedHashMap;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.Vector2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Library.FRC_3117.Component.InputRecorder;
import frc.robot.Library.FRC_3117.Component.Swerve;
import frc.robot.Library.FRC_3117.Component.Data.Input;
import frc.robot.Library.FRC_3117.Component.Data.InputManager;
import frc.robot.Library.FRC_3117.Component.Data.MotorController;
import frc.robot.Library.FRC_3117.Component.Data.WheelData;
import frc.robot.Library.FRC_3117.Component.Data.MotorController.MotorControllerType;
import frc.robot.Library.FRC_3117.Component.Data.Tupple.Pair;
import frc.robot.Library.FRC_3117.Component.Swerve.DrivingMode;
import frc.robot.Library.FRC_3117.Interface.Component;
import frc.robot.Library.FRC_3117.Math.Timer;

public class Robot extends TimedRobot {

  public enum AutonomousMode
  {

  }

  public static Robot instance;
  public static AutonomousMode currentAutonomous;

  private SendableChooser<AutonomousMode> _autoChooser;
  private LinkedHashMap<String, Component> _componentList;
  private boolean _hasBeenInit;

  private InputRecorder _recorder = new InputRecorder();
  boolean _isRecording = false;
  boolean _isPlaying = false;

  @Override
  public void robotInit() 
  {
    instance = this;

    _autoChooser = new SendableChooser<>();
    _componentList = new LinkedHashMap<>();
    _hasBeenInit = false;

    for(AutonomousMode mode : AutonomousMode.values())
    {
      _autoChooser.addOption(mode.toString(), mode);
    }
    SmartDashboard.putData("AutonomousSelector", _autoChooser);

    SmartDashboard.putNumber("TranslationRatio", 1);
    SmartDashboard.putNumber("RotationRatio", 1);

    CreateInput();
    CreateComponentInstance();
    for(var component : _componentList.values())
    {
      component.Awake();
    }
  }

  @Override
  public void robotPeriodic() 
  {

  }

  @Override
  public void autonomousInit() 
  {
    currentAutonomous = _autoChooser.getSelected();

    Init();
  }

  @Override
  public void autonomousPeriodic() 
  {
    ComponentLoop();
  }

  @Override
  public void teleopInit() 
  {
    if(!_hasBeenInit)
    {
      Init();
    }

    //Reset the init state for the next time the robot is eneabled
    _hasBeenInit = false;
  }

  @Override
  public void teleopPeriodic() 
  {
    ComponentLoop();
  }

  @Override
  public void disabledInit()
  {
    for(var component : _componentList.values())
    {
      component.Disabled();
    }
  }

  @Override
  public void disabledPeriodic() 
  {
    
  }

  @Override
  public void testPeriodic() 
  {

  }

  public void CreateComponentInstance()
  {
    /*
    Input.CreateButton("StartRecording", 0, 3);
    Input.CreateButton("StopRecording", 0, 1);
    Input.CreateButton("PlayRecording", 0, 2);
    */

    Input.CreateAxis("Horizontal", 0, 0, false);
    Input.CreateAxis("Vertical", 0, 1, true);
    Input.CreateAxis("Rotation", 0, 2, false);
    Input.SetAxisNegative("Rotation", 0, 3, false);

    Input.SetAxisDeadzone("Horizontal", 0.15);
    Input.SetAxisDeadzone("Vertical", 0.15);
    Input.SetAxisDeadzone("Rotation", 0.15);

    WheelData[] Wheels = {
      new WheelData(new MotorController(MotorControllerType.TalonFX, 20, true), new MotorController(MotorControllerType.SparkMax, 17, true), new Pair<>(0, 0), 0, new Vector2d(1, 1), 0.12578271 - 1.57+ 3.1415 + 3.1415),
      new WheelData(new MotorController(MotorControllerType.TalonFX, 21, true), new MotorController(MotorControllerType.SparkMax, 15, true), new Pair<>(0, 0), 1, new Vector2d(-1, -1), 2.5908172 - 1.57),
      new WheelData(new MotorController(MotorControllerType.TalonFX, 22, true), new MotorController(MotorControllerType.SparkMax, 14, true), new Pair<>(0, 0), 2, new Vector2d(1, -1), 3.236604 - 1.57+ 3.1415),
      new WheelData(new MotorController(MotorControllerType.TalonFX, 23, true), new MotorController(MotorControllerType.SparkMax, 18, true), new Pair<>(0, 0), 3, new Vector2d(-1, 1), 0.64118505 - 1.57 + 3.1415)
    };

    Swerve swerve = new Swerve(Wheels, false);
    swerve.SetPIDGain(0, 1, 0, 0);
    swerve.SetPIDGain(1, 1, 0, 0);
    swerve.SetPIDGain(2, 1, 0, 0);
    swerve.SetPIDGain(3, 1, 0, 0);

    swerve.SetCurrentMode(DrivingMode.World);

    AddComponent("Swerve", swerve);
  }

  public void CreateInput()
  {

  }

  public void Init()
  {
    InputManager.Init();

    for(var component : _componentList.values())
    {
      component.Init();
    }

    _hasBeenInit = true;
  }

  public void ComponentLoop()
  {
    /*
    if(Input.GetButton("StartRecording"))
    {
      _recorder = new InputRecorder();
      _isRecording = true;
    }
    if(Input.GetButton("StopRecording"))
    {
      _isRecording = false;
    }
    if(Input.GetButton("PlayRecording") && !_isPlaying)
    {
      _isRecording = false;
      InputManager.StartPlayback(_recorder.GetPlayback());
    }
    if(_isRecording)
    {
      _recorder.AddFrame();
    }
    */

    InputManager.DoInputManager();
    Timer.Evaluate();

    double translationRatio = SmartDashboard.getNumber("TranslationRatio", 1);
    double rotationRatio = SmartDashboard.getNumber("RotationRatio", 1);

    Swerve swerve = GetComponent("Swerve");
    swerve.OverrideHorizontalAxis(InputManager.GetAxis("Horizontal") * -1 * translationRatio);
    swerve.OverrideVerticalAxis(InputManager.GetAxis("Vertical") * translationRatio);
    swerve.OverrideRotationAxis(InputManager.GetAxis("Rotation") * rotationRatio);

    for(var component : _componentList.values())
    {
      component.DoComponent();
    }
  }

  public static void AddComponent(String name, Component component)
  {
    instance._componentList.put(name, component);
  }

  @SuppressWarnings("unchecked")
  public static <T> T GetComponent(String name)
  {
    try
    {
      return (T)instance._componentList.get(name);
    }
    catch (Exception ex)
    {
      return null;
    }
  }
}
