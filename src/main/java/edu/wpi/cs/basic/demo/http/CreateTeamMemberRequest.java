package edu.wpi.cs.basic.demo.http;

import java.sql.Date;
import java.util.ArrayList;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class CreateTeamMemberRequest {
//aru
//matt
	String uniqueID;
	String password;
	String choiceID;
	
	public String getName() {
		return uniqueID;
	}
	public void setName(String name) {
		this.uniqueID = name;
	}
	public String getChoiceID() {
		return choiceID;
	}
	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}
	public String getUsername() {
		return uniqueID;
	}
	public void setUsername(String n) {
		this.uniqueID = n;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String p) {
		this.password = p;
	}
	

	//using this for the lambda function
	public CreateTeamMemberRequest(String username, String password, String choiceID) {
		this.uniqueID = username;
		this.password = password;
		this.choiceID = choiceID;
	}
	 public CreateTeamMemberRequest() {
		 
	 }
	public String toString() {
		return "Registering teamMember...: " + uniqueID + " for choiceID" + choiceID;
	}

	
	
}