package edu.wpi.cs.basic.demo.http;

import java.util.List;

import edu.wpi.cs.basic.demo.model.Choice;

/** Sends back the name of the constant deleted -- easier to handle on client-side. */
public class DeleteChoicesResponse {
	public final float nDaysOld;
	public final int statusCode;
	public final String error;
	public final List<Choice> list;

	
	
	public DeleteChoicesResponse (float nDaysOld, int statusCode) {
		this.nDaysOld = nDaysOld;
		this.statusCode = statusCode;
		this.error = "";
		this.list = null;
	}
	
	// 200 means success
	public DeleteChoicesResponse (float nDaysOld, int statusCode, List<Choice> list, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.list = list;
		this.nDaysOld = nDaysOld;
	}
	


	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "DeleteResponse(" + nDaysOld + ")" + list;
		} 
			return "ErrorResult(" + nDaysOld + ", statusCode=" + statusCode + ", err=" + error + ")";
		
	}
}
