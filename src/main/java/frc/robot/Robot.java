package frc.robot;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Library.FRC_3117.Interface.Component;
import frc.robot.Library.FRC_3117.Math.Timer;

public class Robot extends TimedRobot {

  public static Robot instance;

  private List<Component> _componentList;
  private boolean _hasBeenInit = false;

  @Override
  public void robotInit() 
  {
    instance = this;

    _componentList = new ArrayList<>();
    _hasBeenInit = false;

    CreateSystemInstance();

    for(var component : _componentList)
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
    Init();
  }

  @Override
  public void autonomousPeriodic() 
  {

  }

  @Override
  public void teleopInit() 
  {
    if(!_hasBeenInit)
    {
      Init();
    }
  }

  @Override
  public void teleopPeriodic() 
  {
    SystemLoop();
  }

  @Override
  public void disabledInit()
  {

  }

  @Override
  public void disabledPeriodic() 
  {
    
  }

  @Override
  public void testPeriodic() 
  {

  }

  public void CreateSystemInstance()
  {

  }

  public void Init()
  {
    for(var component : _componentList)
    {
      component.Init();
    }

    _hasBeenInit = true;
  }

  public void SystemLoop()
  {
    Timer.Calculate();

    for(var component : _componentList)
    {
      component.DoSystem();
    }
  }

  public static void AddSystem(Component component)
  {
    instance._componentList.add(component);
  }
}
