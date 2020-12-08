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

	public Feedback(String text, int teamMemberID, int alternativeChoiceID, int feedbackID) {
		this.timeStamp = new Timestamp(System.currentTimeMillis());
		this.text = text;
		this.teamMemberID = teamMemberID;
		this.alternativeChoiceID = alternativeChoiceID;
		this.feedbackID = feedbackID;
	}

	public Feedback(String text, int teamMemberID, int alternativeChoiceID) {
		this.timeStamp = new Timestamp(System.currentTimeMillis());
		this.text = text;
		this.teamMemberID = teamMemberID;
		this.alternativeChoiceID = alternativeChoiceID;
	}

	public Feedback(Timestamp timeStamp, String text, int teamMemberID, int alternativeChoiceID, int feedbackID) {
		this.timeStamp = timeStamp;
		this.text = text;
		this.teamMemberID = teamMemberID;
		this.alternativeChoiceID = alternativeChoiceID;
		this.feedbackID = feedbackID;
	}

	public int getTeamMemberID() {
		return teamMemberID;
	}

	public void setTeamMemberID(int teamMemberID) {
		this.teamMemberID = teamMemberID;
	}

	public int getAlternativeChoiceID() {
		return alternativeChoiceID;
	}

	public void setAlternativeChoiceID(int alternativeChoiceID) {
		this.alternativeChoiceID = alternativeChoiceID;
	}

	public int getFeedbackID() {
		return feedbackID;
	}

	public void setFeedbackID(int feedbackID) {
		this.feedbackID = feedbackID;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public String getText() {
		return text;
	}

	public String toString() {
		return "Timestamp: " + timeStamp.toString() + " FeedbackID: " + feedbackID;
	}
}
