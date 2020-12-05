package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.ApprovalInfo;

public class GetTeamMemberNameByIDHandlerResponse {
	public final String teamMemberName;
	public final int statusCode;
	public final String response;
	
	public GetTeamMemberNameByIDHandlerResponse(String teamMemberName, int statusCode, String response) {
		super();
		this.teamMemberName = teamMemberName;
		this.statusCode = statusCode;
		this.response = response;
	}
	

public GetTeamMemberNameByIDHandlerResponse(String teamMemberName, int code) {
		this.teamMemberName = teamMemberName;
		this.statusCode = code;
		this.response = "";
	}

	public String toString() {
		
		return "Team member name: " + teamMemberName;
	}
}
