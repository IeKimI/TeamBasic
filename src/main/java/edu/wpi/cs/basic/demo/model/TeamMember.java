package edu.wpi.cs.basic.demo.model;

public class TeamMember {
	final String name;
	final String password;

	public TeamMember(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public TeamMember(String name) {
		super();
		this.name = name;
		this.password = null;
	}

}
