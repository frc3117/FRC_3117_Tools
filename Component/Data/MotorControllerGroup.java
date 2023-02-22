package frc.robot.Library.FRC_3117_Tools.Component.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * A group to control multiple motor controller at the same time
 */
public class MotorControllerGroup 
{
    public MotorControllerGroup()
    {
        _negativeController = new ArrayList<>();
        _positiveController = new ArrayList<>();
    }

    private List<MotorController> _negativeController;
    private List<MotorController> _positiveController;

    /**
     * Add a motor controller to the negative side
     * @param controller The controller to add
     */
    public MotorControllerGroup AddNegativeController(MotorController controller)
    {
        _negativeController.add(controller);
        return this;
    }
    /**
     * Add a motor controller to the positive side
     * @param controller The controller to add
     */
    public MotorControllerGroup AddPositiveController(MotorController controller)
    {
        _positiveController.add(controller);
        return this;
    }

    /**
     * Set the value of the motor controller group
     * @param Value The value to set the controllers to
     */
    public void Set(double Value)
    {
        for(var controller : _negativeController)
        {
            controller.Set(Value * -1);
        }
        for(var controller : _positiveController)
        {
            controller.Set(Value);
        }
    }
    /**
     * Set the brake state of the current motor controller group
     * @param state The brake state
     */
    public void SetBrake(boolean state)
    {
        for(var controller : _negativeController)
        {
            controller.SetBrake(state);
        }
        for(var controller : _positiveController)
        {
            controller.SetBrake(state);
        }
    }
}