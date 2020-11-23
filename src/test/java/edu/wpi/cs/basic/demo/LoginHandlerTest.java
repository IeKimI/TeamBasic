package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.basic.demo.http.LoginRequest;
import edu.wpi.cs.basic.demo.http.LoginResponse;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LoginHandlerTest extends LambdaTest {
//aru

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
	
		String username= "un1";
		String password="pw1";

		LoginRequest lr = new LoginRequest(username, password);
		String SAMPLE_INPUT_STRING = new Gson().toJson(lr);
		System.out.println(SAMPLE_INPUT_STRING);
		
		LoginResponse l_resp = new LoginHandler().handleRequest(lr, createContext("create"));

		try {
			testSuccessInput(SAMPLE_INPUT_STRING);
		} catch (IOException ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}

//        DeleteConstantRequest dcr = new DeleteConstantRequest(var);
//        DeleteConstantResponse d_resp = new DeleteConstantHandler().handleRequest(dcr, createContext("delete"));
		System.out.println(l_resp.toString());
		Assert.assertEquals(lr.getUsername(), l_resp.response);
	}

}
