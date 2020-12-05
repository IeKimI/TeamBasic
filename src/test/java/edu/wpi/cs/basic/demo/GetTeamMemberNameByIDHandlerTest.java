package edu.wpi.cs.basic.demo;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.http.GetAllApprovalsResponse;
import edu.wpi.cs.basic.demo.http.GetTeamMemberNameByIDHandlerResponse;
import edu.wpi.cs.basic.demo.http.GetTeamMemberNameByIDRequest;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;

public class GetTeamMemberNameByIDHandlerTest extends LambdaTest{
	@Test
	public void testGetTeamMemberName() {
		GetTeamMemberNameByIDHandler handler = new GetTeamMemberNameByIDHandler();

		GetTeamMemberNameByIDRequest req = new GetTeamMemberNameByIDRequest("906905823", "95");
		GetTeamMemberNameByIDHandlerResponse resp = handler.handleRequest(req, createContext("list"));

		
		Assert.assertEquals(200, resp.statusCode);
		System.out.println(resp.toString());
	}
}
