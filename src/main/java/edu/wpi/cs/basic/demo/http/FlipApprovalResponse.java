package edu.wpi.cs.basic.demo.http;

import java.util.List;

import edu.wpi.cs.basic.demo.model.ApprovalInfo;

public class FlipApprovalResponse {
	// matt and Eren
	public final String response;
	public int httpCode;
	List<ApprovalInfo> list;


	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public List<ApprovalInfo> getList() {
		return list;
	}

	public void setList(List<ApprovalInfo> list) {
		this.list = list;
	}

	public String getResponse() {
		return response;
	}


	public FlipApprovalResponse(String response, int httpCode, List<ApprovalInfo> list) {
		super();
		this.response = response;
		this.httpCode = httpCode;
		this.list = list;
	}

	public FlipApprovalResponse(String response, int httpCode) {
		this.response = response;
		this.httpCode = httpCode;
	}

	public String toString() {
		return "Response(" + response + ")" + "httpCode: " + httpCode + "List: " + list;
	}

}