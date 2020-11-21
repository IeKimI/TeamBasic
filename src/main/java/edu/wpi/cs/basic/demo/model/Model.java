package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.Hashtable;

import edu.wpi.cs.basic.demo.CreateChoiceHandler;

public class Model { // Eren
	Hashtable<String, TeamMember> teamMembers;
	CreateChoiceHandler handler;
	// Fill With Other Handlers Here

	ArrayList<Choice> choices;
	TeamMember loggedInUser;

	public Model() {
		handler = new CreateChoiceHandler();
	}

	public void makeChoice() throws Exception {
		if (loggedInUser != null) {
			handler.createChoice(new Choice(handler.getNextID())); // Make sure that the
																	// made choice is
																	// valid before
																	// pushing
		}
	}

}
