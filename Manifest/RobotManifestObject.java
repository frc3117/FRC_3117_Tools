package frc.robot.Library.FRC_3117_Tools.Manifest;

import com.google.gson.JsonObject;
import frc.robot.Library.FRC_3117_Tools.Math.Vector2d;

import java.util.HashMap;

public class RobotManifestObject {
    public RobotManifestObject(JsonObject json) {
        Json = json;
    }

    public JsonObject Json;

    public boolean HasEntry(String name) {
        return Json.has(name);
    }

    public RobotManifestObject GetSubObject(String name) {
        return new RobotManifestObject(Json.getAsJsonObject(name));
    }
    public HashMap<String, RobotManifestObject> GetSubObjects() {
        var subObjects = new HashMap<String, RobotManifestObject>();
        for (var entry : Json.entrySet())
            subObjects.put(entry.getKey(), new RobotManifestObject(entry.getValue().getAsJsonObject()));

        return subObjects;
    }
    public RobotManifestObject[] GetSubObjectArray(String name) {
        var array = Json.getAsJsonArray(name);
        var manifestArray = new RobotManifestObject[array.size()];

        for (var i = 0; i < array.size(); i++) {
            manifestArray[i] = new RobotManifestObject(array.get(i).getAsJsonObject());
        }

        return manifestArray;
    }

    public int GetInt(String name) {
        return Json.get(name).getAsInt();
    }
    public int[] GetIntArray(String name) {
        var array = Json.getAsJsonArray(name);
        var intArray = new int[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            intArray[i] = array.get(i).getAsInt();
        }

        return intArray;
    }

    public double GetDouble(String name) {
        return Json.get(name).getAsDouble();
    }
    public double[] GetDoubleArray(String name) {
        var array = Json.getAsJsonArray(name);
        var doubleArray = new double[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            doubleArray[i] = array.get(i).getAsDouble();
        }

        return doubleArray;
    }

    public String GetString(String name) {
        return Json.get(name).getAsString();
    }
    public String[] GetStringArray(String name) {
        var array = Json.getAsJsonArray(name);
        var stringArray = new String[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            stringArray[i] = array.get(i).getAsString();
        }

        return stringArray;
    }

    public boolean GetBoolean(String name) {
        return Json.get(name).getAsBoolean();
    }
    public boolean[] GetBooleanArray(String name) {
        var array = Json.getAsJsonArray(name);
        var booleanArray = new boolean[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            booleanArray[i] = array.get(i).getAsBoolean();
        }

        return booleanArray;
    }

    public Vector2d GetVector2d(String name) {
        var vecJson = Json.getAsJsonObject(name);

        return new Vector2d(
                vecJson.get("x").getAsDouble(),
                vecJson.get("y").getAsDouble()
        );
    }
    public Vector2d[] GetVector2dArray(String name) {
        var array = Json.getAsJsonArray(name);
        var vector2dArray = new Vector2d[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            var vecJson = Json.getAsJsonObject(name);

            vector2dArray[i] = new Vector2d(
                    vecJson.get("x").getAsDouble(),
                    vecJson.get("y").getAsDouble()
            );
        }

        return vector2dArray;
    }
}
