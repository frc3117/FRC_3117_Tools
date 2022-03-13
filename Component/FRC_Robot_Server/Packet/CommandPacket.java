package frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Packet;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class CommandPacket 
{
    public CommandPacket() { }
    public CommandPacket(String command, String requestID, Object param)
    {
        Command = command;
        RequestID = requestID;

        Param = new Gson().toJson(param);
    }

    @SerializedName("command")
    public String Command;
    @SerializedName("requestID")
    public String RequestID;
    @SerializedName("param")
    public String Param;

    public <T> T GetParam(Class<T> dataClass)
    {
        var gson = new Gson();
        return gson.fromJson(Param, dataClass);
    }
}
