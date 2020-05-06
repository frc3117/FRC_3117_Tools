#if defined(ARDUINO) && ARDUINO >= 100
#include "Arduino.h"
#endif

#include <string.h>
#include <FRC_Arduino.h>

void AutoDiscover(FRC_Arduino* instance)
{
	if (strcmp(instance->NextParam(), instance->GetBoardName()) == 0)
	{
		instance->SendCommand("Success", NULL, 0);
	}
}
void PinMode(FRC_Arduino* instance)
{
	int pin = instance->NextParamInt();
	int mode = instance->NextParamInt();

	switch (mode)
	{
	case 0:
		pinMode(pin, INPUT);
		break;
	case 1:
		pinMode(pin, OUTPUT);
		break;
	case 2:
		pinMode(pin, INPUT_PULLUP);
		break;

	default:
		break;
	}
}
void DigitalWrite(FRC_Arduino* instance)
{
	int pin = instance->NextParamInt();

	digitalWrite(pin, instance->NextParamBool() ? HIGH : LOW);
}
void DigitalRead(FRC_Arduino* instance)
{
	char* pin = instance->NextParam();

	char* param[] = { pin, digitalRead(atoi(pin)) == HIGH ? (char*)"true" : (char*)"false" };
	instance->SendCommand("DigitalRead", param, 2);
}
void AnalogWrite(FRC_Arduino* instance)
{
	int pin = instance->NextParamInt();
	int value = constrain(instance->NextParamInt(), 0, 255);

	analogWrite(pin, value);
}
void AnalogRead(FRC_Arduino* instance)
{
	char* pin = instance->NextParam();

	char* param[] = { pin, analogRead(atoi(pin)) };
	instance->SendCommand("AnalogRead", param, 2);
}

void FRC_Arduino::Init()
{
	_initCommand[0] = (char*)"Discover";
	_initCommand[1] = (char*)"PinMode";
	_initCommand[2] = (char*)"DigitalWrite";
	_initCommand[3] = (char*)"DigitalRead";
	_initCommand[4] = (char*)"AnalogWrite";
	_initCommand[5] = (char*)"AnalogRead";

	_initFunctions[0] = AutoDiscover;
	_initFunctions[1] = PinMode;
	_initFunctions[2] = DigitalWrite;
	_initFunctions[3] = DigitalRead;
	_initFunctions[4] = AnalogWrite;
	_initFunctions[5] = AnalogRead;
}

FRC_Arduino::FRC_Arduino(int baudrate)
{
  _baudrate = baudrate;
  _boardName = (char*)"any";

  Init();
}
FRC_Arduino::FRC_Arduino(int baudrate, char* boardName)
{
  _baudrate = baudrate;
  _boardName = boardName;

  Init();
}

char* FRC_Arduino::GetBoardName()
{
  return _boardName;
}

void FRC_Arduino::Setup()
{
  Serial.begin(_baudrate);
}

void FRC_Arduino::AddCommand(const char* commandName, void (*function)())
{
  if(functionCount == MAX_COMMAND_COUNT)
    return;
    
  _command[functionCount] = (char*)commandName;
  _functions[functionCount++] = function;
}
void FRC_Arduino::SetDefaultCommand(void (*function)())
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

		for (int i = 0; i < INIT_COMMAND_COUNT; i++)
		{
			if (strcmp(functionName, _initCommand[i]) == 0)
			{
				_initFunctions[i](this);
				isRegistred = true;

				break;
			}
		}
		if (!isRegistred)
		{
			for (int i = 0; i < MAX_COMMAND_COUNT; i++)
			{
				if (strcmp(functionName, _command[i]) == 0)
				{
					_functions[i]();
					isRegistred = true;

					break;
				}
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

void FRC_Arduino::SendCommand(const char* CommandName, char* Params[], int ParamCount)
{
	int count = ParamCount;
	count += strlen(CommandName);
	for (int i = 0; i < ParamCount; i++)
	{
		count += strlen(Params[i]);
	}

	Serial.write(count);
	Serial.print(CommandName);
	delay(5);

	for (int i = 0; i < ParamCount; i++)
	{
		Serial.print('|');
		Serial.print(Params[i]);
		delay(5);
	}
}