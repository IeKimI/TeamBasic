package edu.wpi.cs.basic.demo.model;

import java.util.List;

public class ApprovalInfo {
	int alternativeID;
	String altDescription;
	int numOfApprovals;
	int numOfDisapprovals;
	List<String> listOfTeamMembers;
	List<String> listOfTeamMebersDisapproval;
	
	public int getNumOfDisapprovals() {
		return numOfDisapprovals;
	}
	public void setNumOfDisapprovals(int numOfDisapprovals) {
		this.numOfDisapprovals = numOfDisapprovals;
	}
	public List<String> getListOfTeamMebersDisapproval() {
		return listOfTeamMebersDisapproval;
	}
	public void setListOfTeamMebersDisapproval(List<String> listOfTeamMebersDisapproval) {
		this.listOfTeamMebersDisapproval = listOfTeamMebersDisapproval;
	}
	public int getAlternativeID() {
		return alternativeID;
	}
	public void setAlternativeID(int alternativeID) {
		this.alternativeID = alternativeID;
	}
	public String getAltDescription() {
		return altDescription;
	}
	public void setAltDescription(String altDescription) {
		this.altDescription = altDescription;
	}
	public int getNumOfApprovals() {
		return numOfApprovals;
	}
	public void setNumOfApprovals(int numOfApprovals) {
		this.numOfApprovals = numOfApprovals;
	}
	public List<String> getListOfTeamMembers() {
		return listOfTeamMembers;
	}
	public void setListOfTeamMembers(List<String> listOfTeamMembers) {
		this.listOfTeamMembers = listOfTeamMembers;
	}
	public ApprovalInfo(int alternativeID, String altDescription, int numOfApprovals, List<String> listOfTeamMembers) {
		super();
		this.alternativeID = alternativeID;
		this.altDescription = altDescription;
		this.numOfApprovals = numOfApprovals;
		this.listOfTeamMembers = listOfTeamMembers;
	}
	
	public ApprovalInfo(int alternativeID, String altDescription, int numOfApprovals, int numOfDisapprovals,
			List<String> listOfTeamMembers, List<String> listOfTeamMebersDisapproval) {
		super();
		this.alternativeID = alternativeID;
		this.altDescription = altDescription;
		this.numOfApprovals = numOfApprovals;
		this.numOfDisapprovals = numOfDisapprovals;
		this.listOfTeamMembers = listOfTeamMembers;
		this.listOfTeamMebersDisapproval = listOfTeamMebersDisapproval;
	}
	public ApprovalInfo() {
		
	}

}
