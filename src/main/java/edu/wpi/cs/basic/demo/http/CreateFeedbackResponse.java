package edu.wpi.cs.basic.demo.http;

public class CreateFeedbackResponse {
	public final String response;
	public final int httpCode;
	
	public CreateFeedbackResponse(String response, int httpCode) {
		super();
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
