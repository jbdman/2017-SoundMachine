package org.usfirst.frc.team88.util;

import java.net.*;

import javax.sound.sampled.*;
import javax.swing.*;

public class Player extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Player() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Test Sound Clip");
        this.setSize(300, 200);
        this.setVisible(true);
    }

    public void playSound(String fileNickname){
        URL url = null;

        System.out.println("fileNickname = " + fileNickname);
        // Open an audio input stream.

        if(fileNickname.startsWith("horn")){
            System.out.println("loading horn");
            url = this.getClass().getClassLoader().getResource("sounds/steam-train-horn.wav");
        } else if(fileNickname.startsWith("shovel")){
            System.out.println("loading shovel");
            url = this.getClass().getClassLoader().getResource("sounds/working-with-shovel.wav");
        } else if(fileNickname.startsWith("targetAcquired")){
            System.out.println("loading gear");
        	url = this.getClass().getClassLoader().getResource("sounds/target_lock.aiff");
        } else if(!fileNickname.equals("")){
            System.out.println("loading file " + fileNickname);
        	url = this.getClass().getClassLoader().getResource("sounds/" + fileNickname + ".wav");
        }

        if (url != null) {

            System.out.println("playing...");

            try {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                // Get a sound clip resource.
                Clip clip = AudioSystem.getClip();
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);
                clip.setFramePosition(0);
                clip.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("done.");

        } else {
        	System.out.println("null url");
        }
    }
}
