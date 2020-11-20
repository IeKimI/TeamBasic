package edu.wpi.cs.basic.demo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List; 

public class Feedback {
	final Timestamp timeStamp;
	final String text;
	List <TeamMember> users;

	public Feedback(Timestamp timeS, String txt, ArrayList<TeamMember> users) {
		super();
		this.timeStamp=timeS;
		this.text=txt;
		this.users=users;
		
		
	}

}
