package edu.wpi.cs.basic.demo.http;

public class GetTeamMemberNameByIDRequest {
	String choiceID;
	String teamMemberID;
	public String getChoiceID() {
		return choiceID;
	}
	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}
	public String getTeamMemberID() {
		return teamMemberID;
	}
	public void setTeamMemberID(String teamMemberID) {
		this.teamMemberID = teamMemberID;
	}
	public GetTeamMemberNameByIDRequest(String choiceID, String teamMemberID) {
		super();
		this.choiceID = choiceID;
		this.teamMemberID = teamMemberID;
	}
	
	public GetTeamMemberNameByIDRequest(){
		
	}
}
