package org.usfirst.frc.team88.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class TableReader implements ITableListener {
	private Player player;

	public static void main(String[] args) {
		new TableReader().run();
	}

	public TableReader() {
		player = new Player();
	}

	public void run() {
		System.out.println("Network Reader started.");
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("roborio-88-frc.local");
		NetworkTable robotTable = NetworkTable.getTable("robot");
		robotTable.addTableListener(this, false);

		// test
		// player.playSound("gearCamera");
		
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}
		}
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
			break;
		case "collision":
			if ((boolean) value) {
				player.playSound("Wilhelm");
			}
			break;
		case "lessthan20":
			if ((boolean) value) {
				player.playSound("JohnStewart");
			}
			break;
		case "boilerLock":
		case "chuteLock":
		case "gearLock":
			if ((boolean) value) {
				player.playSound("targetAcquired");
			} else {
				player.playSound("targetLost");
			}
			break;
		case "climbing":
			if ((boolean) value) {
				player.playSound("goingup");
			}
			break;			
		case "readyForTakeoff":
			if ((boolean) value) {
				player.playSound("victory");
			}
			break;
		case "VisionReady":
			if ((boolean) value) {
				player.playSound("all-systems-nominal");
			}
			break;
		case "isBoiler":
			if ((boolean) value) {
				player.playSound("boilerCamera");
			} else {
				player.playSound("gearCamera");
			}
			break;
		case "sound":
			if ((String) value != "") {
				player.playSound((String) value);
				table.putString("sound", "");
			}
			break;
		}
	}
}