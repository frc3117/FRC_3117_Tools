#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#endif

#define MAX_COMMAND_COUNT 16

class FRC_Arduino
{
    public:
    FRC_Arduino(int baudrate);

    void Setup();
    void RegisterCommand(char* commandName, void (*function)());
    void RegisterDefaultCommand(void (*function)());
    void Loop();
    char* NextParam(int index);
    void SendCommand(char* CommandName, char* Params[]);

    private:
    int _baudrate;

    char* _next;
    char* _command[MAX_COMMAND_COUNT];
    void (*_functions[MAX_COMMAND_COUNT])();
    void (*_defaultFunction)();
    int functionCount;
};