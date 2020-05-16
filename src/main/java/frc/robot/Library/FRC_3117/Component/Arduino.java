package frc.robot.Library.FRC_3117.Component;

import java.util.Arrays;
import java.util.HashMap;

import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Library.FRC_3117.Interface.Action;
import frc.robot.Library.FRC_3117.Interface.AnalogReadCallback;
import frc.robot.Library.FRC_3117.Interface.Component;
import frc.robot.Library.FRC_3117.Interface.DigitalReadCallback;

/**
 * A class to comunicate to an arduino through a serial port
 */
public class Arduino implements Component
{
    public Arduino(int baudRate, SerialPort.Port port)
    {
        _port = port;
        _baudRate = baudRate;

        _methods = new HashMap<>();
        _methods.put("DigitalRead", this::ManageDigitalRead);
        _methods.put("AnalogRead", this::ManageAnalogRead);
        _methods.put("Print", () -> System.out.println(GetParam((0))));

        Open();
    }
    public Arduino(int baudRate,SerialPort.Port port, boolean open)
    {
        _port = port;
        _baudRate = baudRate;

        _methods = new HashMap<>();

        _methods.put("DigitalRead", this::ManageDigitalRead);
        _methods.put("AnalogRead", this::ManageAnalogRead);
        _methods.put("Print", () -> System.out.println(GetParam((0))));

        if(open)
            Open();
    }
   
    public enum ArduinoPinMode
    {
        Input(0),
        Output(1),
        Input_Pullup(2);

        private final int value;
        private ArduinoPinMode(int value)
        {
            this.value = value;
        }
        public int GetValue()
        {
            return value;
        }

        @Override
        public String toString()
        {
            return Integer.toString(value);
        }
    }

    private SerialPort _serial;
    private SerialPort.Port _port;
    private int _baudRate;

    private HashMap<String, Action> _methods;

    private HashMap<Integer, DigitalReadCallback> _digitalReadCallback;
    private Action _anyPinDigitalReadCallback;

    private HashMap<Integer, AnalogReadCallback> _analogReadCallback;
    private Action _anyPinAnalogReadCallback;

    private boolean _isOpen = false;
    private boolean _isReading = false;

    private byte _byteToRead;

    private String[] _currentParams;

    public void Awake()
    {

    }

    public void Init()
    {
        
    }

    public void DoComponent()
    {
        //Data available
        while(_serial.getBytesReceived() > 0)
        {
            if(_isReading)
            {
                if(_serial.getBytesReceived() < _byteToRead)
                    return;

                String result = new String(_serial.read(_byteToRead));
                
                //[0] = Method Name
                //All The Other Are Parameters (If there is any);
                String[] Split = result.split("|", 0);
                
                if(Split.length >= 2)
                    _currentParams = Arrays.copyOfRange(Split, 1, Split.length);

                if(_methods.containsKey(Split[0]))
                {
                    _methods.get(Split[0]).Invoke();
                }

                _currentParams = null;
                _isReading = false;
            }
            else
            {
                _byteToRead = _serial.read(1)[0];
                _isReading = true;
            }
            
        }
    }

    /**
     * Register a callback for an incoming command
     * @param MethodName The name of the command
     * @param Method The callback of the command
     */
    public void RegisterMethod(String MethodName, Action Method)
    {
        _methods.put(MethodName, Method);
    }
    
    /**
     * Set the callback from the digital read command
     * @param callback The callback of the digital read command
     */
    public void SetDigitalReadCallback(Action callback)
    {
        _anyPinDigitalReadCallback = callback;
    }
    /**
     * Set the callback from the digital read command with a specific pin
     * @param pin The pin to set the callback to
     * @param callback The function to call with the pin state as parameter
     */
    public void SetDigitalReadCallback(int pin, DigitalReadCallback callback)
    {
        _digitalReadCallback.put(pin, callback);
    }

    /**
     * Set the callback from the analog read command
     * @param callback The callback of the analog read command
     */  
    public void SetAnalogReadCallback(Action callback)
    {
        _anyPinAnalogReadCallback = callback;
    }
    /**
     * Set the callback from the analog read command with a specific pin
     * @param pin The pin to set the callback to
     * @param callback the function to call with the pin value as parameter
     */
    public void SetAnalogReadCallback(int pin, AnalogReadCallback callback)
    {
        _analogReadCallback.put(pin, callback);
    }

    /**
     * Get the ammount of avalaible param
     * @return The amount of param
     */
    public int GetParamCount()
    {
        if(_currentParams == null)
            return 0;

        return _currentParams.length;
    }

    /**
     * Get the selected parameter of the current incoming method
     * @param index The index of the parameter
     * @return The selected parameter of the current incoming method
     */
    public String GetParam(int index)
    {
        if(_currentParams == null)
            return null;

        return _currentParams[index];
    }
    /**
     * Get all the parameters of the current incoming method
     * @return The parameters of the current incoming method
     */
    public String[] GetAllParams()
    {
        return _currentParams;
    }

    /**
     * Set the mode of the selected pin
     * @param pin The pin to set the mode to
     * @param mode The mode to set the pin to
     */
    public void PinMode(int pin, ArduinoPinMode mode)
    {
        SendCommand("PinMode", pin, mode);
    }
    /**
     * Write a digital value to a pin
     * @param pin The pin to set the value to
     * @param value The value to set the pin to
     */
    public void DigitalWrite(int pin, boolean value)
    {
        SendCommand("DigitalWrite", pin, value);
    }
    /**
     * Read the digital value of a pin
     * @param pin The pin to read the value from
     */
    public void DigitalRead(int pin)
    {
        SendCommand("DigitalRead", pin);
    }
    /**
     * Write an analog value to a pin
     * @param pin The pin tot set the value to
     * @param value The value to set the pin to
     */
    public void AnalogWrite(int pin, int value)
    {
        SendCommand("AnalogWrite", pin, value);
    }
    /**
     * Read the analog value of a pin
     * @param pin The pin to read the value from
     */
    public void AnalogRead(int pin)

    {
        SendCommand("AnalogRead", pin);
    }
    
    /**
     * Send a command to the arduino
     * @param CommandName The name of the command
     * @param Params The parameters of the command
     */
    public void SendCommand(String CommandName, String... Params)
    {
        if(Params.length >= 1)
            _serial.writeString(CommandName + "|" + String.join("|", Params) + "$");
        else
            _serial.writeString(CommandName + "$");
    }
    /**
     * Send a command to the arduino
     * @param CommandName The name of the command
     * @param Params The parameters of the command
     */
    public void SendCommand(String CommandName, Object... Params)
    {
        String[] stringParams = new String[Params.length];

        for(int i = 0; i < Params.length; i++)
        {
            stringParams[i] = String.valueOf(Params[i]);
        }

        SendCommand(CommandName, stringParams);
    }

    /**
     * Start the comunication betwee the roborio and the arduino
     */
    public void Open()
    {
        if(_isOpen)
            return;

        SendCommand("Connect");

        _serial = new SerialPort(_baudRate, _port);
        _isOpen = true;
    }
    /**
     * Stop the comunication between the roborio and the arduino
     */
    public void Close()
    {
        if(!_isOpen)
            return;

        SendCommand("Disconnect");

        _serial.close();
        _isOpen = false;
    }

    private void ManageDigitalRead()
    {
        _anyPinDigitalReadCallback.Invoke();

        int pin = Integer.parseInt(GetParam(0));

        if(_digitalReadCallback.containsKey(pin))
            _digitalReadCallback.get(pin).Invoke(Boolean.parseBoolean(GetParam(1)));
    }
    private void ManageAnalogRead()
    {
        _anyPinAnalogReadCallback.Invoke();

        int pin = Integer.parseInt(GetParam(0));

        if(_analogReadCallback.containsKey(pin))
            _analogReadCallback.get(pin).Invoke(Integer.parseInt(GetParam(1)));
    }
}