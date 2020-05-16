package frc.robot;

import java.util.LinkedHashMap;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Library.FRC_3117.Interface.Component;
import frc.robot.Library.FRC_3117.Math.Timer;

public class Robot extends TimedRobot {

  public static Robot instance;

  private LinkedHashMap<String, Component> _componentList;
  private boolean _hasBeenInit = false;

  @Override
  public void robotInit() 
  {
    instance = this;

    _componentList = new LinkedHashMap<>();
    _hasBeenInit = false;

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
    ComponentLoop();
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

  public void CreateComponentInstance()
  {

  }

  public void Init()
  {
    for(var component : _componentList.values())
    {
      component.Init();
    }

    _hasBeenInit = true;
  }

  public void ComponentLoop()
  {
    Timer.Calculate();

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
    return (T)instance._componentList.get(name);
  }
}
