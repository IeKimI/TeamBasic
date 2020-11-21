package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Feedback;
import edu.wpi.cs.basic.demo.model.TeamMember;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateChoiceHandlerTest extends LambdaTest {

	String testSuccessInput(String incoming) throws IOException {
		CreateChoiceHandler handler = new CreateChoiceHandler();
		CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

		CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
		Assert.assertEquals(200, resp.httpCode);

		return resp.response;
	}

	String testFailInput(String incoming, int failureCode) throws IOException {
		CreateChoiceHandler handler = new CreateChoiceHandler();
		CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

		CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
		Assert.assertEquals(failureCode, resp.httpCode);

		return resp.response;
	}

	// NOTE: this proliferates large number of constants! Be mindful
	@Test
	public void testShouldBeOk() {
		/**
		 * public AlternativeChoice(ArrayList<TeamMember> approvals,
		 * ArrayList<TeamMember> disapprovals, ArrayList<Feedback> feedback, String
		 * description) { this.approvals = approvals; this.disapprovals = disapprovals;
		 * this.feedback = feedback; this.description = description; }
		 */
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice(null, null, null, "Hello");
		AlternativeChoice alt2 = new AlternativeChoice(null, null, null, "Hello2");

		alternatives.add(alt1);
		alternatives.add(alt2);

		System.out.println(alternatives);
		CreateChoiceRequest ccr = new CreateChoiceRequest("uniqueID", alternatives, null, "Choice1");
		String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);

		CreateChoiceResponse c_resp = new CreateChoiceHandler().handleRequest(ccr, createContext("post"));

		try {
			testSuccessInput(SAMPLE_INPUT_STRING);
		} catch (IOException ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}

//        DeleteConstantRequest dcr = new DeleteConstantRequest(var);
//        DeleteConstantResponse d_resp = new DeleteConstantHandler().handleRequest(dcr, createContext("delete"));
		Assert.assertEquals("uniqueID", c_resp.httpCode);
	}

}
