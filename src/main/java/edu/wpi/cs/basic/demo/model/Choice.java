package edu.wpi.cs.basic.demo.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Choice {

	List<AlternativeChoice> alternativeChoices = new ArrayList<AlternativeChoice>();
	List<TeamMember> participatingMembers = new ArrayList<TeamMember>();
	public String uniqueID = "";
	int maxNumOfTeamMembers;
	String description;
	boolean isCompleted;
	Date dateOfCompletion;
	Timestamp dateOfCreation;
	String chosenAlternativeID;

	// test
	public Choice() {
		this.dateOfCompletion = null;
		chosenAlternativeID = null;
	}

	public Choice(String uniqueID, int maxNumOfTeamMembers, String description, String chosenAlternativeID,
			boolean isCompleted, Date dateOfCompletion, Timestamp dateOfCreation) {
		super();
		this.uniqueID = uniqueID;
		this.maxNumOfTeamMembers = maxNumOfTeamMembers;
		this.description = description;
		this.chosenAlternativeID = chosenAlternativeID;
		this.isCompleted = isCompleted;
		this.dateOfCompletion = dateOfCompletion;
		this.dateOfCreation = dateOfCreation;
	}

	// using this for lambda function
	public Choice(String uniqueID, String description, int maxNumOfTeamMembers) {
		this.uniqueID = uniqueID;
		this.description = description;
		this.maxNumOfTeamMembers = maxNumOfTeamMembers;
		this.dateOfCreation = new Timestamp(System.currentTimeMillis());
		this.isCompleted = false;
		this.dateOfCompletion = null;
		this.chosenAlternativeID = null;
		// TODO Auto-generated constructor stub
	}

	public List<AlternativeChoice> getAlternativeChoices() {
		return alternativeChoices;
	}

	public void setAlternativeChoices(List<AlternativeChoice> alternativeChoices) {
		if (alternativeChoices.size() < 6 || alternativeChoices.size() > 1) {
			this.alternativeChoices = alternativeChoices;
		} else {
			// must display error message
		}
	}

	public List<TeamMember> getParticipatingMembers() {
		return participatingMembers;
	}

	public void setParticipatingMembers(List<TeamMember> participatingMembers) {
		if (participatingMembers.size() < this.maxNumOfTeamMembers) {
			this.participatingMembers = participatingMembers;
		} else {
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

	public Timestamp getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Timestamp dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getChosenAlternative() {
		return chosenAlternativeID;
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
