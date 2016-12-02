package org.usfirst.frc.team88.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class TableReader implements ITableListener{

	public static void main(String[] args) throws IOException, LineUnavailableException {

		new TableReader().run();
	}

	public void run() throws LineUnavailableException {

		double x;
		double y;
		double z;
		double leftCurrent;
		double rightCurrent;

		final AudioFormat af =
				new AudioFormat(Tone.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		SourceDataLine line2 = AudioSystem.getSourceDataLine(af);
		line.open(af, Tone.SAMPLE_RATE);
		line.start();
		line2.open(af, Tone.SAMPLE_RATE);
		line2.start();

		System.out.println("Network Reader started.");

		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.88.88.20");
		NetworkTable table = NetworkTable.getTable("oitable");

		while(true){
			try{
				Thread.sleep(50);
			}
			catch(InterruptedException ex){
				//				Logger.getLogger(NetworkTablesDesktopClient.class.getName())log(Level.SEVERE, null, ex);
			}
			x = table.getNumber("x", 0.0);
			y = table.getNumber("y", 0.0);
			z = table.getNumber("z", 0.0);
			
			leftCurrent = table.getNumber("Left Current", 0.0);
			rightCurrent = table.getNumber("Right Current", 0.0);

			System.out.println("x: " + x + " y: " + y + " z: " + z + " Right " + rightCurrent + " Left " + leftCurrent);
			System.out.println();
			
			//right sound
			playSound(line, rightCurrent);
			//left sound
			playSound(line2, leftCurrent);
		}
	}

	private static void playSound(SourceDataLine line, double current){

		if(current > 30){
			play(line, Tone.A5, 50);
		}
		else if(current > 27.5){
			play(line, Tone.G4$, 50);
		}
		else if(current > 25){
			play(line, Tone.G4, 50);
		}
		else if(current > 22.5){
			play(line, Tone.F4$, 50);
		}
		else if(current > 20){
			play(line, Tone.F4, 50);
		}
		else if(current > 17.5){
			play(line, Tone.E4, 50);
		}
		else if(current > 15){
			play(line, Tone.D4$, 50);
		}
		else if(current > 12.5){
			play(line, Tone.D4, 50);
		}
		else if(current > 10){
			play(line, Tone.C4$, 50);
		}
		else if(current > 7.5){
			play(line, Tone.C4, 50);
		}
		else if(current > 5){
			play(line, Tone.B4, 50);
		}
		else if(current > 2.5){
			play(line, Tone.A4$, 50);
		}
		else if(current > 0){
			play(line, Tone.A4, 50);
		}
		else{
			play(line, Tone.REST, 50);
		}
	}
	private static void play(SourceDataLine line, Tone note, int ms) {
		ms = Math.min(ms, Tone.SECONDS * 1000);
		int length = Tone.SAMPLE_RATE * ms / 1000;
		int count = line.write(note.data(), 0, length);
	}

	@Override
	public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3) {
		// TODO Auto-generated method stub

	}

}

enum Tone {

	REST, A4, A4$, B4, C4, C4$, D4, D4$, E4, F4, F4$, G4, G4$, A5;
	public static final int SAMPLE_RATE = 16 * 1024; // ~16KHz
	public static final int SECONDS = 2;
	private byte[] sin = new byte[SECONDS * SAMPLE_RATE];

	Tone() {
		int n = this.ordinal();
		if (n > 0) {
			double exp = ((double) n - 1) / 12d;
			double f = 220d * Math.pow(2d, exp);
			for (int i = 0; i < sin.length; i++) {
				double period = (double)SAMPLE_RATE / f;
				double angle = 2.0 * Math.PI * i / period;
				sin[i] = (byte)(Math.sin(angle) * 127f);
			}
		}
	}

	public byte[] data() {
		return sin;
	}
}