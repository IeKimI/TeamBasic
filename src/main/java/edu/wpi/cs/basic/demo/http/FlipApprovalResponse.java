package edu.wpi.cs.basic.demo.http;


public class FlipApprovalResponse {
	// matt and Eren
	public final String response;
	public int httpCode;

	public FlipApprovalResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}

	public String toString() {
		return "Response(" + response + ")" + "httpCode: " + httpCode;
	}

}