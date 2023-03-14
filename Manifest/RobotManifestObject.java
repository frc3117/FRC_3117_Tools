package frc.robot.Library.FRC_3117_Tools.Manifest;

import com.google.gson.JsonObject;
import frc.robot.Library.FRC_3117_Tools.Math.Vector2d;
import frc.robot.Library.FRC_3117_Tools.Math.Vector3d;

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
        return GetInt(name, 0);
    }
    public int GetInt(String name, int defaultValue) {
        if (Json.has(name))
            return Json.get(name).getAsInt();

        return defaultValue;
    }
    public int[] GetIntArray(String name) {
        if (!Json.has(name))
            return null;

        var array = Json.getAsJsonArray(name);
        var intArray = new int[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            intArray[i] = array.get(i).getAsInt();
        }

        return intArray;
    }

    public double GetDouble(String name) {
        return GetDouble(name, 0);
    }
    public double GetDouble(String name, double defaultValue) {
        if (Json.has(name))
            return Json.get(name).getAsDouble();

        return defaultValue;
    }
    public double[] GetDoubleArray(String name) {
        if (!Json.has(name))
            return null;

        var array = Json.getAsJsonArray(name);
        var doubleArray = new double[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            doubleArray[i] = array.get(i).getAsDouble();
        }

        return doubleArray;
    }

    public String GetString(String name) {
        return GetString(name, "");
    }
    public String GetString(String name, String defaultValue) {
        if (Json.has(name))
            return Json.get(name).getAsString();

        return defaultValue;
    }
    public String[] GetStringArray(String name) {
        if (!Json.has(name))
            return null;

        var array = Json.getAsJsonArray(name);
        var stringArray = new String[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            stringArray[i] = array.get(i).getAsString();
        }

        return stringArray;
    }

    public boolean GetBoolean(String name) {
        return GetBoolean(name,false);
    }
    public boolean GetBoolean(String name, boolean defaultValue) {
        if (Json.has(name))
            return Json.get(name).getAsBoolean();

        return defaultValue;
    }
    public boolean[] GetBooleanArray(String name) {
        if (!Json.has(name))
            return null;

        var array = Json.getAsJsonArray(name);
        var booleanArray = new boolean[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            booleanArray[i] = array.get(i).getAsBoolean();
        }

        return booleanArray;
    }

    public Vector2d GetVector2d(String name) {
        return GetVector2d(name, Vector2d.Zero());
    }
    public Vector2d GetVector2d(String name, Vector2d defaultValue) {
        if (!Json.has(name))
            return defaultValue;

        var vecJson = Json.getAsJsonObject(name);
        return new Vector2d(
                vecJson.get("x").getAsDouble(),
                vecJson.get("y").getAsDouble()
        );
    }
    public Vector2d[] GetVector2dArray(String name) {
        if (!Json.has(name))
            return null;

        var array = Json.getAsJsonArray(name);
        var vector2dArray = new Vector2d[array.size()];

        for (var i = 0; i < array.size(); i++)
        {
            var vecJson = array.get(i).getAsJsonObject();

            vector2dArray[i] = new Vector2d(
                    vecJson.get("x").getAsDouble(),
                    vecJson.get("y").getAsDouble()
            );
        }

        return vector2dArray;
    }

    public Vector3d GetVector3d(String name) {
        return GetVector3d(name, Vector3d.Zero());
    }
    public Vector3d GetVector3d(String name, Vector3d defaultValue) {
        if (!Json.has(name))
            return defaultValue;

        var vecJson = Json.getAsJsonObject(name);
        return new Vector3d(
                vecJson.get("x").getAsDouble(),
                vecJson.get("y").getAsDouble(),
                vecJson.get("z").getAsDouble()
        );
    }
    public Vector3d[] GetVector3dArray(String name) {
        if (!Json.has(name))
            return null;

        var array = Json.getAsJsonArray(name);
        var vector3dArray = new Vector3d[array.size()];

        for (var i = 0; i < array.size(); i++) {
            var vecJson = array.get(i).getAsJsonObject();

            vector3dArray[i] = new Vector3d(
                    vecJson.get("x").getAsDouble(),
                    vecJson.get("y").getAsDouble(),
                    vecJson.get("z").getAsDouble()

            );
        }

        return vector3dArray;
    }
}
