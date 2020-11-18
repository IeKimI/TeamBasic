package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.List;

public class AlternativeChoice {
	List<TeamMember> approvals = new ArrayList<TeamMember>();
	List<TeamMember> disapprovals = new ArrayList<TeamMember>();
	List<Feedback> feedback = new ArrayList<Feedback>();
	String description;
	String alternativeID;
	String choiceID;
	
	
	public AlternativeChoice(List<TeamMember> approvals, List<TeamMember> disapprovals, List<Feedback> feedback,
			String description) {
		this.approvals = approvals;
		this.disapprovals = disapprovals;
		this.feedback = feedback;
		this.description = description;
	}
	
	public AlternativeChoice() {
		
	}
	
	boolean flipApproval() {
		
		return false;
		
	}
	
	boolean flipDisapproval() {
		return false;
		
	}
	
	Feedback createFeedback() {
		return null;
		
	}
	


 
}
