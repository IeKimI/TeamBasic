package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.wpi.cs.basic.demo.db.ChoiceDatabaseHandler;

public class Model { // Eren
	Hashtable<String, TeamMember> teamMembers;
	ChoiceDatabaseHandler handler;
	// Fill With Other Handlers Here

	ArrayList<Choice> choices;
	TeamMember loggedInUser;

	public Model() {
		// TODO Auto-generated constructor stub
	}

	public void makeChoice() {
		if(loggedInUser!=null){
			handler.pushChoice(new Choice(null, null, null, null, handler.getNextID(), false));
		}
	}

}
