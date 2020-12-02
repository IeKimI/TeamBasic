package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.TeamMember;


public class FlipApprovalResponse {
	//matt and aru
	public final String response;
	public boolean hasBeenApproved;
	public boolean hasBeenDisapproved;
	public int httpCode;
	
	public FlipApprovalResponse(String response, ApprovalInfo approvInfo, boolean hasBeenApproved, boolean hasBeenDisapproved) {
		this.response = response;
		this.httpCode = 200;
		this.hasBeenApproved = hasBeenApproved;
		this.hasBeenDisapproved = hasBeenDisapproved;
	}
	
	
	public String toString() {
		return "Response(" + response + ")";
	}

}