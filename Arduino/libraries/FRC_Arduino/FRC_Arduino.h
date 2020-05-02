#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#endif

class FRC_Arduino
{
    public:
    FRC_Arduino(int baudrate);

    void Setup();
    void RegisterCommand(String commandName, void (*function)());
    void RegisterDefaultCommand(void (*function)());
    void Loop();
    String GetParam(int index);

    private:
    int _baudrate;

    String _currentMessage;
    String _command[16];
    void (*_functions[16])();
    void (*_defaultFunction)();
    int functionCount;

    String getValue(String data, char separator, int index);
};