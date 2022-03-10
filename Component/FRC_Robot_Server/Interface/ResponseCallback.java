package frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Interface;

import frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Packet.ResponsePacket;

public interface ResponseCallback 
{
    public void Invoke(ResponsePacket value);
}
