package frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Packet;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Packet 
{
    public Packet() { }
    public Packet(PacketType type, Object data)
    {
        Type = type;

        Data = new Gson().toJson(data);
    }
    public Packet(String recipient, PacketType type, Object data)
    {
        Recipient = recipient;
        Type = type;

        if(data instanceof String)
            Data = (String)data;
        else
            Data = new Gson().toJson(data);
    }

    @SerializedName("recipient")
    public String Recipient;
    @SerializedName("sender")
    public String Sender;
    @SerializedName("type")
    public PacketType Type;
    @SerializedName("data")
    public String Data;

    public <T> T GetData(Class<T> dataClass)
    {
        var gson = new Gson();
        return gson.fromJson(Data, dataClass);
    }
}
