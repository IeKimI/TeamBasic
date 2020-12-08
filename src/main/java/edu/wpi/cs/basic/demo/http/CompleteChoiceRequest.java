package edu.wpi.cs.basic.demo.http;

public class CompleteChoiceRequest {
	String choiceID;
	String chosenAltID;
	
	public CompleteChoiceRequest(String choiceID, String chosenAltID) {
		super();
		this.choiceID=choiceID;
		this.chosenAltID=chosenAltID;
	}
	
	public String getChoiceID() {
		return choiceID;
	}

	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}
	
	public String getChosenAltID() {
		return chosenAltID;
	}

	public void setChosenAltID(String chosenAltID) {
		this.chosenAltID = chosenAltID;
	}
	
	
}
