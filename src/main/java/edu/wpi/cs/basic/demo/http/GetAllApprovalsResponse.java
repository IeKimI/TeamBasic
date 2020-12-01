package edu.wpi.cs.basic.demo.http;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.Choice;

public class GetAllApprovalsResponse {
	public final List<ApprovalInfo> list;
	public final int statusCode;
	public final String error;
	
	public GetAllApprovalsResponse(List<ApprovalInfo> list, int statusCode, String error) {
		super();
		this.list = list;
		this.statusCode = statusCode;
		this.error = error;
	}
	

public GetAllApprovalsResponse(List<ApprovalInfo> list, int code) {
		this.list = list;
		this.statusCode = code;
		this.error = "";
	}

	public GetAllApprovalsResponse (int code, String errorMessage) {
		this.list = new ArrayList<ApprovalInfo>();
		this.statusCode = code;
		this.error = errorMessage; 
	}
	
	public String toString() {
		if (list == null) { return "EmtpyChoice"; }
		return "AllChoices(" + list.size() + ")";
	}

}
