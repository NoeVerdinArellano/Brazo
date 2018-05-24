#include <Servo.h>
int variable = 0;
int boton = 5;
Servo base;
Servo hombro;
Servo codo;
Servo garra;
void setup() {
  pinMode(boton, INPUT);
  base.attach(1); //El pin al cual conectaremos nuestro Servo
  base.write(10);
  hombro.attach(2); //El pin al cual conectaremos nuestro Servo
  hombro.write(90);
  codo.attach(3); //El pin al cual conectaremos nuestro Servo
  codo.write(95);
  garra.attach(4); //El pin al cual conectaremos nuestro Servo
  garra.write(100);
  
  
  
}

void loop() {
  variable = digitalRead(boton);
  if (variable == HIGH) {
    hombro.write(100);
    delay(1000);
    codo.write(180);
    delay(1000);
    garra.write(90);
    delay(1000);
    codo.write(100);
    delay(1000);
    /*base.write(50);
    delay(1000);
    base.write(70);
    delay(1000);*/
    base.write(100);
    delay(1000);
    garra.write(100);
    delay(1000);
    base.write(30);
    delay(1000);
    codo.write(95);
    delay(1000);
    hombro.write(90);
    delay(1000);
  }
}
