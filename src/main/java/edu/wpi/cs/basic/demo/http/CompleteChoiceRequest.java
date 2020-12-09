package edu.wpi.cs.basic.demo.http;

public class CompleteChoiceRequest {
	String choiceID;
	int chosenAltID;
	
	public CompleteChoiceRequest() {
		
	}
	public CompleteChoiceRequest(String choiceID,int chosenAltID) {
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
	
	public int getChosenAltID() {
		return chosenAltID;
	}

	public void setChosenAltID(int chosenAltID) {
		this.chosenAltID = chosenAltID;
	}
	
	
}
