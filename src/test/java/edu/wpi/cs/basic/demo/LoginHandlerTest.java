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
import edu.wpi.cs.basic.demo.http.CreateTeamMemberRequest;
import edu.wpi.cs.basic.demo.http.CreateTeamMemberResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.TeamMember;


/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LoginHandlerTest extends LambdaTest {
//aru

	String testSuccessInput(String incoming) throws IOException {
		LoginHandler handler = new LoginHandler();
		CreateTeamMemberRequest req = new Gson().fromJson(incoming, CreateTeamMemberRequest.class);

		CreateTeamMemberResponse resp = handler.handleRequest(req, createContext("create"));
		System.out.println(resp.toString());
		Assert.assertEquals(200, resp.httpCode);

		System.out.println(resp.response);
		return resp.response;
		
	}

	String testFailInput(String incoming, int failureCode) throws IOException {
		LoginHandler handler = new LoginHandler();
		CreateTeamMemberRequest req = new Gson().fromJson(incoming, CreateTeamMemberRequest.class);

		CreateTeamMemberResponse resp = handler.handleRequest(req, createContext("create"));
		Assert.assertEquals(failureCode, resp.httpCode);

		return resp.response;
	}

	// NOTE: this proliferates large number of constants! Be mindful
//	@Test
//	public void testShouldBeOk() {
//	
//		String username= "un1";
//		String password="pw1";
//
//		LoginRequest lr = new LoginRequest(username, password);
//		String SAMPLE_INPUT_STRING = new Gson().toJson(lr);
//		System.out.println(SAMPLE_INPUT_STRING);
//		
//		LoginResponse l_resp = new LoginHandler().handleRequest(lr, createContext("create"));
//
//		try {
//			testSuccessInput(SAMPLE_INPUT_STRING);
//		} catch (IOException ioe) {
//			Assert.fail("Invalid:" + ioe.getMessage());
//		}
//
////        DeleteConstantRequest dcr = new DeleteConstantRequest(var);
////        DeleteConstantResponse d_resp = new DeleteConstantHandler().handleRequest(dcr, createContext("delete"));
//		System.out.println(l_resp.toString());
//		Assert.assertEquals(lr.getUsername(), l_resp.response);
//	}

	@Test
	public void testTeamMember() {
		LoginHandler handler = new LoginHandler();
		
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		
		AlternativeChoice alt1 = new AlternativeChoice("12");
		AlternativeChoice alt2 = new AlternativeChoice("123");
		AlternativeChoice alt3 = new AlternativeChoice("1234");
		
		alternatives.add(alt1); 
		alternatives.add(alt2); 
		alternatives.add(alt3); 
		String uniqueID = null; 
		
		CreateChoiceHandler choiceHandler = new CreateChoiceHandler();
		CreateChoiceRequest ccr = new CreateChoiceRequest("gegege", 3, alternatives);
		
		CreateChoiceResponse c_resp = choiceHandler.handleRequest(ccr, createContext("create"));
		uniqueID = c_resp.response;
		
		TeamMember tm1 = new TeamMember("3dg", "sersqderser", uniqueID);
//		TeamMember tm1 = new TeamMember(uniqueID, "name", "password");
//		TeamMember tm1 = new TeamMember(uniqueID, "name", "password");
//		TeamMember tm1 = new TeamMember(uniqueID, "name", "password");
		String sampleInput = "{\n" + 
				"    \"uniqueID\" : \"hello\",\n" + 
				"    \"password\" : \"ERERERERE\",\n" + 
				"    \"choiceID\" : " + uniqueID + 
				"}";

		CreateTeamMemberRequest req1 = new CreateTeamMemberRequest(tm1.getName(), tm1.getPassword(), tm1.getChoiceID());
//		CreateTeamMemberRequest req1 = new Gson().fromJson(sampleInput, CreateTeamMemberRequest.class);
		
		System.out.println(req1.toString());
		
		System.out.println(c_resp.response.toString());
		if (uniqueID == null) {
			Assert.fail("FAILED");
		}
		
		Assert.assertEquals(200, handler.handleRequest(req1, createContext("register1")).httpCode);
		
//		if(uniqueID!=null) {
//			
//		}
	}
}
