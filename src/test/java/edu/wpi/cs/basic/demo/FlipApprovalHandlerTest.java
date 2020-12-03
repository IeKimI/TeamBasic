package edu.wpi.cs.basic.demo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.http.FlipApprovalRequest;
import edu.wpi.cs.basic.demo.http.FlipApprovalResponse;
import edu.wpi.cs.basic.demo.http.GetAlternativesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;

public class FlipApprovalHandlerTest extends LambdaTest {

	@Test
	public void testFlipApproval() throws Exception {

		FlipApprovalHandler handler = new FlipApprovalHandler();

		FlipApprovalRequest far = new FlipApprovalRequest(736, 65, true);
		FlipApprovalRequest far2 = new FlipApprovalRequest(736, 65, false);

		FlipApprovalResponse f_resp = handler.handleRequest(far, createContext("flip"));

		System.out.println(f_resp.toString());
		Assert.assertEquals(f_resp.httpCode, 200);

		f_resp = handler.handleRequest(far2, createContext("flip"));
		Assert.assertEquals(f_resp.httpCode, 200);
		
		f_resp = handler.handleRequest(far2, createContext("flip"));
		Assert.assertEquals(f_resp.httpCode, 200);
	}

}
