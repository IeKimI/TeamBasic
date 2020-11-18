package edu.wpi.cs.basic.demo.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Choice {
	public final String uniqueID = "";
	List<AlternativeChoice> alternativeChoices = new ArrayList<AlternativeChoice>();
	List<TeamMember> participatingMembers = new ArrayList<TeamMember>();
	String description;
	Date dayOfCompletion;
	boolean isCompleted;
	
	public Choice(List<AlternativeChoice> alternativeChoices, List<TeamMember> participatingMembers, Date dayOfCompletion, String description) {
		this.alternativeChoices = alternativeChoices;
		this.participatingMembers = participatingMembers;
		this.dayOfCompletion = dayOfCompletion;
		this.description = description;
		this.isCompleted = false;
	}
	
	public Choice() {
		//eren is hot
	}
	
	public String getReport(TeamMember member) {
		return null;
	}
	
	public boolean completeChoice(Choice choice) {
		return false;
	}
	
	
	public boolean isComplete() {
		return isCompleted;
	}
	public void setComplete(boolean complete) {
		this.isCompleted = complete;
	}
	
	
}
