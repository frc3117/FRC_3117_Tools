package frc.robot.Library.FRC_3117_Tools.Manifest;

import frc.robot.Library.FRC_3117_Tools.Component.Data.Input;
import frc.robot.Library.FRC_3117_Tools.Interface.AxisTransform;
import frc.robot.Library.FRC_3117_Tools.Interface.FromManifest;
import frc.robot.Library.FRC_3117_Tools.Interface.JoystickInput;
import frc.robot.Library.FRC_3117_Tools.Reflection.Reflection;

@FromManifest(EntryName = "inputs", EarlyOnLoadMethod = "LoadInputs")
public class RobotManifestInputs
{
    public static void LoadInputs(String entryName) {
        if (!RobotManifest.ManifestJson.HasEntry(entryName))
            return;

        var inputs = RobotManifest.ManifestJson.GetSubObject(entryName);
        for (var input : inputs.GetSubObjects().entrySet())
        {
            var inputName = input.getKey();
            var manifestObject = input.getValue();

            var positiveSplit = manifestObject.GetString("positive").split("\\.");
            var negativeSplit = manifestObject.GetString("negative").split("\\.");

            var positiveInverted = manifestObject.GetBoolean("positiveInverted");
            var negativeInverted = manifestObject.GetBoolean("negativeInverted");

            var deadzone = manifestObject.GetDouble("deadzone");
            var axisTransform = manifestObject.GetString("axisTransform", "deadzone");

            switch (manifestObject.GetString("type"))
            {
                case "Axis":
                    AddAxis(inputName,
                            positiveSplit,
                            negativeSplit,
                            positiveInverted,
                            negativeInverted,
                            deadzone,
                            axisTransform);
                    break;

                case "Button":
                    AddButton(inputName,
                              positiveSplit,
                              negativeSplit);
                    break;

                default:
                    break;
            }
        }
    }
    private static void AddAxis(String inputName, String[] positiveSplit, String[] negativeSplit, boolean positiveInverted, boolean negativeInverted, double deadzone, String axisTransform) {
        var hasPositive = positiveSplit.length > 1;
        var hasNegative = negativeSplit.length > 1;

        if (!hasPositive && !hasNegative)
            return;

        if (!hasPositive && hasNegative)
            Input.CreateAxis(inputName,
                             Integer.parseInt(negativeSplit[0]),
                             GetInput(negativeSplit[1], negativeSplit[2]),
                             !negativeInverted);
        else
        {
            Input.CreateAxis(inputName,
                             Integer.parseInt(positiveSplit[0]),
                             GetInput(positiveSplit[1], positiveSplit[2]),
                             positiveInverted);

            if (hasNegative)
                Input.SetAxisNegative(inputName,
                                      Integer.parseInt(positiveSplit[0]),
                                      GetInput(negativeSplit[1], negativeSplit[2]),
                                      negativeInverted);
        }

        Input.SetAxisDeadzone(inputName, deadzone);

        AxisTransform transform = null;
        switch (axisTransform) {
            default:
            case "Deadzone":
                transform = Input::DeadzoneTransform;
                break;

            case "Linear":
                transform = Input::LinearTransform;
                break;

            case "Pow":
                transform = (a, d) -> Input.PowTransform(a, d, 1.5);
                break;

            case "NSigmoid":
                transform = (a, d) -> Input.NormalizedSignmoid(a, d, -0.45);
                break;
        }

        Input.SetAxisTransform(inputName, transform);
    }
    private static void AddButton(String inputName, String[] positiveSplit, String[] negativeSplit) {
        var hasPositive = positiveSplit.length > 1;
        var hasNegative = negativeSplit.length > 1;

        if (hasNegative == hasPositive)
            return;

        if (hasPositive)
            Input.CreateButton(inputName,
                               Integer.parseInt(positiveSplit[0]),
                               GetInput(positiveSplit[1], positiveSplit[2]));
        else
            Input.CreateButton(inputName,
                               Integer.parseInt(negativeSplit[0]),
                               GetInput(negativeSplit[1], negativeSplit[2]));
    }

    private static JoystickInput GetInput(String joystickName, String inputName)
    {
        var joysticks = Reflection.GetAllInheritedClass(JoystickInput.class);
        for (var j : joysticks)
        {
            if (j.getSimpleName().equals(joystickName) && j.isEnum())
                return (JoystickInput)Enum.valueOf((Class<Enum>)j, inputName);
        }

        return null;
    }
}
