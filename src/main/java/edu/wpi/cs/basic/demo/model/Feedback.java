package edu.wpi.cs.basic.demo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List; 

public class Feedback {
	final Timestamp timeStamp;
	final String text;
	int teamMemberID;
	int alternativeChoiceID;
	int feedbackID;
	public Feedback(Timestamp timeStamp, String text, int teamMemberID, int alternativeChoiceID, int feedbackID) {
		super();
		this.timeStamp = timeStamp;
		this.text = text;
		this.teamMemberID = teamMemberID;
		this.alternativeChoiceID = alternativeChoiceID;
		this.feedbackID = feedbackID;
	}
	public Feedback(Timestamp timeStamp, String text, int teamMemberID, int alternativeChoiceID) {
		super();
		this.timeStamp = timeStamp;
		this.text = text;
		this.teamMemberID = teamMemberID;
		this.alternativeChoiceID = alternativeChoiceID;
	}


}
