package edu.wpi.cs.basic.demo.model;

import java.util.List;

public class ApprovalInfo {
	int alternativeID;
	String altDescription;
	int numOfApprovals;
	List<String> listOfTeamMembers;
	
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
	
	public ApprovalInfo() {
		
	}

}
