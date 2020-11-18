package edu.wpi.cs.basic.demo.model;

import java.util.Collection;
import java.util.Iterator;

public class Admin extends TeamMember {

	public Admin() {
		super("ADMIN");
	}
	
	String createReport(Collection<Choice> choices){
		Iterator<Choice> iterator= choices.iterator();
		while(iterator.hasNext()) {
			
		}
		return null;
	}

}
