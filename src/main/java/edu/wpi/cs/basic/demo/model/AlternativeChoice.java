package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.List;

public class AlternativeChoice {
	ArrayList<TeamMember> approvals = new ArrayList<TeamMember>();
	ArrayList<TeamMember> disapprovals = new ArrayList<TeamMember>();
	ArrayList<Feedback> feedback = new ArrayList<Feedback>();
	String description;
	String alternativeID;
	String choiceID;
	
	
	public AlternativeChoice(ArrayList<TeamMember> approvals, ArrayList<TeamMember> disapprovals, ArrayList<Feedback> feedback,
			String description) {
		this.approvals = approvals;
		this.disapprovals = disapprovals;
		this.feedback = feedback;
		this.description = description;
	}
	
	public AlternativeChoice() {
		
	}
	
	boolean flipApproval(TeamMember member) {
		
		return false;
		
	}
	
	boolean flipDisapproval(TeamMember member) {
		return false;
		
	}
	
	Feedback createFeedback() {
		return null;
		
	}
	


 
}
