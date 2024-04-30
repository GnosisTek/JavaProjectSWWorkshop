package com.bham.fsd.assignments.jabberserver;

import java.io.*;
import java.net.*;

public class JabberServer implements Runnable {
	
	private static final int PORT = 44444;
	
	private static ServerSocket server;
	private static JabberDatabase db;
	
	public JabberServer() {
		try {	
			server = new ServerSocket(PORT);
			server.setReuseAddress(true);
			server.setSoTimeout(300);
			new Thread(this).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void processClientRequests() {
		while (true) {
			
			Socket clientSocket = null;
			try {
				//Waits for client connection
				clientSocket = server.accept();
				db = new JabberDatabase();
				ClientConnection clientC = new ClientConnection(clientSocket, db);				
				Thread.sleep(100);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	@Override
	public void run() {
		while(true) {
			processClientRequests();
		}
	}
	
	
	
	public static void main(String argv[]) throws IOException {
		
		JabberServer js = new JabberServer();
		
	}
}

