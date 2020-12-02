package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.TeamMember;

@Deprecated
public class ApprovalResponse {
	//matt and aru
	public final String response;
	public List<TeamMember> ListOfTeamMember = new ArrayList<TeamMember>();
	public int numVotes = 0;
	ApprovalInfo approvInfo;
	public int httpCode;
	
	public ApprovalResponse(String response, ApprovalInfo approvInfo) {
		this.response = response;
		this.approvInfo = approvInfo;
		this.httpCode = 200;
	}
	
	//when code not 200 null is returned as the choice
	public ApprovalResponse(int code, String response) {
		this.approvInfo = null;
		this.response = response;
		this.httpCode = code;
		}
	
	public ApprovalResponse (String s, int code, int numVotes, List<TeamMember> ListOfTeamMember) {
		this.response = s;
		this.httpCode = code;
		this.numVotes = numVotes;
		this.ListOfTeamMember = ListOfTeamMember;
	}
	
	// 200 means success
	public ApprovalResponse (String s, int numVotes, List<TeamMember> ListOfTeamMember) {
		this.response = s;
		this.httpCode = 200;
		this.numVotes = numVotes;
		this.ListOfTeamMember = ListOfTeamMember;
	}
	
	
	public String toString() {
		return "Response(" + response + ")";
	}

}