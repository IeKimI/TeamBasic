package edu.wpi.cs.basic.demo.http;

/** Sends back the name of the constant deleted -- easier to handle on client-side. */
public class DeleteChoicesResponse {
	public final float nDaysOld;
	public final int statusCode;
	public final String error;
	
	public DeleteChoicesResponse (float nDaysOld, int statusCode) {
		this.nDaysOld = nDaysOld;
		this.statusCode = statusCode;
		this.error = "";
	}
	
	// 200 means success
	public DeleteChoicesResponse (float nDaysOld, int statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.error = errorMessage;
		this.nDaysOld = nDaysOld;
	}
	


	public String toString() {
		if (statusCode / 100 == 2) {  // too cute?
			return "DeleteResponse(" + nDaysOld + ")";
		} else {
			return "ErrorResult(" + nDaysOld + ", statusCode=" + statusCode + ", err=" + error + ")";
		}
	}
}
