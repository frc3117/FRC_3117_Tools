package frc.robot.Library.FRC_3117_Tools.Component.CAN;

import edu.wpi.first.hal.CANData;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;

public class DebugCANDevice extends CANDevice
{
    public DebugCANDevice(int deviceID) {
        super(deviceID);
    }

    HashMap<Integer, byte[]> _values = new HashMap<>();

    public byte[] GetValue(int apiID) {
        var data = new CANData();
        if (readPacketNew(apiID, data))
        {
            _values.put(apiID, data.data);
            return data.data;
        }

        if (_values.containsKey(apiID))
            return _values.get(apiID);

        return null;
    }

    public long GetValueLong(int apiID) {
        var data = GetValue(apiID);
        if (data == null)
            return 0;

        var bb = ByteBuffer.wrap(data);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        return bb.getLong();
    }
}
