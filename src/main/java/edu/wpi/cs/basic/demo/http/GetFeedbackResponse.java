package edu.wpi.cs.basic.demo.http;

import java.util.List;

import edu.wpi.cs.basic.demo.model.Feedback;

public class GetFeedbackResponse {
	public final String response;
	public final int httpCode;
	List<Feedback> feedback;
	
	public GetFeedbackResponse(String response, int httpCode, List<Feedback> feedback) {
		this.response = response;
		this.httpCode = httpCode;
		this.feedback=feedback;
	}
	public String getResponse() {
		return response;
	}
	public int getHttpCode() {
		return httpCode;
	}
	public List<Feedback> getFeedback() {
		return feedback;
	}
	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}

}
