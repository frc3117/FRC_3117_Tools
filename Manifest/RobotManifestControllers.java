package frc.robot.Library.FRC_3117_Tools.Manifest;

import frc.robot.Library.FRC_3117_Tools.Physics.Controll.Interface.ControllerBase;
import frc.robot.Library.FRC_3117_Tools.Reflection.Reflection;

public class RobotManifestControllers
{
    private static RobotManifestObject Controllers;

    public static void Initialize() {
        Controllers = RobotManifest.ManifestJson.GetSubObject("controllers");
    }

    public static void LoadControllers() {
        var controllerClasses = Reflection.GetAllInheritedClass(ControllerBase.class);

        for (var controllerEntry : Controllers.Json.entrySet())
        {
            var controllerManifestObject = new RobotManifestObject(controllerEntry.getValue().getAsJsonObject());

            var controllerName = controllerEntry.getKey();
            var controllerType = controllerManifestObject.GetString("type");

            var cls = controllerClasses.stream().filter(x -> x.getSimpleName().equals(controllerType)).findFirst();
            cls.ifPresent(x -> {
                try {
                    var method = x.getMethod("CreateFromManifest", String.class, RobotManifestObject.class);

                    var controller = (ControllerBase)method.invoke(null, controllerName, controllerManifestObject);
                    ControllerBase.Controllers.put(controllerName, controller);
                } catch (Exception e) { }
            });
        }
    }
}
