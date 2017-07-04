package de.info_ag.printer.filacontrol;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;
import lejos.util.Delay;

public class FilaControl {
	public static void main(String[] args) {
		LCD.drawString("connecting...", 0, 0);
		USBConnection connection = USB.waitForConnection();
		DataInputStream input = connection.openDataInputStream();
		LCD.clear();
		LCD.drawString("connected", 0, 0);
		scanLoop(input);
		Motor.A.setSpeed(200);
	}
	private static void scanLoop(DataInputStream input){
		int state = 0;
		while (!Button.ESCAPE.isDown()) {
			try {
				state = input.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
				
			if(state != 0){
				LCD.clear();
				LCD.drawString("state: " + state, 0, 0);
			}
			
			if (state == 1) {
				Motor.A.forward();
				togglePen();
			}
			else if (state == 2)  {
				Motor.A.stop();
				togglePen();
			}
		}
	}
	public static void  togglePen() {
		Motor.B.rotate(45);
		Motor.B.rotate(-45);
	}
}
