package edu.wpi.cs.basic.demo.model;

import java.sql.Date;

public class Choice {
	public final String uniqueID = "";
	boolean complete;
	Date dayOfCompletion;
	
	public boolean isComplete() {
		return complete;
	}
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	
}
