package frc.robot.Library.FRC_3117_Tools.Component;

import java.util.ArrayList;
import java.util.List;

import frc.robot.Library.FRC_3117_Tools.Component.Data.FunctionSchedulerStep;
import frc.robot.Library.FRC_3117_Tools.Component.Data.FunctionSchedulerStep.StepType;
import frc.robot.Library.FRC_3117_Tools.Interface.Action;
import frc.robot.Library.FRC_3117_Tools.Interface.Component;
import frc.robot.Library.FRC_3117_Tools.Interface.Condition;
import frc.robot.Library.FRC_3117_Tools.Math.Timer;

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

    @Override
    public void Awake()
    {

    }
    
    @Override
    public void Init()
    {
        _isStarted = false;
        _isPaused = false;
        _isCompeted = false;

        _currentStep = -1;

        _repeatCount = 0;
    }

    @Override
    public void Disabled()
    {
        Stop();
    }

    @Override
    public void DoComponent()
    {
        if(_isStarted && !_isPaused)
        {
            var step = _steps.get(_currentStep);
            
            switch(step.GetType())
            {
                case Wait:
                if(Timer.GetCurrentTime() >= _nextStepTime)
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
                if(Timer.GetCurrentTime() >= _nextStepTime)
                {
                    _repeatCount++;

                    if(_repeatCount >= step.GetRepeatCount())
                    {
                        _repeatCount = 0;
                        HandleNextStep();
                    }
                    else
                    {
                        _nextStepTime = Timer.GetCurrentTime() + step.GetDelay();
                    }
                }
                break;

                case RunFor:
                if(Timer.GetCurrentTime() >= _nextStepTime)
                {
                    HandleNextStep();
                }
                else
                {
                    step.CallAction();
                }
                break;
            }
        }
    }

    @Override
    public void Print()
    {
        
    }

    public FunctionScheduler AddWait(double delay)
    {
        _steps.add(new FunctionSchedulerStep(delay));

        return this;
    }
    public FunctionScheduler AddWaituntil(Condition condition)
    {
        _steps.add(new FunctionSchedulerStep(condition));

        return this;
    }
    public FunctionScheduler AddFunction(Action function)
    {
        _steps.add(new FunctionSchedulerStep(function));

        return this;
    }
    public FunctionScheduler AddRepeatFunction(Action function, double delay, int repeat)
    {
        _steps.add(new FunctionSchedulerStep(function, delay, repeat));

        return this;
    }
    public FunctionScheduler AddRunFor(Action function, double delay)
    {
        _steps.add(new FunctionSchedulerStep(function, delay));

        return this;
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

        if(_steps.get(_currentStep).GetType() == StepType.Wait || _steps.get(_currentStep).GetType() == StepType.RunFor)
            _nextStepTime = Timer.GetCurrentTime() + _steps.get(_currentStep).GetDelay();

        DoComponent();
    }
}
