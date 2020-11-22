package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.List;

public class AlternativeChoice {
	ArrayList<TeamMember> approvals = new ArrayList<TeamMember>();
	ArrayList<TeamMember> disapprovals = new ArrayList<TeamMember>();
	ArrayList<Feedback> feedback = new ArrayList<Feedback>();
	String description;
	int alternativeID;
	String choiceID;
	
	
	
	
	public AlternativeChoice(String description) {
		this.description = description;
	}

	public AlternativeChoice(int alternativeID, String choiceID,String description) {
		super();
		this.alternativeID = alternativeID;
		this.choiceID = choiceID;
		this.description = description;

	}

	public AlternativeChoice(ArrayList<TeamMember> approvals, ArrayList<TeamMember> disapprovals,
			ArrayList<Feedback> feedback, String description, int alternativeID, String choiceID) {
		super();
		this.approvals = approvals;
		this.disapprovals = disapprovals;
		this.feedback = feedback;
		this.description = description;
		this.alternativeID = alternativeID;
		this.choiceID = choiceID;
	}

	public AlternativeChoice(ArrayList<TeamMember> approvals, ArrayList<TeamMember> disapprovals, ArrayList<Feedback> feedback,
			String description) {
		this.approvals = approvals;
		this.disapprovals = disapprovals;
		this.feedback = feedback;
		this.description = description;
	}
	
	public ArrayList<TeamMember> getApprovals() {
		return approvals;
	}

	public void setApprovals(ArrayList<TeamMember> approvals) {
		this.approvals = approvals;
	}

	public ArrayList<TeamMember> getDisapprovals() {
		return disapprovals;
	}

	public void setDisapprovals(ArrayList<TeamMember> disapprovals) {
		this.disapprovals = disapprovals;
	}

	public ArrayList<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(ArrayList<Feedback> feedback) {
		this.feedback = feedback;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAlternativeID() {
		return alternativeID;
	}

	public void setAlternativeID(int alternativeID) {
		this.alternativeID = alternativeID;
	}

	public String getChoiceID() {
		return choiceID;
	}

	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}

	Feedback createFeedback() {
		return null;
		
	}
	


 
}
