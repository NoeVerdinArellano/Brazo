/* Programacion en arduino para el arduino esclavo conectado al arduino maestro
 * que ademas controla el brazo robotico de 4 DOF.
 * 
 * Se incluyen 2 librerias
 *  *LiquidCrystal
 *  Esta libreria funciona para poner en funcionamiento el LCD conectado al arduino
 *    En este programa la utilizamos para mostrar los mensajes que manda
 *    el arduino maestro mediante la comunicacion I2C.
 *  *Wire
 *  Esta libreria sirve para la comunicacion entre arduinos mediante el protocolo I2C
 *    En este programa la utilizamos para hacer las peticiones de comunicacion con el 
 *    arduino maestro, de manera muy similar a como se hacen las lecturas por serial.
 *    
 * 
 */
#include <LiquidCrystal.h>
#include <Wire.h>
//----se declara un objeto de la libreria LiquidCrystal con los pines del LCD
LiquidCrystal lcd (8,9,4,5,6,7);
//Variable para almacenar la lectura de la comunicacion con el arduino maestro
String mensaje="";

void setup()
{
  //se inicializa la comunicacion I2C y el serial
  Wire.begin(); // Abrimos el canal 1 (0x01) del I2C
  lcd.begin(16,2);
}

void loop()
{
  //Se coloca el cursor en el inicio de la pantalla LCD
  lcd.setCursor(0,1);
  //Se solicita la comunicacion con el arduino maestro
  Wire.requestFrom(1,6,false);
  //si se recibe comunicacion se lee
  while(Wire.available()>0){
    delay(500);
    mensaje=mensaje+ASCII(Wire.read());
  }
  //si se leyó algun mensaje se borra la pantalla del LCD
  //y se escribe el mensaje recibido
  if(mensaje!=""){
    lcd.clear();
    lcd.print(mensaje);
    mensaje="";
    //se termina la transmision de informacion, para poder
    //prepararse para recibir comunicacion nuevamente
    Wire.endTransmission();
  }
  
}

//----------------------------------------
char ASCII(int entrada){
  char salida=' ';
  switch(entrada){
case 32: 
salida=' '; 
break; 
case 33: 
salida='!'; 
break; 
case 34: 
salida='"'; 
break; 
case 35: 
salida='#'; 
break; 
case 36: 
salida='$'; 
break; 
case 37: 
salida='%'; 
break; 
case 38: 
salida='&'; 
break; 
case 39: 
salida=' '; 
break; 
case 40: 
salida='('; 
break; 
case 41: 
salida=')'; 
break; 
case 42: 
salida='*'; 
break; 
case 43: 
salida='+'; 
break; 
case 44: 
salida=','; 
break; 
case 45: 
salida='-'; 
break; 
case 46: 
salida='.'; 
break; 
case 47: 
salida='/'; 
break; 
case 48: 
salida='0'; 
break; 
case 49: 
salida='1'; 
break; 
case 50: 
salida='2'; 
break; 
case 51: 
salida='3'; 
break; 
case 52: 
salida='4'; 
break; 
case 53: 
salida='5'; 
break; 
case 54: 
salida='6'; 
break; 
case 55: 
salida='7'; 
break; 
case 56: 
salida='8'; 
break; 
case 57: 
salida='9'; 
break; 
case 58: 
salida=':'; 
break; 
case 59: 
salida=';'; 
break; 
case 60: 
salida='<'; 
break; 
case 61: 
salida='='; 
break; 
case 62: 
salida='>'; 
break; 
case 63: 
salida='?'; 
break; 
case 64: 
salida='@'; 
break; 
case 65: 
salida='A'; 
break; 
case 66: 
salida='B'; 
break; 
case 67: 
salida='C'; 
break; 
case 68: 
salida='D'; 
break; 
case 69: 
salida='E'; 
break; 
case 70: 
salida='F'; 
break; 
case 71: 
salida='G'; 
break; 
case 72: 
salida='H'; 
break; 
case 73: 
salida='I'; 
break; 
case 74: 
salida='J'; 
break; 
case 75: 
salida='K'; 
break; 
case 76: 
salida='L'; 
break; 
case 77: 
salida='M'; 
break; 
case 78: 
salida='N'; 
break; 
case 79: 
salida='O'; 
break; 
case 80: 
salida='P'; 
break; 
case 81: 
salida='Q'; 
break; 
case 82: 
salida='R'; 
break; 
case 83: 
salida='S'; 
break; 
case 84: 
salida='T'; 
break; 
case 85: 
salida='U'; 
break; 
case 86: 
salida='V'; 
break; 
case 87: 
salida='W'; 
break; 
case 88: 
salida='X'; 
break; 
case 89: 
salida='Y'; 
break; 
case 90: 
salida='Z'; 
break; 
case 91: 
salida='['; 
break; 
case 92: 
salida=' '; 
break; 
case 93: 
salida=']'; 
break; 
case 94: 
salida='^'; 
break; 
case 95: 
salida='_'; 
break; 
case 96: 
salida='`'; 
break; 
case 97: 
salida='a'; 
break; 
case 98: 
salida='b'; 
break; 
case 99: 
salida='c'; 
break; 
case 100: 
salida='d'; 
break; 
case 101: 
salida='e'; 
break; 
case 102: 
salida='f'; 
break; 
case 103: 
salida='g'; 
break; 
case 104: 
salida='h'; 
break; 
case 105: 
salida='i'; 
break; 
case 106: 
salida='j'; 
break; 
case 107: 
salida='k'; 
break; 
case 108: 
salida='l'; 
break; 
case 109: 
salida='m'; 
break; 
case 110: 
salida='n'; 
break; 
case 111: 
salida='o'; 
break; 
case 112: 
salida='p'; 
break; 
case 113: 
salida='q'; 
break; 
case 114: 
salida='r'; 
break; 
case 115: 
salida='s'; 
break; 
case 116: 
salida='t'; 
break; 
case 117: 
salida='u'; 
break; 
case 118: 
salida='v'; 
break; 
case 119: 
salida='w'; 
break; 
case 120: 
salida='x'; 
break; 
case 121: 
salida='y'; 
break; 
case 122: 
salida='z'; 
break; 
case 123: 
salida='{'; 
break; 
case 124: 
salida='|'; 
break; 
case 125: 
salida='}'; 
break; 
case 126: 
salida='~'; 
break; 

  }
  return salida;
}
