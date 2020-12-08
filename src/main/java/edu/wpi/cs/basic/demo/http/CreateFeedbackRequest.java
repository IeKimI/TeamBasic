package edu.wpi.cs.basic.demo.http;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;

public class CreateFeedbackRequest {
	final String text;
	int teamMemberID;
	int alternativeChoiceID;
	int feedbackID;

	public CreateFeedbackRequest(String text, int teamMemberID, int alternativeChoiceID, int feedbackID) {
		super();
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

	public String getText() {
		return text;
	}

}
