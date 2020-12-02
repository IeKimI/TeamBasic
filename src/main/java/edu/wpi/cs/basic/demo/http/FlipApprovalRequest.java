package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class FlipApprovalRequest {
//matt and Eren
	int approvalID;
	boolean whichToFlip; //true for flipping approval, false for flipping disapproval
	
	public FlipApprovalRequest(int alternativeID) {
		this.approvalID = alternativeID;
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
		return approvalID;
	}
	
	public void setAlternativeID(int alternativeID) {
		this.approvalID = alternativeID;
	}
	
	
}