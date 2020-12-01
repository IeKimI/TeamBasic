package edu.wpi.cs.basic.demo.http;

public class ApprovalResponse {
	//matt
	public final String response;
	public final int httpCode;
	
	public ApprovalResponse (String s, int code) {
		this.response = s;
		this.httpCode = code;
	}
	
	// 200 means success
	public ApprovalResponse (String s) {
		this.response = s;
		this.httpCode = 200;
	}
	
	public String toString() {
		return "Response(" + response + ")";
	}

}