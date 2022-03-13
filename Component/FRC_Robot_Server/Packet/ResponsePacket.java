package frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Packet;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ResponsePacket 
{
    public ResponsePacket() {}
    public ResponsePacket(String requestID, Boolean isSuccess, Object data)
    {
        RequestID = requestID;
        IsSuccess = isSuccess;

        Data = new Gson().toJson(data);
    }

    @SerializedName("requestID")
    public String RequestID;
    @SerializedName("isSuccess")
    public boolean IsSuccess;
    @SerializedName("data")
    public String Data;

    public <T> T GetData(Class<T> dataClass)
    {
        var gson = new Gson();
        return gson.fromJson(Data, dataClass);
    }
}
