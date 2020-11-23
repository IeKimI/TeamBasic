package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.basic.demo.http.LoginRequest;
import edu.wpi.cs.basic.demo.http.LoginResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.Feedback;
import edu.wpi.cs.basic.demo.model.TeamMember;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LoginHandlerTest extends LambdaTest {

	String testSuccessInput(String incoming) throws IOException {
		LoginHandler handler = new LoginHandler();
		LoginRequest req = new Gson().fromJson(incoming, LoginRequest.class);

		LoginResponse resp = handler.handleRequest(req, createContext("create"));
		System.out.println(resp.toString());
		Assert.assertEquals(200, resp.httpCode);

		System.out.println(resp.response);
		return resp.response;
		
	}

	String testFailInput(String incoming, int failureCode) throws IOException {
		LoginHandler handler = new LoginHandler();
		LoginRequest req = new Gson().fromJson(incoming, LoginRequest.class);

		LoginResponse resp = handler.handleRequest(req, createContext("create"));
		Assert.assertEquals(failureCode, resp.httpCode);

		return resp.response;
	}

	// NOTE: this proliferates large number of constants! Be mindful
	@Test
	public void testShouldBeOk() {
	
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();

		AlternativeChoice alt1 = new AlternativeChoice("Alt1");
		AlternativeChoice alt2 = new AlternativeChoice("Alt2");
		AlternativeChoice alt3 = new AlternativeChoice("Alt3");

		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);

		LoginRequest lr = new LoginRequest("Choice_Description", 10, alternatives);
		String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
		System.out.println(SAMPLE_INPUT_STRING);
		
		LoginResponse c_resp = new CreateChoiceHandler().handleRequest(ccr, createContext("create"));

		try {
			testSuccessInput(SAMPLE_INPUT_STRING);
		} catch (IOException ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}

//        DeleteConstantRequest dcr = new DeleteConstantRequest(var);
//        DeleteConstantResponse d_resp = new DeleteConstantHandler().handleRequest(dcr, createContext("delete"));
		System.out.println(c_resp.toString());
		Assert.assertEquals(ccr.getDescription(), c_resp.response);
	}

}
