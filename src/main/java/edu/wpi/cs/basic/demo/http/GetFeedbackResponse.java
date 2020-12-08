package edu.wpi.cs.basic.demo.http;

import java.util.List;

import edu.wpi.cs.basic.demo.model.Feedback;

public class GetFeedbackResponse {
	public final String response;
	public final int httpCode;
	List<Feedback> feedback;
	
	public GetFeedbackResponse(String response, int httpCode, List<Feedback> feedbackList) {
		this.response = response;
		this.httpCode = httpCode;
		feedback=feedbackList;
	}
	public String getResponse() {
		return response;
	}
	public int getHttpCode() {
		return httpCode;
	}
	public List<Feedback> getListOfFeedback() {
		return feedback;
	}
	public void setListOfFeedback(List<Feedback> listOfFeedback) {
		this.feedback = listOfFeedback;
	}

}
