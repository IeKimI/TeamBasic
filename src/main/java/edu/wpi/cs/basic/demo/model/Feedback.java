package edu.wpi.cs.basic.demo.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.db.TeamMemberDAO;

public class Feedback {
	final Timestamp timeStamp;
	final String text;
	int teamMemberID;
	String teamMemberName;
	int alternativeChoiceID;
	int feedbackID;

	public Feedback() {
		this.timeStamp = null;
		this.text = "";
		
	}
	public Feedback(String text, int teamMemberID, int alternativeChoiceID, int feedbackID) {
		TeamMemberDAO teamMemberDAO = new TeamMemberDAO();
		this.timeStamp = new Timestamp(System.currentTimeMillis());
		this.text = text;
		this.teamMemberID = teamMemberID;
		try {
			this.teamMemberName = teamMemberDAO.getTeamMemberByID(teamMemberID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.alternativeChoiceID = alternativeChoiceID;
		this.feedbackID = feedbackID;
	}

	public Feedback(String text, int teamMemberID, int alternativeChoiceID) {
		TeamMemberDAO teamMemberDAO = new TeamMemberDAO();

		this.timeStamp = new Timestamp(System.currentTimeMillis());
		this.text = text;
		this.teamMemberID = teamMemberID;
		try {
			this.teamMemberName = teamMemberDAO.getTeamMemberByID(teamMemberID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.alternativeChoiceID = alternativeChoiceID;
	}

	public Feedback(Timestamp timeStamp, String text, int teamMemberID, int alternativeChoiceID, int feedbackID) {
		TeamMemberDAO teamMemberDAO = new TeamMemberDAO();

		this.timeStamp = timeStamp;
		this.text = text;
		this.teamMemberID = teamMemberID;
		try {
			this.teamMemberName = teamMemberDAO.getTeamMemberByID(teamMemberID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.alternativeChoiceID = alternativeChoiceID;
		this.feedbackID = feedbackID;
	}

	public String getTeamMemberName() {
		return teamMemberName;
	}
	public void setTeamMemberName(String teamMemberName) {
		this.teamMemberName = teamMemberName;
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
		return "Timestamp: " + timeStamp.toString() + " FeedbackID: " + feedbackID + "teamMEmberNAME: " + teamMemberName;
	}
}
