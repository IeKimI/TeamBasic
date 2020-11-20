package edu.wpi.cs.basic.demo.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Choice {
	public String uniqueID = "";
	ArrayList<AlternativeChoice> alternativeChoices = new ArrayList<AlternativeChoice>();
	ArrayList<TeamMember> participatingMembers = new ArrayList<TeamMember>();
	String description;
	Date dayOfCompletion;
	float daysOld;
	boolean isCompleted;
	AlternativeChoice chosenAlternative = null; 
	
	public Choice(String uniqueID, ArrayList<AlternativeChoice> alternativeChoices,
			ArrayList<TeamMember> participatingMembers, String description, Date dayOfCompletion, float daysOld,
			boolean isCompleted) {
		super();
		this.uniqueID = uniqueID;
		this.alternativeChoices = alternativeChoices;
		this.participatingMembers = participatingMembers;
		this.description = description;
		this.dayOfCompletion = dayOfCompletion;
		this.daysOld = daysOld;
		this.isCompleted = isCompleted;	
	}
	

	public ArrayList<AlternativeChoice> getAlternativeChoices() {
		return alternativeChoices;
	}

	public void setAlternativeChoices(ArrayList<AlternativeChoice> alternativeChoices) {
		this.alternativeChoices = alternativeChoices;
	}

	public ArrayList<TeamMember> getParticipatingMembers() {
		return participatingMembers;
	}

	public void setParticipatingMembers(ArrayList<TeamMember> participatingMembers) {
		this.participatingMembers = participatingMembers;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDayOfCompletion() {
		return dayOfCompletion;
	}

	public void setDayOfCompletion(Date dayOfCompletion) {
		this.dayOfCompletion = dayOfCompletion;
	}

	public float getDaysOld() {
		return daysOld;
	}

	public void setDaysOld(float daysOld) {
		this.daysOld = daysOld;
	}


	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getUniqueID() {
		return uniqueID;
	}
	
	public AlternativeChoice getChosenAlternative() {
		return this.chosenAlternative;
	}
	
	public void setChosenAlternative(AlternativeChoice alternativeChoice) {
		if(this.alternativeChoices.contains(alternativeChoice) || alternativeChoice == null) {
			this.chosenAlternative = alternativeChoice;
		}
		else {
			//must display error message;
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
