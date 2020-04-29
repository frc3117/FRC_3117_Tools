package frc.robot.Component;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Component.Data.FunctionSchedulerStep;
import frc.robot.Interface.Action;
import frc.robot.Interface.Component;
import frc.robot.Interface.Condition;

/**
 * A class to create sequence with delay and condition
 */
public class FunctionScheduler implements Component
{
    public FunctionScheduler()
    {
        _steps = new ArrayList<FunctionSchedulerStep>();
    }
    private FunctionScheduler(List<FunctionSchedulerStep> steps)
    {
        _steps = new ArrayList<FunctionSchedulerStep>(steps);
    }

    private boolean _isStarted = false;
    private boolean _isPaused = false;
    private boolean _isCompeted = false;
    
    private int _currentStep = -1;
    private double _nextStepTime = 0;
    private int _repeatCount = 0;

    private List<FunctionSchedulerStep> _steps;

    public void Init()
    {
        _isStarted = false;
        _isPaused = false;
        _isCompeted = false;

        _currentStep = -1;

        _repeatCount = 0;
    }

    public void DoSystem()
    {
        if(_isStarted && !_isPaused)
        {
            FunctionSchedulerStep step = _steps.get(_currentStep);
            
            switch(step.GetType())
            {
                case Wait:
                if(frc.robot.Math.Timer.GetCurrentTime() >= _nextStepTime)
                {
                    HandleNextStep();
                }
                break;

                case WaitUntil:
                if(step.GetCondition())
                {
                    HandleNextStep();
                }
                break;

                case Function:
                step.CallAction();
                HandleNextStep();
                break;

                case RepeatFunction:
                if(frc.robot.Math.Timer.GetCurrentTime() >= _nextStepTime)
                {
                    _repeatCount++;

                    if(_repeatCount >= step.GetRepeatCount())
                    {
                        _repeatCount = 0;
                        HandleNextStep();
                    }
                    else
                    {
                        _nextStepTime = frc.robot.Math.Timer.GetCurrentTime() + step.GetDelay();
                    }
                }
                break;
            }
        }
    }

    public void AddWait(double delay)
    {
        _steps.add(new FunctionSchedulerStep(delay));
    }
    public void AddWaituntil(Condition condition)
    {
        _steps.add(new FunctionSchedulerStep(condition));
    }
    public void AddFunction(Action function)
    {
        _steps.add(new FunctionSchedulerStep(function));
    }
    public void AddRepeatFunction(Action function, double delay, int repeat)
    {
        _steps.add(new FunctionSchedulerStep(function, delay, repeat));
    }

    public void Start()
    {
        if(!_isStarted)
        {
            _isStarted = true;
            _isPaused = false;
            _isCompeted = false;

            _currentStep = -1;

            _repeatCount = 0;

            HandleNextStep();
        }
    }
    public void Stop()
    {
        if(_isStarted)
        {
            _isStarted = false;
            _isPaused = false;
            _isCompeted = true;

            _currentStep = -1;

            _repeatCount = 0;
        }
    }
    public boolean IsStarted()
    {
        return _isStarted;
    }

    public void Pause()
    {
        if(_isStarted)
        {
            _isPaused = true;
        }
    }
    public void UnPause()
    {
        if(_isStarted)
        {
            _isPaused = false;
        }
    }
    public boolean IsPaused()
    {
        return _isPaused;
    }

    public boolean IsCompleted()
    {
        return _isCompeted;
    }

    public FunctionScheduler Copy()
    {
        return new FunctionScheduler(_steps);
    }

    private void HandleNextStep()
    {
        _currentStep++;

        if(_currentStep == _steps.size())
        {
            _isStarted = false;
            _isPaused = false;

            _isCompeted = true;
            return;
        }
    }
}
