package controllers;

import java.util.ArrayList;

import models.Calendar;
import models.Event;
import models.Notification;
import models.Person;
import models.PersonInfo;
import models.UserGroup;
import socket.Requester;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class LoginController {
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Button loginButton;
    @FXML private Label status;
    boolean pressed = false;
    Requester connection;
    Person p;
    
  @FXML
  private void initialize(){
	  Rectangle shape = new Rectangle(1, 1);
	  username.setShape(shape);
	  password.setShape(shape);
  }

  @FXML
  private void keyPressed(KeyEvent key) {
	  if (key.getCode().equals(KeyCode.ENTER)){
		  if (fieldsAreSet()){
			  if (authenticateUser()){
				  WindowController.goToCalendarView();
			  }
			  status.setText("Incorrect username or password");
		  }
		  else{
			  status.setText("Fill in both username and password");
		  }
	  }
  }
    
   @FXML
   private void loginButtonOnAction(){
	   if (fieldsAreSet()){
		   status.setText("logging in");
		   if(authenticateUser()){
			   WindowController.goToCalendarView();
		   }
		   status.setText("Incorrect username or password");
	   }
	   else{
	   status.setText("Fill in both username and password");}
	 
   }
   
   private boolean fieldsAreSet(){
	   if(username.getText().isEmpty() || password.getText().isEmpty()){
		   return false;
	   }
	   return true;
   }
   
   private boolean authenticateUser(){
	   connection = new Requester();
	   p = new Person(username.getText(), password.getText());
	   Person p2  = connection.authenticate(p);
	   connection.closeConnection();
	   if (p2 == null){
		   return false;
	   }
	   this.p=p2;
	   
	   PersonInfo personInfo = new PersonInfo();
	   personInfo.setPerson(this.p);
	   
	   connection = new Requester();
	   UserGroup ug = connection.getPersonalUserGroup(p);
	   personInfo.setPersonalUserGroup(ug);
	   System.out.println(p);
	   connection.closeConnection();
	   
	   connection = new Requester();
	   ArrayList<UserGroup> ugs = connection.getUserGroups(this.p);
	   System.out.println(ugs);
	   personInfo.setUsergroups(ugs);
	   connection.closeConnection();
	   
	   connection = new Requester();
	   ArrayList<Notification> n = connection.getNotifications(ug);
	   personInfo.setNotifications(n);
	   connection.closeConnection();
	   
	   connection = new Requester();
	   ArrayList<Calendar> cal = connection.getCalendars(ug);
	   personInfo.setAllCalendars(cal);
	   connection.closeConnection();
	   
	   connection = new Requester();
	   ArrayList<Event> events = connection.getEvents(cal);
	   personInfo.setEvents(events);
	   connection.closeConnection();
	   
	   PersonInfo.setPersonInfo(personInfo);
	   return true;
   }
}


   
