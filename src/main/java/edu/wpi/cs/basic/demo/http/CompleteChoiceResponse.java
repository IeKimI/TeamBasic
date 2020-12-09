package edu.wpi.cs.basic.demo.http;

import java.util.List;

import edu.wpi.cs.basic.demo.model.Choice;

public class CompleteChoiceResponse {
	public boolean isComplete;
	public int statusCode;
	public final String error;

	
	public CompleteChoiceResponse (int statusCode) {
		super();
		this.statusCode = statusCode;
		this.error = "";
	}

	public CompleteChoiceResponse ( String errorMessage, int statusCode) {
		super();
		this.statusCode = statusCode;
		this.error = errorMessage;
	}

}
