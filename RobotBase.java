package frc.robot.Library.FRC_3117_Tools;

import java.util.LinkedHashMap;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.Library.FRC_3117_Tools.Component.Data.InputManager;
import frc.robot.Library.FRC_3117_Tools.Interface.Component;
import frc.robot.Library.FRC_3117_Tools.Math.Timer;

public class RobotBase extends TimedRobot
{
    private LinkedHashMap<String, Component> _componentList;
    private boolean _hasBeenInit;

    @Override
    public void robotInit()
    {
        _componentList = new LinkedHashMap<>();
        _hasBeenInit = false;

        CreateComponentInstance();
        CreateInput();
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
      Timer.ClearEvents();
  
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

    }
    public void CreateInput()
    {

    }

    public void Init()
    {
      Timer.Init();
      InputManager.Init();
  
      for(var component : _componentList.values())
      {
        component.Init();
      }
  
      _hasBeenInit = true;
    }
    public void ComponentLoop()
    {
      Timer.Evaluate();
      InputManager.DoInputManager();
  
      for(var component : _componentList.values())
      {
        component.DoComponent();
      }
    }

    public void AddComponent(String name, Component component)
    {
      _componentList.put(name, component);
    }
  
    @SuppressWarnings("unchecked")
    public <T> T GetComponent(String name)
    {
      try
      {
        return (T)_componentList.get(name);
      }
      catch (Exception ex)
      {
        return null;
      }
    }
}
