package edu.wpi.cs.basic.demo.http;

public class GetFeedbackResponse {
	public final String response;
	public final int httpCode;
	
	public GetFeedbackResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}
	public String getResponse() {
		return response;
	}
	public int getHttpCode() {
		return httpCode;
	}

}
