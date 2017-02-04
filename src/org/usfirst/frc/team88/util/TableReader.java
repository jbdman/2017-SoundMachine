package org.usfirst.frc.team88.util;

import java.io.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class TableReader implements ITableListener {
	private Player player;
	
	public static void main(String[] args) throws IOException, LineUnavailableException {
		new TableReader().run();
	}
	
	public TableReader () {
		player = new Player();
	}
	
	public void run() throws LineUnavailableException {
		double avgCurrent;
		
		final AudioFormat af = new AudioFormat(Tone.SAMPLE_RATE, 8, 1, true, true);
		SourceDataLine line = AudioSystem.getSourceDataLine(af);
		line.open(af, Tone.SAMPLE_RATE);
		line.start();

		System.out.println("Network Reader started.");

		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.8.83.76");
		NetworkTable robotTable = NetworkTable.getTable("robot");
		robotTable.addTableListener(this);

		while (true) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				// Logger.getLogger(NetworkTablesDesktopClient.class.getName())log(Level.SEVERE,
				// null, ex);
			}

			avgCurrent = robotTable.getNumber("driveAvgCurrent", 0.0);
			playSound(line, avgCurrent);
		}
	}

	private static void playSound(SourceDataLine line, double current) {
		double maxCurrent = 30;

		if (current > 0.0) {
			play(line,Tone.values()[Math.min((int)(current/maxCurrent * 13),13)],25);
		}	}

	private static void play(SourceDataLine line, Tone note, int ms) {
		ms = Math.min(ms, Tone.SECONDS * 1000);
		int length = Tone.SAMPLE_RATE * ms / 1000;
		int count = line.write(note.data(), 0, length);
	}

	@Override
	public void valueChanged(ITable table, String key, Object value, boolean isNew) {
		switch (key) {
		case "inLow":
			if ((boolean) value) {
				// play shifted to low gear sound
				player.playSound("shovel");
			} else {
				// play shifted to high gear sound
				player.playSound("horn");
			}
		}
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
				double period = (double) SAMPLE_RATE / f;
				double angle = 2.0 * Math.PI * i / period;
				sin[i] = (byte) (Math.sin(angle) * 127f);
			}
		}
	}

	public byte[] data() {
		return sin;
	}
}