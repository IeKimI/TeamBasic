package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Approval {
	int approvalID;
	int alternativeID;
	int teamMemberID;
	boolean isApproval;
	
	public boolean isApproval() {
		return isApproval;
	}
	public void setApproval(boolean isApproval) {
		this.isApproval = isApproval;
	}
	public int getApprovalID() {
		return approvalID;
	}
	public void setApprovalID(int approvalID) {
		this.approvalID = approvalID;
	}
	public int getAlternativeID() {
		return alternativeID;
	}
	public void setAlternativeID(int alternativeID) {
		this.alternativeID = alternativeID;
	}
	public int getTeamMemberID() {
		return teamMemberID;
	}
	public void setTeamMemberID(int teamMemberID) {
		this.teamMemberID = teamMemberID;
	}
	
	public Approval() {
		
	}
	public Approval(int approvalID, int alternativeID, int teamMemberID, boolean isApproval) {
		super();
		this.approvalID = approvalID;
		this.alternativeID = alternativeID;
		this.teamMemberID = teamMemberID;
		this.isApproval = isApproval;
	}
	public Approval(int approvalID, int alternativeID, int teamMemberID) {
		super();
		this.approvalID = approvalID;
		this.alternativeID = alternativeID;
		this.teamMemberID = teamMemberID;
	}
	public Approval(int alternativeID, int teamMemberID) {
		super();
		this.alternativeID = alternativeID;
		this.teamMemberID = teamMemberID;
	}
	
	
}
