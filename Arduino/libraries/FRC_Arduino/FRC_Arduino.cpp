#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#endif

#include "FRC_Arduino.h"

FRC_Arduino::FRC_Arduino(int baudrate)
{
    _baudrate = baudrate;
}

void FRC_Arduino::Setup()
{
    Serial.begin(_baudrate);
}

void FRC_Arduino::RegisterCommand(char* commandName, void (*function)())
{
    if(functionCount == MAX_COMMAND_COUNT)
      return;
    
  	_command[functionCount] = commandName;
  	_functions[functionCount++] = function;
}
void FRC_Arduino::RegisterDefaultCommand(void (*function)())
{
  _defaultFunction = function;
}

void FRC_Arduino::Loop()
{
    if(Serial.available())
    {
        char string[100];
     	Serial.readBytesUntil('$', string, 100);
       	
		char *functionName;
        functionName = strtok_r(string, "|", &_last);
      
      	bool isRegistred = false;
      	for(int i = 0; i < MAX_COMMAND_COUNT; i++)
        {
         	if(strcmp(functionName, _command[i]) == 0)
            {
             	_functions[i]();
              	isRegistred = true;
            }
        }
      
      	if(!isRegistred)
          _defaultFunction();
    }
}

char* NextParam()
{
  char* nextParam;
  nextParam = strtok_r(NULL, "|", &_last);
  
  return nextParam;
}