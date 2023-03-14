package frc.robot.Library.FRC_3117_Tools.Manifest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import frc.robot.Library.FRC_3117_Tools.Interface.FromManifest;
import frc.robot.Library.FRC_3117_Tools.Reflection.Reflection;

import java.io.File;
import java.io.FileReader;

public class RobotManifest
{
    public static RobotManifestObject ManifestJson;

    public static void LoadFromFile(String path) {
        LoadFromFile(new File(path));
    }
    public static void LoadFromFile(File file) {
        try
        {
            ManifestJson = new RobotManifestObject((JsonObject)JsonParser.parseReader(new FileReader(file)));

            var fromManifest = Reflection.GetAllClassWithAnnotation(FromManifest.class);
            for (var cls : fromManifest)
            {
                var fromManifestAnnotation = cls.getAnnotation(FromManifest.class);

                var onEarlyLoadMethodName = fromManifestAnnotation.EarlyOnLoadMethod();
                if (!(onEarlyLoadMethodName == null || onEarlyLoadMethodName.isEmpty()))
                {
                    try
                    {
                        var onEarlyLoadMethod = cls.getMethod(onEarlyLoadMethodName, String.class);
                        onEarlyLoadMethod.invoke(null, fromManifestAnnotation.EntryName());
                    }
                    catch (Exception e) { e.printStackTrace(); }
                }

                var onLoadMethodName = fromManifestAnnotation.OnLoadMethod();
                if (!(onLoadMethodName == null || onLoadMethodName.isEmpty()))
                {
                    try
                    {
                        var onLoadMethod = cls.getMethod(onLoadMethodName, String.class);
                        onLoadMethod.invoke(null, fromManifestAnnotation.EntryName());
                    }
                    catch (Exception e) { e.printStackTrace(); }
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
