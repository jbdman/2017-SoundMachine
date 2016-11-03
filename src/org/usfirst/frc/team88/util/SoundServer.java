package org.usfirst.frc.team88.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
import java.io.*;

public class SoundServer implements ITableListener{
	private static Player soundPlayer;
	
	public static void main(String[] args) throws IOException {

		soundPlayer = new Player();

		new SoundServer().run();
	}
	
	public void run() {

		System.out.println("SoundServer started.");

		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.88.88.20");
		NetworkTable table = NetworkTable.getTable("oitable");

		table.addTableListener(this);
	}

	@Override
	// valueChanged
	//   Called when a key-value pair is changed in a ITable
	//
	// Parameters:
	//   source - the table the key-value pair exists in
	//   key - the key associated with the value that changed
	//   value - the new value
	//   isNew - true if the key did not previously exist in the table, otherwise it is false
	
	public void valueChanged(ITable source, String key, Object value, boolean isNew) {

		if (source.getBoolean(key, false)) {
			switch (key) {
			case "driverButtonA" :
				soundPlayer.playSound("horn");
				break;
			case "driverButtonB" :
				soundPlayer.playSound("game_over");
				break;		
			case "driverButtonX" :
				soundPlayer.playSound("shovel");
				break;		
			case "driverButtonY" :
				break;
			default:
				break;
			}
		}
	}
}
