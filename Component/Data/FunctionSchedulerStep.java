package frc.robot.Library.FRC_3117_Tools.Component.Data;

import frc.robot.Library.FRC_3117_Tools.Interface.Action;
import frc.robot.Library.FRC_3117_Tools.Interface.Condition;

/**
 * A class that contain the step info
 */
public class FunctionSchedulerStep 
{
    public FunctionSchedulerStep(double delay)
    {
        _delay = delay;

        _type = StepType.Wait;
    }
    public FunctionSchedulerStep(Condition condition)
    {
        _condition = condition;

        _type = StepType.WaitUntil;
    }
    public FunctionSchedulerStep(Action action)
    {
        _action = action;

        _type = StepType.Function;
    }
    public FunctionSchedulerStep(Action action, double delay, int repeat)
    {
        _action = action;
        _delay = delay;
        _repeat = repeat;

        _type = StepType.RepeatFunction;
    }

    public enum StepType
    {
        Wait,
        WaitUntil,
        Function,
        RepeatFunction
    }

    private double _delay;
    private Condition _condition;
    private Action _action;
    private int _repeat;

    private StepType _type;

    public StepType GetType()
    {
        return _type;
    }

    public double GetDelay()
    {
        return _delay;
    }
    public boolean GetCondition()
    {
        return _condition.Invoke();
    }
    public void CallAction()
    {
        _action.Invoke();
    }
    public int GetRepeatCount()
    {
        return _repeat;
    }
}
