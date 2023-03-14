package frc.robot.Library.FRC_3117_Tools.Component.Data;

import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Library.FRC_3117_Tools.Interface.JoystickInput;

/**
 * The input manager
 */
public class Input implements Sendable
{
    public enum XboxButton implements JoystickInput
    {
        A(1),
        B(2),
        X(3),
        Y(4),
        LB(5),
        RB(6),
        BACK(7),
        START(8),
        LJ(9),
        RJ(10);

        private final int _value;
        private XboxButton(int value)
        {
            _value = value;
        }

        public int GetValue()
        {
            return _value;
        }
    }
    public enum XboxAxis implements JoystickInput
    {
        LEFTX(0),
        LEFTY(1),
        LEFT_TRIGGER(2),
        RIGHT_TRIGGER(3),
        RIGHTX(4),
        RIGHTY(5);
        
        private final int _value;
        private XboxAxis(int value)
        {
            _value = value;
        }

        public int GetValue()
        {
            return _value;
        }
    }

    private Input(int ID,  int input,  String inputName, boolean invert) {
        if (!_joysticks.containsKey(ID)) {
            _joysticks.put(ID, new Joystick(ID));
        }

        _inputName = inputName;
        _joystickID = ID;
        _input = input;
        _isInputNegativeInverted = invert;

        _deadzone.put(inputName, 0.);
        _inputs.put(inputName, this);

        SmartDashboard.putData("Inputs/" + inputName, this);
    }

    private static HashMap<String, Input> _inputs = new HashMap<String, Input>();
    private static HashMap<String, Double> _deadzone = new HashMap<String, Double>();
    private static HashMap<Integer, Joystick> _joysticks = new HashMap<Integer, Joystick>();

    private String _inputName;
    private int _joystickID;
    private int _joystickIDNegative = 9999;
    private int _input; // The ID of the input
    private boolean _isInputInverted;
    private int _inputNegative = 9999;
    private boolean _isInputNegativeInverted;

    /**
     * Register an axis
     * @param Name The name of the axis to register
     * @param JoystickID The joystick id from where the axis come from
     * @param InputID The axis id of the axix
     * @param invert If the value will be multiply by -1
     */
    public static void CreateAxis(String Name, int JoystickID, JoystickInput InputID, boolean invert) 
    {
        new Input(JoystickID, InputID.GetValue(), "Axis/" + Name, invert);
    }
    /**
     * Register a button
     * @param Name The name of the button to register
     * @param JoystickID The joystick id from where the button come from
     * @param InputID The button id of the button
     */
    public static void CreateButton(String Name, int JoystickID, JoystickInput InputID ) 
    {
        new Input(JoystickID, InputID.GetValue(), "Button/" + Name, false);
    }

    /**
     * Check if a axis is registred
     * @param Name The name of the axis to check
     * @return If the axis is registred
     */
    public static boolean ContainAxis(String Name)
    {
        return _inputs.containsKey("Axis/" + Name);
    }
    /**
     * Check if a button is registred
     * @param Name The name of the button to check
     * @return If the button is registred
     */
    public static boolean ContainButton(String Name)
    {
        return _inputs.containsKey("Button/" + Name);
    }

    /**
     * Set a negative axis to an existing axis
     * @param Name The name of the axis to add a negative to
     * @param JoystickID The id of the joystick where the negative come from
     * @param InputID The axis id of the negative axis
     * @param invert If the value will be multiply by -1
     */
    public static void SetAxisNegative(String Name, int JoystickID, JoystickInput InputID , boolean invert)
    {
        if(!_joysticks.containsKey(JoystickID))
        {
            _joysticks.put(JoystickID, new Joystick(JoystickID));
        }

        var current = _inputs.get("Axis/" + Name);

        current._joystickIDNegative = JoystickID;
        current._inputNegative = InputID.GetValue();

        current._isInputNegativeInverted = invert;
    }

    /**
     * Set the axis deadzone to an existing axis
     * @param Name The name of the axis
     * @param Deadzone The deadzone to set
     */
    public static void SetAxisDeadzone(String Name, double Deadzone)
    {
        _deadzone.put("Axis/" + Name, Deadzone);
    }

    /**
     * Unregister all the axis and buttons
     */
    public static void Reset() {
        _inputs.clear();
        _joysticks.clear();
    }

    /**
     * Get the current value of an axis
     * @param Name The name of the axis to get the value from
     * @return The current value of the axis
     */
    public static double GetAxis(String Name) {
        var fullName = "Axis/" + Name;
        if (!_inputs.containsKey(fullName))
            return 0;

        var current = _inputs.get(fullName);
        var negative = 0.;

        if(current._joystickIDNegative != 9999)
        {
            negative = _joysticks.get(current._joystickIDNegative).getRawAxis(current._inputNegative) * (current._isInputNegativeInverted ? -1 : 1);
        }

        var val = (_joysticks.get(current._joystickID).getRawAxis(current._input) * (current._isInputInverted ? -1 : 1)) - negative;

        if(Math.abs(val) <= _deadzone.get(fullName))
        {
            return 0;
        }

        return val;
    }

    /**
     * Get the current state of a button 
     * @param Name The name of the button to get the state from
     * @return The current state of the button
     */
    public static boolean GetButton(String Name) {
        var fullName = "Button/" + Name;
        if (!_inputs.containsKey(fullName))
            return false;

        var current = _inputs.get(fullName);
        return _joysticks.get(current._joystickID).getRawButton(current._input);
    }

    /**
     * Get all the registred axis
     * @return All the registred axis
     */
    public static String[] GetAllAxis()
    {
        var keys = new ArrayList<String>();
        
        for (var key : _inputs.keySet())
        {
            if(key.contains("Axis/"))
            {
                keys.add(key.split("/", 2)[1]);
            }
        }

        return keys.toArray(new String[keys.size()]);
    }
    /**
     * Get all the registred button
     * @return All the registred button
     */
    public static String[] GetAllButton()
    {
        var keys = new ArrayList<String>();
        
        for (var key : _inputs.keySet())
        {
            if(key.contains("Button/"))
            {
                keys.add(key.split("/", 2)[1]);
            }
        }

        return keys.toArray(new String[keys.size()]);
    }

    /**
     * Get all the registred input
     * @return All the registred input
     */
    public String[] GetAllInput()
    {
        return _inputs.keySet().toArray(new String[_inputs.size()]);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        var split = _inputName.split("/", 2);

        var name = split[1];
        var isAxis = split[0].equals("Axis");
        if (isAxis)
        {
            builder.addStringProperty("Type", () -> "Axis", null);
            builder.addDoubleProperty("Value", () -> GetAxis(name), null);
            builder.addDoubleProperty("Deadzone", () -> _deadzone.get(_inputName), (val) -> _deadzone.put(_inputName, val));
            builder.addBooleanProperty("Inverted", () -> _isInputInverted, (val) -> _isInputInverted = val);
        }
        else
        {
            builder.addStringProperty("Type", () -> "Button", null);
            builder.addBooleanProperty("Value", () -> GetButton(name), null);
        }
    }
}