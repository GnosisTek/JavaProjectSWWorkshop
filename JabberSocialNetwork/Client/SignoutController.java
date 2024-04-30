package com.bham.fsd.assignments.jabberserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;

public class SignoutController implements Initializable{
	
	MainController mc = new MainController();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public void clickB4(ActionEvent event) {
		//when click confirm close client
		mc.sendMessage("signout");
		System.exit(0);
	}
	
	public void clickB5(ActionEvent event) {
		//when click cancel button close signout warning window
		((Node)event.getSource()).getScene().getWindow().hide();
	}

}
