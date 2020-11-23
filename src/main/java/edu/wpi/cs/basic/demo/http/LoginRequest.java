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
	public LoginRequest(String username, String password) {
		this.name = username;
		this.password = password;
	}

	
	
}