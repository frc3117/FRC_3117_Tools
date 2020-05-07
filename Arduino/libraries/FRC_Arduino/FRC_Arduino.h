#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#endif

#define MAX_COMMAND_COUNT 16
#define INIT_COMMAND_COUNT 6
#define COMMAND_BUFFER_SIZE 75

class FRC_Arduino
{
    public:
    FRC_Arduino(int baudrate);
    FRC_Arduino(int baudrate, char* boardName);

    char* GetBoardName();

    void Setup();
    void AddCommand(const char* commandName, void (*function)());
    void SetDefaultCommand(void (*function)());
	void CallCommand(char* CommandName);
	void CallCommand(const char* CommandName);
    void Loop();
    char* NextParam();
    int NextParamInt();
    float NextParamFloat();
    bool NextParamBool();
	void SendCommand(const char* CommandName, char* Params[], int ParamCount);

    private:
    int _baudrate;
    char* _boardName;

	char _buffer[COMMAND_BUFFER_SIZE];
	int _bufferIndex;

    char* _last;
	char* _initCommand[INIT_COMMAND_COUNT];
    char* _command[MAX_COMMAND_COUNT];
	void (*_initFunctions[INIT_COMMAND_COUNT])(FRC_Arduino*);
    void (*_functions[MAX_COMMAND_COUNT])();
	void (*_defaultFunction)();
    int functionCount;

	void Init();
};