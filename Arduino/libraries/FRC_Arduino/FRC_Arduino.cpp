#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#endif

#include <string.h>
#include <FRC_Arduino.h>

FRC_Arduino::FRC_Arduino(int baudrate)
{
  _baudrate = baudrate;

  _boardName = (char*)"any";
}
FRC_Arduino::FRC_Arduino(int baudrate, char* boardName)
{
  _baudrate = baudrate;
  _boardName = boardName;
}

char* FRC_Arduino::GetBoardName()
{
  return _boardName;
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
  if(Serial.available() > 0)
  {
	char current = Serial.read();

	if(current == '$')
	{
		_bufferIndex = 0;

		char *functionName;
		_last = _buffer;
		functionName = strtok_r(_buffer, "|", &_last);

		bool isRegistred = false;
		for(int i = 0; i < MAX_COMMAND_COUNT; i++)
		{
		  if(strcmp(functionName, "Discover") == 0)
		  {
		    if(strcmp(NextParam(), _boardName) == 0)
		    {
				Serial.write((byte)7);
				Serial.print("Success");
				break;
		    }
		  }
		  else if(strcmp(functionName, _command[i]) == 0)
		  {
		    _functions[i]();
		    isRegistred = true;

		    break;
		  }
		}

		for(int i = 0; i < COMMAND_BUFFER_SIZE; i++)
		{
			_buffer[i] = '\0';
		}

		if(!isRegistred)
			_defaultFunction();
	}
	else
	{
		_buffer[_bufferIndex++] = current;

		if(_bufferIndex == COMMAND_BUFFER_SIZE + 1)
		{
			for(int i = 0; i < COMMAND_BUFFER_SIZE; i++)
			{
				_buffer[i] = '\0';
			}

			_bufferIndex = 0;
		}
	}
  }
}

char* FRC_Arduino::NextParam()
{
  char* nextParam;
  nextParam = strtok_r(NULL, "|", &_last);
  
  return nextParam;
}
int FRC_Arduino::NextParamInt()
{
  return atoi(NextParam());
}
float FRC_Arduino::NextParamFloat()
{
  return (float)strtod(NextParam(), NULL);
}
bool FRC_Arduino::NextParamBool()
{
  char* nextParam = NextParam();
  return nextParam && strcmp(nextParam, "true") == 0;
}

void FRC_Arduino::SendCommand(char* CommandName, char* Params[], int ParamCount)
{
  int count = ParamCount;
  count += strlen(CommandName);
  for(int i = 0; i < ParamCount; i++)
  {
    count += strlen(Params[i]);
  }

  char command[50] = { '\0' };
  strncat(command, CommandName, 25);
  
  for(int i = 0; i < ParamCount; i++)
  {
    strncat(command, "|", 25);
    strncat(command, Params[i], 25);
  }

  Serial.write((byte)count);
  Serial.print((__FlashStringHelper*)command);
}