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
	
	public FlipApprovalResponse(String response, ApprovalInfo approvInfo) {
		this.response = response;
		this.httpCode = 200;
	}
	
	//when code not 200 null is returned as the choice
	public FlipApprovalResponse(int code, String response) {
		this.approvInfo = null;
		this.response = response;
		this.httpCode = code;
		}
	
	// 200 means success
	public FlipApprovalResponse (String s, int numVotes, List<TeamMember> ListOfTeamMember) {
		this.response = s;
		this.httpCode = 200;
		this.numVotes = numVotes;
		this.ListOfTeamMember = ListOfTeamMember;
	}
	
	
	public String toString() {
		return "Response(" + response + ")";
	}

}