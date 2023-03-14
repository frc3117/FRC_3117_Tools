package frc.robot.Library.FRC_3117_Tools.Manifest;

import frc.robot.Library.FRC_3117_Tools.Interface.FromManifest;
import frc.robot.Library.FRC_3117_Tools.Physics.Control.Interface.ControllerBase;
import frc.robot.Library.FRC_3117_Tools.Reflection.Reflection;

@FromManifest(EntryName = "controllers", EarlyOnLoadMethod = "LoadControllers")
public class RobotManifestControllers
{
    public static void LoadControllers(String entryName) {
        if (!RobotManifest.ManifestJson.HasEntry(entryName))
            return;

        var controllers = RobotManifest.ManifestJson.GetSubObject(entryName);
        var controllerClasses = Reflection.GetAllInheritedClass(ControllerBase.class);

        for (var controllerEntry : controllers.Json.entrySet())
        {
            try {
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
            } catch (Exception e) { }
        }
    }
}
