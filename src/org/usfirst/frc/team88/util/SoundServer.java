package org.usfirst.frc.team88.util;

import java.net.*;
import java.io.*;

public class SoundServer {
	public static void main(String[] args) throws IOException {

		// FMS Restricts us to 5800 - 5810
		int portNumber = 5800;
		Player soundPlayer = new Player();

		System.out.println("SoundServer started.");

		
		try (ServerSocket serverSocket = new ServerSocket(portNumber);
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				out.println(inputLine);
					soundPlayer.playSound(inputLine);
			}
			
		} catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
