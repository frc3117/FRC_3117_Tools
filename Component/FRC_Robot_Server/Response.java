package frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server;

import java.time.Duration;
import java.time.LocalDateTime;

import frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Interface.ResponseCallback;
import frc.robot.Library.FRC_3117_Tools.Component.FRC_Robot_Server.Packet.ResponsePacket;

public class Response 
{
    public Response(LocalDateTime sentTime)
    {
        SentTime = sentTime;
    }

    public boolean IsFinished;
    public boolean IsSuccess;

    public LocalDateTime SentTime;
    public LocalDateTime ReceivedTime;

    public ResponsePacket ResponsePacket;
    public ResponseCallback Callback;

    public void SetResponsePacket(ResponsePacket packet)
    {
        ResponsePacket = packet;
        if (Callback != null)
        {
            Callback.Invoke(ResponsePacket);
        }

        ReceivedTime = LocalDateTime.now();
        IsFinished = true;
        IsSuccess = true;
    }
    public void Timeout()
    {
        IsFinished = true;
    }

    public double GetPing()
    {
        return Duration.between(ReceivedTime, SentTime).toMillis();
    }
}
