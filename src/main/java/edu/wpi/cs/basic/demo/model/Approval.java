package edu.wpi.cs.basic.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Approval {
	int approvalID;
	int alternativeID;
	int teamMemberID;
	boolean isApproved;
	boolean isDisapproved;
	
	public Approval(int approvalID, int alternativeID, int teamMemberID, boolean isApproval, boolean isDisapproved) {
		super();
		this.approvalID = approvalID;
		this.alternativeID = alternativeID;
		this.teamMemberID = teamMemberID;
		this.isApproved = isApproval;
		this.isDisapproved = isDisapproved;
	}
	public boolean isDisapproved() {
		return isDisapproved;
	}
	public void setDisapproved(boolean isDisapproved) {
		this.isDisapproved = isDisapproved;
	}
	public boolean isApproval() {
		return isApproved;
	}
	public void setApproval(boolean isApproval) {
		this.isApproved = isApproval;
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
		this.isApproved = isApproval;
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
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	
}
