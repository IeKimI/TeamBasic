package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.Choice;


public class AllChoicesResponse {
	public final List<Choice> list;
	public final int statusCode;
	public final String error;
	
	public AllConstantsResponse (List<Choice> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}
	
	public AllConstantsResponse (int code, String errorMessage) {
		this.list = new ArrayList<Choice>();
		this.statusCode = code;
		this.error = errorMessage;
	}
	
	public String toString() {
		if (list == null) { return "EmptyConstants"; }
		return "AllConstants(" + list.size() + ")";
	}

}
