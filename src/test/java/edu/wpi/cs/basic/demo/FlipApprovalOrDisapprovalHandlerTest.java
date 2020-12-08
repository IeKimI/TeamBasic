package edu.wpi.cs.basic.demo;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.http.FlipApprovalRequest;
import edu.wpi.cs.basic.demo.http.FlipApprovalResponse;

public class FlipApprovalOrDisapprovalHandlerTest extends LambdaTest{
	@Test
	public void testFlipApproval() throws Exception {

		FlipApprovalHandler handler = new FlipApprovalHandler();

		FlipApprovalRequest far = new FlipApprovalRequest(1169, 128, true);
		FlipApprovalRequest far2 = new FlipApprovalRequest(1169, 128, false);

		FlipApprovalResponse f_resp = handler.handleRequest(far, createContext("flip"));

		System.out.println(f_resp.toString());
		Assert.assertEquals(f_resp.httpCode, 200);

		f_resp = handler.handleRequest(far2, createContext("flip"));
		Assert.assertEquals(f_resp.httpCode, 200);

		f_resp = handler.handleRequest(far2, createContext("flip"));
		Assert.assertEquals(f_resp.httpCode, 200);

		System.out.println(f_resp.toString());
	}
}
