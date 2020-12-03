package edu.wpi.cs.basic.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.http.GetAllApprovalsResponse;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;

public class ListAllApprovalsHandler extends LambdaTest {
	@Test
	public void testGetList() {
		GetApprovalsHandler handler = new GetApprovalsHandler();

		GetAllApprovalsResponse resp = handler.handleRequest("2135024186", createContext("list"));

		boolean hasE = false;
		for (ApprovalInfo approvInfo : resp.list) {
			System.out.println("found approval " + approvInfo);
			System.out.println(approvInfo.getListOfTeamMembers());
		}
		Assert.assertEquals(200, resp.statusCode);
		System.out.println(resp.list);
	}
}
