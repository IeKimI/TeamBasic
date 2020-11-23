package edu.wpi.cs.basic.demo.http;

import java.sql.Date;
import java.util.ArrayList;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class LoginRequest {
//aru
//matt
	String name;
	String password;
	String choiceID;

	

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChoiceID() {
		return choiceID;
	}
	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}
	public String getUsername() {
		return name;
	}
	public void setUsername(String n) {
		this.name = n;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String p) {
		this.password = p;
	}
	
	
	
	//using this for the lambda function
	public LoginRequest(String username, String password, String choiceID) {
		this.name = username;
		this.password = password;
		this.choiceID = choiceID;
	}
	
	public String toString() {
		return "Registering teamMember...: " + name + "for choiceID" + choiceID;
	}

	
	
}