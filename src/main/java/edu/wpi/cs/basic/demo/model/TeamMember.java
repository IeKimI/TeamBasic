package edu.wpi.cs.basic.demo.model;

public class TeamMember { // Eren
	String name;
	String password;
	String choiceID;
	int teamMemberID;

	public TeamMember() {

	}

	public TeamMember(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public TeamMember(String name) {
		this.name = name;
		this.password = null;
	}

	public TeamMember(String name, String password, String choiceID) {
		this.name = name;
		this.password = password;
		this.choiceID = choiceID;
	}
	public TeamMember(String name, String password, String choiceID, int teamMemberID) {
		this.name = name;
		this.password = password;
		this.choiceID = choiceID;
		this.teamMemberID = teamMemberID;
	}
	public int getTeamMemberID() {
		return teamMemberID;
	}

	public void setTeamMemberID(int teamMemberID) {
		this.teamMemberID = teamMemberID;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getChoiceID() {
		return choiceID;
	}

	public void setChoiceID(String choiceID) {
		this.choiceID = choiceID;
	}

	public boolean equals(Object x) {
		return (x instanceof TeamMember) && ((TeamMember) x).name.equals(name)
				&& ((TeamMember) x).choiceID.equals(choiceID);
	}



}
