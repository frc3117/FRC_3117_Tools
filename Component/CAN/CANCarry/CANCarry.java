package frc.robot.Library.FRC_3117_Tools.Component.CAN.CANCarry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import frc.robot.Library.FRC_3117_Tools.Component.CAN.CANDevice;

public class CANCarry extends CANDevice
{
    public enum DeviceType
    {
        DigitalInput((byte)0),
        DigitalOutput((byte)1),
        AnalogInput((byte)2),
        AnalogOutput((byte)3);

        private DeviceType(byte deviceType)
        {
            Value = deviceType;
        }

        public byte Value;
    }
    private enum ApiID
    {
        SetSensor(0),
        RemoveDevice(1);

        private ApiID(int apiID)
        {
            Value = apiID;
        }
        
        public int Value;
    }

    public CANCarry(int deviceId)
    {
        super(deviceId);
    }

    public DigitalInputCAN SetDigitalInput(int sensorId, int pin)
    {
        var bb = CreateByteBuffer();

        bb.put(DeviceType.DigitalInput.Value);
        bb.put((byte)sensorId);
        bb.put((byte)pin);

        writePacket(bb.array(), ApiID.SetSensor.Value);

        return new DigitalInputCAN(this, sensorId);
    }
    public DigitalOutputCAN SetDigitalOutput(int sensorId, int pin)
    {
        var bb = CreateByteBuffer();

        bb.put(DeviceType.DigitalOutput.Value);
        bb.put((byte)sensorId);
        bb.put((byte)pin);

        writePacket(bb.array(), ApiID.SetSensor.Value);

        return new DigitalOutputCAN(this, sensorId);
    }
    
    public AnalogInputCAN SetAnalogInput(int sensorId, int pin)
    {
        var bb = CreateByteBuffer();

        bb.put(DeviceType.AnalogInput.Value);
        bb.put((byte)sensorId);
        bb.put((byte)pin);

        writePacket(bb.array(), ApiID.SetSensor.Value);

        return new AnalogInputCAN(this, sensorId);
    }
    public AnalogOutputCAN SetAnalogOutput(int sensorId, int pin)
    {
        var bb = CreateByteBuffer();

        bb.put(DeviceType.AnalogOutput.Value);
        bb.put((byte)sensorId);
        bb.put((byte)pin);

        writePacket(bb.array(), ApiID.SetSensor.Value);

        return new AnalogOutputCAN(this, sensorId);
    }

    public void RemoveSensor(int sensorId)
    {
        var bb = CreateByteBuffer();

        bb.put((byte)sensorId);

        writePacket(bb.array(), ApiID.RemoveDevice.Value);
    }

    private ByteBuffer CreateByteBuffer()
    {
        var bb = ByteBuffer.allocate(8);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        return bb;
    }
}
