package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Feedback {
	final String timeStamp;
	final String text;
	List <TeamMember> users=new ArrayList<TeamMember>();

	public Feedback(String timeS, String txt, ArrayList<TeamMember> users) {
		super();
		this.timeStamp=timeS;
		this.text=txt;
		this.users=users;
		
		
	}

}
