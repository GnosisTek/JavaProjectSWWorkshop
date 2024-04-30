package com.bham.fsd.assignments.jabberserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController implements Initializable{
	
	private static final int PORT = 44444;
	public static Socket socket; 
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;

	private JabberMessage jm;
	@FXML TextField T1;
	@FXML Label L1;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			//open socket
			socket = new Socket("localhost", PORT );
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String request) {
		jm = new JabberMessage(request);
		try{
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(jm);
			oos.flush();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String recieveMessage() {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			jm = (JabberMessage) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jm.getMessage();
		
	}
	
	public ArrayList<ArrayList<String>> recieveData(){
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			jm = (JabberMessage) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jm.getData();
	}
	
	public void clickB1(ActionEvent event) {
		String username = T1.getText();
		if(T1.getText().equals("")) {
			L1.setText("Please enter a username");
		}else {
		String request = "signin "+username;
		sendMessage(request);
		String response = recieveMessage();
		try {
			switch(response)
			{
				case "signedin":{
					//L1.setText("Signed in successfully");
					openUserPage();
					((Node)event.getSource()).getScene().getWindow().hide();
					break;
				}
				case "unknown-user":{
					//L1.setText("incorrect username");
					//opens closable error message and clears incorrect username
					Stage stage = new Stage();
					FXMLLoader loader = new FXMLLoader();
					Pane root;
					root = loader.load(getClass().getResource("Error.fxml").openStream());
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.show();
					T1.setText("");
					}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	}
	
	
	public void clickB2(ActionEvent event) {
		//if click register
		if(T1.getText().equals("")) {
			L1.setText("Please enter a username");
		}else {
			String username = T1.getText();
			String request = "register "+username;
			sendMessage(request);
			String response = recieveMessage();
			if(response.equals("signedin")) {
				openUserPage();
				((Node)event.getSource()).getScene().getWindow().hide();
			
			}
		}
	}
	
	public void openUserPage() {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();//create new loader
		Pane root;
		try {
			root = loader.load(getClass().getResource("UserPage.fxml").openStream());
			UserPageController upc = (UserPageController)loader.getController(); // have to cast usercontroller using new loader
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
