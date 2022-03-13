package frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Packet;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class DataPacket 
{
    public DataPacket() { }
    public DataPacket(String name, Object data)
    {
        Name = name;

        Data = new Gson().toJson(data);
    }

    @SerializedName("name")
    public String Name;
    @SerializedName("data")
    public String Data;

    public <T> T GetData(Class<T> dataClass)
    {
        var gson = new Gson();
        return gson.fromJson(Data, dataClass);
    }
}
