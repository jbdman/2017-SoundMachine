package org.usfirst.frc.team88.util;

import java.io.IOException;
import java.net.*;

import javax.sound.sampled.*;
import javax.swing.*;

public class Player extends JFrame {

	public Player() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Test Sound Clip");
		this.setSize(300, 200);
		this.setVisible(true);

		try {
			// Open an audio input stream.
			URL url = this.getClass().getClassLoader().getResource("steam-train-horn.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();

//			System.out.println("first time");
//			for (int i=0; i<500000; i++) {
//				System.out.println("test");
//			}
//			System.out.println("second time");
//			audioIn = AudioSystem.getAudioInputStream(url);
//			clip.close();
//			// Open audio clip and load samples from the audio input stream.
//			clip.open(audioIn);
//			clip.start();
//
//			System.out.println("third time");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Player();
	}

}
