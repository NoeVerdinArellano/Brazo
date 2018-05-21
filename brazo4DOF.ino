/* Codigo Arduino para el control del brazo robotico 4 DOF y sus perifericos.
 * 
 * El codigo consiste en la inclusion de 3 librerias:
 *  *EEPROM
 *   Esta libreria es utilizada para escribir/leer la memoria EEPROM incluida
 *   en el arduino UNO.
 *      En el codigo siguiente la utilizamos para la programacion automatica del brazo
 *      y para lograr que al cargar un programa al brazo si se desconecta al reconectarse
 *      se continue ejecutando el programa.
 *  *Servo
 *  Esta libreria sirve para el control de los servo motores del brazo robotico
 *    En el siguiente codigo se usa justamente para eso, el control de los servo
 *    motores en el brazo.
 *  *Wire
 *  Esta libreria nos ayuda con la comunicacion entre arduinos por el protocolo I2C.
 *  I2C es un bus de comunicaciones en serie.
 *    En el siguiente codigo se usa para comunicar este arduino con el arduino esclavo
 *    que tiene conectado el LCD.
 *
 * 
 * 
 */
#include <EEPROM.h> // Incluimos la biblioteca EEPROM
#include <Servo.h> // Incluimos la biblioteca Servo
#include <Wire.h> //Incluimos la biblioteca de comunicaciòn I2C
//-----VARIABLES de los servo motores
Servo servo_1;  // Definimos los servos que vamos a utilizar
Servo servo_2;
Servo servo_3;
//variables para cargar el movimiento de cada motor, leido del programa
//cargado.
int motor1;
int motor2;
int motor3;
int motor4;
//------FIN DE VARIABLES
//-----VARIABLES del motor a pasos
const int motorPin1 = 8;    // 28BYJ48 In1
const int motorPin2 = 9;    // 28BYJ48 In2
const int motorPin3 = 10;   // 28BYJ48 In3
const int motorPin4 = 11;   // 28BYJ48 In4
//definicion variables
int motorSpeed = 1200;   //variable para fijar la velocidad
int stepCounter = 0;     // contador para los pasos
int stepsPerRev = 4076;  // pasos para una vuelta completa
int qStep = 1019 * 2;
int tope = 0;
//secuencia media fase
const int numSteps = 8;
const int stepsLookup[8] = { B1000, B1100, B0100, B0110, B0010, B0011, B0001, B1001 };
//-----FIN DE VARIABLES
//---VARIABLES DE USO VARIO
//variable de lectura de comunicacion del serial
String Lectura = "";
//variable de mensaje para la comunicacion con el arduino esclavo
String mensaje = "S/N   ";
//-----FIN DE VARIABLES
void setup() {
  //inicializacion de la comunicacion con el arduino esclavo
  Wire.begin(1); // Abrimos el canal 1 (0x01) del I2C
  //Se asocia el petodo peticion() a la comunicacion con el arduino 
  //esclavo, si el otro arduino pide comunicacion, se ejecuta el 
  //metodo asociado
  Wire.onRequest(peticion);
  //inicializacion de los pines del motor a pasos
  pinMode(motorPin1, OUTPUT);
  pinMode(motorPin2, OUTPUT);
  pinMode(motorPin3, OUTPUT);
  pinMode(motorPin4, OUTPUT);
  //inicializacion de los pines de los servo motores
  servo_1.attach(4);  // Difinimos el pines de señal para el servo
  servo_2.attach(5);
  servo_3.attach(6);
  //boton de paro
  pinMode(12,INPUT);
  //LEDS
  pinMode(2,OUTPUT);//VERDE
  pinMode(3,OUTPUT);//AMARILLO
  pinMode(7,OUTPUT);//ROJOALARMA
  //---------
  Serial.begin(9600); //comunicacion serial
}
//limpia la memoria EEPROM
void clearM(int x, int y) {
  for (int i = x ; i < y ; i++) {
    EEPROM.write(i, 255);
  }
}
//mensasje del LCD "manual", "auto"
void peticion()
{
  byte a[7];
  mensaje.getBytes(a, 7);
  for (int i = 0; i < 7; i++) {
    Wire.write(a[i]);
  }
}
//encendido de led dependiendo del modo
void enciende(int l){
  //VERDE=1
  //AMARILLO=2
  digitalWrite(2,LOW);
  digitalWrite(3,LOW);

  switch(l){
    //verde automatico
    case 1:
    digitalWrite(2,HIGH);
    break;
    //amarillo manual
    case 2:
    digitalWrite(3,HIGH);
    break;
    default:
    break;
  }
}
void loop() {
  if (EEPROM.read(0) == 1) {
    //enciende el led verde en modo auto
    enciende(1);
    //MODO AUTO
    movAuto();
    //apaga los leds
    enciende(0);
  } else {
    //intentar leer instruccion
    Lectura="";
    // la conexion serial al presionar auto o manual entregar una m(77) o a(65)
    //si es el caracter "m" lenctura(); entregara un 77 en ascii
    //si es el caracter "a" lenctura(); entregara un 65 en ascii
    lectura(); 
    if(Lectura!=""){
    switch (Lectura.charAt(0)) {
     //case de modo manual
      case 77:
      enciende(2);
        mensaje = "Manual";
        movManual();
        delay(200);
        enciende(0);
        break;
      //case de modo automatico
      case 65:
        mensaje = "auto  ";
        cargaAuto();
        break;

      default:
        break;
    }
    }
  }

  delay(50);
}

//metodo automatico de carga de programacion
void cargaAuto() {
  int index = 1;
  int memoIndex = 0;
  byte val = 0;
  EEPROM.write(memoIndex, 1);
  String aux = "";
  //concatena los pasos ingresados desde la interfaz de java
  for(index;index<Lectura.length()-1;index=index){
    for (int j = 0; j < 3; j++) {
      aux = aux + Lectura.charAt(index++)+"";
    }
    memoIndex++;
    val = aux.toInt();  
    //escribismos los pasos del brazo con un index que lo identifica
    EEPROM.write(memoIndex, val);
    aux = "";
  }
  val =200;
  //finaliza el proceso
  EEPROM.write(++memoIndex, val);
}
//metodo para abrotar operaciones
boolean abortar() {
    int a = digitalRead(12);
  //si es precionado el boton, regresa un mensaje abortar al Display
  if (a == HIGH) {
    mensaje = "aborta";
    return true;
  } else {
    return false;
  }
}
//metodo de encendido de led rojo cuando esta en paro el brazo
void detener() {
  enciende(0);
  delay(500);
  int y;
  while(true){
  digitalWrite(7,HIGH);
  if(y==HIGH){ break;}
  delay(500);
  y = digitalRead(12);
  if(y==HIGH){ break;}
  digitalWrite(7,LOW);
  if(y==HIGH){ break;}
  delay(500);
  y = digitalRead(12);
  if(y==HIGH){ break;}
  }
  digitalWrite(7,LOW);
  mensaje = "AUTO  ";
  delay(1000);
  enciende(1);
}
//metodo automatico de manejo de motores
void movAuto() {
  
  mensaje= Lectura.length();
  int index = 1;
  while (EEPROM.read(index) != 200&&EEPROM.read(index)!=255) {
    if (EEPROM.read(index) != 255
    &&(EEPROM.read(index)>=0&&EEPROM.read(index)<=180)) {
      //lectura
      motor1 = EEPROM.read(index++);
      motor2 = EEPROM.read(index++);
      motor3 = EEPROM.read(index++);
      motor4 = EEPROM.read(index++);
      //ejecucion, con las condicionales para el boton de paro
      if(abortar()){detener();}
        servo_1.write(motor1);
      if(abortar()){detener();}  
        servo_2.write(motor2);
      if(abortar()){detener();}
        servo_3.write(motor3);
      if(abortar()){detener();}
        motorPasos(motor4);
        delay(2000); 
      //recorrerMemoria
      clearM(1, index);
    }
    else {
      index++;
    }
  }
  EEPROM.write(0, 0);
  EEPROM.write(index,255);
  mensaje = "AUTO  ";
}

int lectura() {
  delay(500);
  //crea el string al cambiar los valores ascii
  while (Serial.available() > 0) {
    Lectura = Lectura + ASCII(Serial.read());
  }
}


//metodo para el movimiento manual de los motores
void movManual() {
  String aux = Lectura.substring(2, 5);
  switch (Lectura.charAt(1)) {
    case 49:
      servo_1.write(aux.toInt());
      break;
    case 50:
      servo_2.write(aux.toInt());
      break;
    case 51:
      servo_3.write(aux.toInt());
      break;
    case 52:
      motorPasos(aux.toInt());
      break;
  }


}
//moviemiento del motor a pasos
void motorPasos(int x) {
  if (x > tope) {
    while (x > tope) {
      tope++;
      mov();
    }
  } else if (x < tope) {
    while (x < tope) {
      tope--;
      bMov();
    }
  }
}



//------------------------------------------------------------------------------------------------------
void mov() {
  for (int i = 0; i < qStep; i++)
  {
    clockwise();
    delayMicroseconds(motorSpeed);
  }
}

void bMov() {
  for (int i = 0; i < qStep; i++)
  {
    anticlockwise();
    delayMicroseconds(motorSpeed);
  }
}
void clockwise()
{
  stepCounter++;
  if (stepCounter >= numSteps) stepCounter = 0;
  setOutput(stepCounter);
}

void anticlockwise()
{
  stepCounter--;
  if (stepCounter < 0) stepCounter = numSteps - 1;
  setOutput(stepCounter);
}

void setOutput(int step)
{
  digitalWrite(motorPin1, bitRead(stepsLookup[step], 0));
  digitalWrite(motorPin2, bitRead(stepsLookup[step], 1));
  digitalWrite(motorPin3, bitRead(stepsLookup[step], 2));
  digitalWrite(motorPin4, bitRead(stepsLookup[step], 3));
}


char ASCII(int entrada) {
  char salida = ' ';
  switch (entrada) {
    case 42:
      salida = '*';
      break;
    case 48:
      salida = '0';
      break;
    case 49:
      salida = '1';
      break;
    case 50:
      salida = '2';
      break;
    case 51:
      salida = '3';
      break;
    case 52:
      salida = '4';
      break;
    case 53:
      salida = '5';
      break;
    case 54:
      salida = '6';
      break;
    case 55:
      salida = '7';
      break;
    case 56:
      salida = '8';
      break;
    case 57:
      salida = '9';
      break;
    case 65:
      salida = 'A';
      break;
    case 77:
      salida = 'M';
      break;
  }
  return salida;
}
