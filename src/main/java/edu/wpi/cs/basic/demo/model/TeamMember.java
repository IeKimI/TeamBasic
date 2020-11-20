package edu.wpi.cs.basic.demo.model;

public class TeamMember { // Eren
	final String name;
	final String password;
	String choiceID;

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
				&& ((TeamMember) x).password.equals(password);
	}

	boolean flipApproval(AlternativeChoice alt) {
		alt.disapprovals.remove(this);
		if (alt.approvals.contains(this)) {
			alt.approvals.remove(this);
			return false;
		} else {
			return alt.approvals.add(this);
		}

	}

	boolean flipDisapproval(AlternativeChoice alt) {
		alt.approvals.remove(this);
		if (alt.disapprovals.contains(this)) {
			alt.disapprovals.remove(this);
			return false;
		} else {
			return alt.disapprovals.add(this);
		}
	}

}
