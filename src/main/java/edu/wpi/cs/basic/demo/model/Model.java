package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.wpi.cs.basic.demo.ChoiceDatabaseHandler;

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
		if (loggedInUser != null) {
			handler.pushChoice(new Choice(handler.getNextID())); // Make sure that the
																									// made choice is
																									// valid before
																									// pushing
		}
	}

}
