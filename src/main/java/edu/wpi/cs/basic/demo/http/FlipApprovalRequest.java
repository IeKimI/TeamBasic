package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class FlipApprovalRequest {
//matt and Eren
	int alternativeID;
	boolean whichToFlip; //true for flipping approval, false for flipping disapproval
	int teamMemberID;
	String choiceID;
	
	
	
	public FlipApprovalRequest(int alternativeID, int teamMemberID, boolean whichToFlip) {
		super();
		this.alternativeID = alternativeID;
		this.whichToFlip = whichToFlip;
		this.teamMemberID = teamMemberID;
	}

	public FlipApprovalRequest(int alternativeID, boolean whichToFlip, int teamMemberID, String choiceID) {
		super();
		this.alternativeID = alternativeID;
		this.whichToFlip = whichToFlip;
		this.teamMemberID = teamMemberID;
		this.choiceID = choiceID;
	}

	public String getChoiceID() {
		return choiceID;
	}

	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}

	public int getTeamMemberID() {
		return teamMemberID;
	}

	public void setTeamMemberID(int teamMemberID) {
		this.teamMemberID = teamMemberID;
	}

	public FlipApprovalRequest(int alternativeID) {
		this.alternativeID = alternativeID;
	}
	
	public FlipApprovalRequest() {
		
	}
	
	public boolean isWhichToFlip() {
		return whichToFlip;
	}

	public void setWhichToFlip(boolean whichToFlip) {
		this.whichToFlip = whichToFlip;
	}

	public int getAlternativeID() {
		return alternativeID;
	}
	
	public void setAlternativeID(int alternativeID) {
		this.alternativeID = alternativeID;
	}
	
	
}