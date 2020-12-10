package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;

public class CreateChoiceRequest {
// eri 
	List<AlternativeChoice> alternativeChoices = new ArrayList<AlternativeChoice>();
	String description;
	int maxNum;
	

	
	public List<AlternativeChoice> getAlternativeChoices() {
		return alternativeChoices;
	}
	public void setAlternativeChoices(List<AlternativeChoice> alternativeChoices) {
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
	public CreateChoiceRequest(String description, int maxNum, List<AlternativeChoice> alternativeChoices) {
		this.description = description;
		this.maxNum = maxNum;
		this.alternativeChoices = alternativeChoices;

	}
	
	public CreateChoiceRequest() {
		
	}

	
	
}