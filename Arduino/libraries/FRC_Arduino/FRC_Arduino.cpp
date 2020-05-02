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

void FRC_Arduino::RegisterCommand(String commandName, void (*function)())
{
    if(functionCount == 16)
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
      	_currentMessage = Serial.readStringUntil('$');        	
		String commandName = getValue(_currentMessage, '|', 0);
      
      	bool isRegistred = false;
      	for(int i = 0; i < 18; i++)
        {
         	if(_command[i] == commandName)
            {
             	_functions[i]();
              	isRegistred = true;
            }
        }
      
      	if(!isRegistred)
          _defaultFunction();
    }
}

String FRC_Arduino::GetParam(int index)
{
    return getValue(_currentMessage, '|', index + 1);
}
String FRC_Arduino::getValue(String data, char separator, int index)
{
    int found = 0;
    int strIndex[] = { 0, -1 };
    int maxIndex = data.length() - 1;

    for (int i = 0; i <= maxIndex && found <= index; i++) {
        if (data.charAt(i) == separator || i == maxIndex) {
            found++;
            strIndex[0] = strIndex[1] + 1;
            strIndex[1] = (i == maxIndex) ? i+1 : i;
        }
    }
    return found > index ? data.substring(strIndex[0], strIndex[1]) : "";
}