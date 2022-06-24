#include <Servo.h>
#include <LiquidCrystal.h>

const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

Servo myservo;

String inputString = "";
String command = "";
String value = "";
String password = "1234";
boolean stringComplete = false;

void setup() {

  Serial.begin(9600);
  
  lcd.begin(16, 2);
  
  inputString.reserve(50);
  command.reserve(50);
  value.reserve(50);

  boolean stringOK = false;

  myservo.attach(9);
}

void loop() {

  if (stringComplete) {
    Serial.println(inputString);
    delay(100);

    int pos = inputString.indexOf('=');

    if (pos > -1) {
      command = inputString.substring(0, pos);
      value = inputString.substring(pos + 1, inputString.length() - 1); // extract command up to \n exluded

      if (!password.compareTo(value) && (command == "OPEN")) {
        openDoor();
        Serial.println(" OPEN");
        delay(100);
        
      }
      else if (!password.compareTo(value) && (command == "CLOSE")) {
        closeDoor();
        Serial.println(" CLOSE");
        delay(100);
      }
      else if (password.compareTo(value)) {
        closeDoor();
        Serial.println(" WRONG");
        delay(100);
      }
    }
    inputString = "";
    stringComplete = false;
  }

}


void serialEvent() {
  while (Serial.available()) {
    char inChar = (char)Serial.read();
    inputString += inChar;

    if (inChar == '\n' || inChar == '\r') {
      stringComplete = true;
    }
  }
}

void openDoor() {
  myservo.write(0);
  delay(100);
  lcd.clear();
  lcd.print("Unlocked");
}

void closeDoor() {
  myservo.write(83);
  delay(100);
  lcd.clear();
  lcd.print("Locked");
}
