package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class ApprovalRequest {
//matt
	int alternativeID;
	int numOfApprovals;
	List<TeamMember> listOfTeamMembers;
	
	public int getAlternativeID() {
		return alternativeID;
	}
	public void setAlternativeID(int alternativeID) {
		this.alternativeID = alternativeID;
	}
	public int getNumOfApprovals() {
		return numOfApprovals;
	}
	public void setNumOfApprovals(int numOfApprovals) {
		this.numOfApprovals = numOfApprovals;
	}
	public List<TeamMember> getListOfTeamMembers() {
		return listOfTeamMembers;
	}
	public void setListOfTeamMembers(List<TeamMember> listOfTeamMembers) {
		this.listOfTeamMembers = listOfTeamMembers;
	}
	
	public ApprovalRequest(int alternativeID, int numOfApprovals, List<TeamMember> listOfTeamMembers) {
		this.alternativeID = alternativeID;
		this.numOfApprovals = numOfApprovals;
		this.listOfTeamMembers = listOfTeamMembers;
	}
	
	public ApprovalRequest() {
		
	}
	
	
	
	
	
}
