package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.TeamMember;

public class ApprovalResponse {
	//matt and aru
	public final String response;
	public List<TeamMember> ListOfTeamMember = new ArrayList<TeamMember>();
	public int numVotes = 0;
	public final int httpCode;
	
	
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