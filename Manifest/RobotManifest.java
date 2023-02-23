package frc.robot.Library.FRC_3117_Tools.Manifest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;

public class RobotManifest
{
    public static RobotManifestObject ManifestJson;

    public static void LoadFromFile(String path)
    {
        LoadFromFile(new File(path));
    }
    public static void LoadFromFile(File file)
    {
        try
        {
            ManifestJson = new RobotManifestObject((JsonObject)JsonParser.parseReader(new FileReader(file)));
        } catch (Exception e) {}

        RobotManifestDevices.Initialize();
        RobotManifestInputs.Initialize();
    }
}
