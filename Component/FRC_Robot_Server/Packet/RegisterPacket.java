package frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Packet;

import com.google.gson.annotations.SerializedName;

public class RegisterPacket 
{
    public RegisterPacket() { }
    public RegisterPacket(String name)
    {
        Name = name;
    }

    @SerializedName("name")
    public String Name;
}
