package edu.wpi.cs.basic.demo.http;

public class CreateTeamMemberResponse {
	public final String response;
	public final int httpCode;
	
	public CreateTeamMemberResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public CreateTeamMemberResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}
