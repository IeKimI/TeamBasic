package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.List;

public class AlternativeChoice {
	List<TeamMember> approvals = new ArrayList<TeamMember>();
	List<TeamMember> disapprovals = new ArrayList<TeamMember>();
	List<Feedback> feedback = new ArrayList<Feedback>();
	String description;
	int alternativeID;
	String choiceID;
	
	public AlternativeChoice () {
		
	}
	
	
	public AlternativeChoice(String description) {
		this.description = description;
	}

	public AlternativeChoice(int alternativeID, String choiceID,String description) {
		this.alternativeID = alternativeID;
		this.choiceID = choiceID;
		this.description = description;

	}

	public AlternativeChoice(List<TeamMember> approvals, List<TeamMember> disapprovals,
			List<Feedback> feedback, String description, int alternativeID, String choiceID) {
		this.approvals = approvals;
		this.disapprovals = disapprovals;
		this.feedback = feedback;
		this.description = description;
		this.alternativeID = alternativeID;
		this.choiceID = choiceID;
	}

	public AlternativeChoice(List<TeamMember> approvals, List<TeamMember> disapprovals, List<Feedback> feedback,
			String description) {
		this.approvals = approvals;
		this.disapprovals = disapprovals;
		this.feedback = feedback;
		this.description = description;
	}
	
	public List<TeamMember> getApprovals() {
		return approvals;
	}

	public void setApprovals(List<TeamMember> approvals) {
		this.approvals = approvals;
	}

	public List<TeamMember> getDisapprovals() {
		return disapprovals;
	}

	public void setDisapprovals(List<TeamMember> disapprovals) {
		this.disapprovals = disapprovals;
	}

	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
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
