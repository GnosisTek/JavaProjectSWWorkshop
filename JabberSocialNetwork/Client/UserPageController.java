package com.bham.fsd.assignments.jabberserver;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

public class UserPageController implements Initializable {
	
	@FXML TextArea T1;
	
	@FXML ListView<Jab> timeline;
	
	@FXML ListView<User> userFollow;
	
	ObservableList<Jab> list1= FXCollections.observableArrayList();
	
	ObservableList<User> list2= FXCollections.observableArrayList();
	
	
	MainController mc= new MainController();
	
	JabberMessage jm;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//“java execute every x seconds” snippet from
		//https://www.codegrepper.com/code-examples/java/java+execute+every+x+seconds
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				showUsersToFollow(userData());
				showTimeline(jabData());
			}
		}, 0, 1000);
	}
	
	
	public void clickB3(ActionEvent event) {
		//when click signout button,open signout warning window
		openSignout();
	}
	
	public void openSignout() {
		Stage stage = new Stage();
		FXMLLoader loader = new FXMLLoader();//create new loader
		Pane root;
		try {
			//shows signout warning - goes to signoutcontroller
			root = loader.load(getClass().getResource("SignoutWarning.fxml").openStream());
			SignoutController sc = (SignoutController)loader.getController(); // have to cast signoutcontroller using new loader
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void showUsersToFollow(ArrayList<ArrayList<String>> userData) { 
	
		Platform.runLater(new Runnable() {
			@Override public void run() {
				if(!list2.isEmpty()) {
					list2.clear();
				}
				
				ArrayList<User> users = new ArrayList<User>();
				
				for (int i = 0;i<userData.size();i++) {
					User user = new User();
					ArrayList<String> temp = userData.get(i);
					user.setUsername(temp.get(0));
					users.add(user);	
				}
				
				for(int i=0;i<users.size();i++) {
					list2.add(users.get(i));
				}
				
				userFollow.setItems(list2);
		
				userFollow.setCellFactory(new Callback<ListView<User>,ListCell<User>>(){
					//custom cell factory is based on 
					//https://stackoverflow.com/questions/19588029/customize-listview-in-javafx-with-fxml
					//reply by Anvay
					@Override
					public ListCell<User> call(ListView<User> userFollow) {
						final Label label1 = new Label();
						final Button button = new Button();
						final HBox hbox = new HBox();
						Image img = new Image("images/plus.png");
						ImageView imgView = new ImageView(img);
				
						return new ListCell<User>() {
							@Override
							public void updateItem(final User user, boolean empty) {
								super.updateItem(user, empty);
								if (empty) {
									setGraphic(null);
								} else {
						        	hbox.setSpacing(10);
						        	imgView.setFitHeight(20);
						        	imgView.setPreserveRatio(true);
						        	label1.setText(user.getUsername());
						        	label1.setFont(new Font("System", 13));
						        	button.setPrefSize(10, 10);
						        	button.setGraphic(imgView);
						            button.setOnAction(new EventHandler<ActionEvent>() {
						            	@Override
						            	public void handle(ActionEvent event) {
						            		String request = "follow "+user.getUsername();
						            		mc.sendMessage(request);
						            		String response = mc.recieveMessage();
						            	}
						            });
						            if(!hbox.getChildren().isEmpty()) {
						            	hbox.getChildren().clear();
						            }
						            hbox.getChildren().addAll(label1, button);
						            setGraphic(hbox);
				        	
								}
							}
						};
					}
				});
			}
		});
	}
	
	private ArrayList<ArrayList<String>> userData() {
    	mc.sendMessage("users");
    	String response = mc.recieveMessage();
		if(response.equals("users")) {
			mc.sendMessage("users");	
		}
		ArrayList<ArrayList<String>> data = mc.recieveData();		 
		return data;
	}


	public ArrayList<ArrayList<String>> jabData() {
		mc.sendMessage("timeline");
    	String response = mc.recieveMessage();
		if(response.equals("timeline")) {
			mc.sendMessage("timeline");	
		}
		ArrayList<ArrayList<String>> data = mc.recieveData();		 
		return data;
	}
	
	public void showTimeline(ArrayList<ArrayList<String>> jabData) { 
		Platform.runLater(new Runnable() {
			@Override 
			public void run() {
				if(!list1.isEmpty()) {
					list1.clear();
				}
				ArrayList<Jab> jabs = new ArrayList<Jab>();
				for (int i = 0;i<jabData.size();i++) {
					Jab jab = new Jab();
					ArrayList<String> temp = jabData.get(i);
					jab.setUsername(temp.get(0));
					jab.setJabtext(temp.get(1));
					jab.setJabID(temp.get(2));
					jab.setLikesCount(temp.get(3));
					jabs.add(jab);	
				}
				for(int i=0;i<jabs.size();i++) {	
					list1.add(jabs.get(i));
				}
		
				timeline.setItems(list1);
		
				timeline.setCellFactory(new Callback<ListView<Jab>,ListCell<Jab>>(){
				//custom cell factory is based on 
				//https://stackoverflow.com/questions/19588029/customize-listview-in-javafx-with-fxml
				//reply by Anvay
					@Override
					public ListCell<Jab> call(ListView<Jab> timeline) {
						final Label label1 = new Label();
						final Button button = new Button();
						final Label label2 = new Label();
						final HBox hbox = new HBox();
						Image img = new Image("images/heart.png");
						ImageView imgView = new ImageView(img);
				
						return new ListCell<Jab>() {
							@Override
							public void updateItem(final Jab jab, boolean empty) {
								super.updateItem(jab, empty);
								if (empty) {
									setGraphic(null);
								} else {
						        	hbox.setSpacing(10);
						        	imgView.setFitHeight(20);
						        	imgView.setPreserveRatio(true);
						        	label1.setText(jab.getUsername()+" : "+jab.getJabText());
						        	label1.setFont(new Font("System", 13));
						        	button.setPrefSize(10, 10);
						        	button.setGraphic(imgView);
						        	label2.setText(jab.getLikesCount());
						            button.setOnAction(new EventHandler<ActionEvent>() {
						            	@Override
						            	public void handle(ActionEvent event) {
						            		String request = "like "+jab.getJabID();
						            		mc.sendMessage(request);
						            		String response = mc.recieveMessage();
				                
						            	}
						            });
						            if(!hbox.getChildren().isEmpty()) {
						            	hbox.getChildren().clear();
						            }
						            hbox.getChildren().addAll(label1, button, label2);
						            setGraphic(hbox);
								}
							}
						};
					}
				});
			}
		});	
	}
	
	public void postJab(ActionEvent event) {
		//when click post, send jab to server and clear textarea
		String jabtext = T1.getText();
		
		String request = "post "+jabtext;
		mc.sendMessage(request);
		String response = mc.recieveMessage();
		
		if(response.equals("posted")) {
			T1.setText("");
		}
	}

}
