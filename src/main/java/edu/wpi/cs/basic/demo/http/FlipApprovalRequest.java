package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class FlipApprovalRequest {
//matt and aru
	int approvalID;
	
	public int getAlternativeID() {
		return approvalID;
	}
	
	public void setAlternativeID(int alternativeID) {
		this.approvalID = alternativeID;
	}

	public FlipApprovalRequest(int alternativeID) {
		this.approvalID = alternativeID;
	}
	
	public FlipApprovalRequest() {
		
	}
	
	
}