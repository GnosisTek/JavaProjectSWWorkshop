package com.bham.fsd.assignments.jabberserver;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnection implements Runnable{
	
	private JabberDatabase db;
	private Socket clientSocket;
	private String username;
	private int userid;
	private int signout = 0;

	
	public ClientConnection(Socket client, JabberDatabase db) {
		this.clientSocket = client;
		this.db = db;
		this.username = null;
		this.userid = -1;
		new Thread(this).start();
	}
	
	
	
	public JabberMessage makeQuery(String request) {
		
		String[] message = request.split(" ", 2);
		
		String response = null;
		JabberMessage jm;
		ArrayList<ArrayList<String>> data = null;
		
		switch (message[0]) {
			case "signin":
				username = message[1];
				userid = db.getUserID(message[1]);
				if (userid == -1) {
					response = "unknown-user";
				} else {
					response = "signedin";
				}
				break;
			
			case "register":
				username = message[1];
				db.addUser(username, username + "@tormail.com");
				userid = db.getUserID(message[1]);
				response =  "signedin";
				break;
				
			case "signout":
				signout = 1;
				break;
				
			case "timeline":
				response = "timeline";
				data = db.getTimelineOfUserEx(username);
				break;
			
			case "users":
				response = "users";
				data = db.getUsersNotFollowed(userid);
				break;
				
			case "post":
				db.addJab(username, message[1]);
				response = "posted";
				break;
				
			case "like":
				db.addLike(userid, Integer.valueOf(message[1]));
				response = "posted";
				break;
				
			case "follow":
				db.addFollower(userid, message[1]);
				response = "posted";
				break;
				
		}
		if(data == null) {
			jm = new JabberMessage(response);
		} else {
			jm = new JabberMessage(response, data);
		}
		
		return jm;
	}
	
	
	
	public void run() {
		try {
			while (true) {
				
				ObjectInputStream ois = new
						ObjectInputStream(clientSocket.getInputStream());
				JabberMessage request = (JabberMessage) ois.readObject();
				
				JabberMessage jm = makeQuery(request.getMessage());
				
				if (signout == 1) {
					break;
				}
				
				ObjectOutputStream oos = new
						ObjectOutputStream(clientSocket.getOutputStream());
				oos.writeObject(jm);
				oos.flush();
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
