package edu.wpi.cs.basic.demo.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Choice {

	ArrayList<AlternativeChoice> alternativeChoices = new ArrayList<AlternativeChoice>();
	ArrayList<TeamMember> participatingMembers = new ArrayList<TeamMember>();
	public String uniqueID = "";
	int maxNumOfTeamMembers;
	String description;
	AlternativeChoice chosenAlternative = null;
	boolean isCompleted;
	Date dateOfCompletion;
	Date dateOfCreation;
	// test

	public Choice(String uniqueID, ArrayList<AlternativeChoice> alternativeChoices,
			ArrayList<TeamMember> participatingMembers, String description, Date dateOfCompletion, Date dateOfCreation,
			boolean isCompleted, int maxNumOfTeamMembers) {
		this.uniqueID = uniqueID;
		this.alternativeChoices = alternativeChoices;
		this.participatingMembers = participatingMembers;
		this.description = description;
		this.dateOfCompletion = dateOfCompletion;
		this.dateOfCreation = dateOfCreation;
		this.isCompleted = isCompleted;
		this.maxNumOfTeamMembers = maxNumOfTeamMembers;
	}
	
	public Choice(String uniqueID) {
		this.uniqueID = uniqueID;
		this.alternativeChoices = null;
		this.participatingMembers = null;
		this.description = null;
		this.dateOfCompletion = null;
		this.dateOfCreation = null;
		this.isCompleted = false;
	}

	public ArrayList<AlternativeChoice> getAlternativeChoices() {
		return alternativeChoices;
	} 

	public void setAlternativeChoices(ArrayList<AlternativeChoice> alternativeChoices) {
		if(alternativeChoices.size() < 6 || alternativeChoices.size() > 1) {
			this.alternativeChoices = alternativeChoices;
		}
		else {
			// must display error message
		}
	}

	public ArrayList<TeamMember> getParticipatingMembers() {
		return participatingMembers;
	}

	public void setParticipatingMembers(ArrayList<TeamMember> participatingMembers) {
		if(participatingMembers.size() < this.maxNumOfTeamMembers) {
			this.participatingMembers = participatingMembers;
		}
		else {
			// must have error message
		}
	}

	public String getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public int getMaxNumOfTeamMembers() {
		return maxNumOfTeamMembers;
	}

	public void setMaxNumOfTeamMembers(int maxNumOfTeamMembers) {
		this.maxNumOfTeamMembers = maxNumOfTeamMembers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public Date getDateOfCompletion() {
		return dateOfCompletion;
	}

	public void setDateOfCompletion(Date dateOfCompletion) {
		this.dateOfCompletion = dateOfCompletion;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public AlternativeChoice getChosenAlternative() {
		return chosenAlternative;
	}

	public void setChosenAlternative(AlternativeChoice alternativeChoice) {
		if (this.alternativeChoices.contains(alternativeChoice) || alternativeChoice == null) {
			this.chosenAlternative = alternativeChoice;
		} else {
			// must display error message;
		}
	}

//	public String getReport(TeamMember member) {
//		String reportOutput = "";
//		reportOutput += "Report on Team Member"+ member.name + "";
//		return reportOutput;
//	}

	public boolean completeChoice(Choice choice) {
		choice.setCompleted(true);
		return choice.isComplete();
	}

	public boolean isComplete() {
		return isCompleted;
	}

	public void setComplete(boolean complete) {
		this.isCompleted = complete;
	}

}
