package edu.wpi.cs.basic.demo.http;

import java.sql.Date;
import java.util.ArrayList;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class CreateChoiceRequest {
// eri 
	ArrayList<AlternativeChoice> alternativeChoices = new ArrayList<AlternativeChoice>();
	String description;
	int maxNum;
	

	
	public ArrayList<AlternativeChoice> getAlternativeChoices() {
		return alternativeChoices;
	}
	public void setAlternativeChoices(ArrayList<AlternativeChoice> alternativeChoices) {
		this.alternativeChoices = alternativeChoices;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}
	
	//using this for the lambda function
	public CreateChoiceRequest(String description, int maxNum, ArrayList<AlternativeChoice> alternativeChoices) {
		this.description = description;
		this.maxNum = maxNum;
		this.alternativeChoices = alternativeChoices;

	}

	
	
}