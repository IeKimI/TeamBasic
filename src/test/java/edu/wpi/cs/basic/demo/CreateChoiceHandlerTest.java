package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.Feedback;
import edu.wpi.cs.basic.demo.model.TeamMember;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreateChoiceHandlerTest extends LambdaTest {

	String testSuccessInput(String incoming) {
		CreateChoiceHandler handler = new CreateChoiceHandler();
		CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

		CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
		System.out.println(resp.toString());
		Assert.assertEquals(200, resp.httpCode);

		System.out.println("IS this working" + resp.response);
		return resp.response;

	}

	String testFailInput(String incoming, int failureCode)  {
		CreateChoiceHandler handler = new CreateChoiceHandler();
		CreateChoiceRequest req = new Gson().fromJson(incoming, CreateChoiceRequest.class);

		CreateChoiceResponse resp = handler.handleRequest(req, createContext("create"));
		Assert.assertEquals(failureCode, resp.httpCode);

		return resp.response;
	}


//		CreateChoiceRequest ccr = new CreateChoiceRequest("t", 10, alternatives);
//		String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
////		System.out.println("{\n" + "\"alternativeChoices\" : [\n{\n" + "\"approvals\" : [],\n" + "\"disapprovals\" : [],\n"
////				+ "\"feedback\" : [],\n" + "\"description\" : " + "\"Alt1\",\n" + "\"alternativeID\" : 0\n"
////				+ "},\n" + "{\n" + "\"approvals\" : [],\n" + "\"disapprovals\" : [],\n" + "\"feedback\" : [],\n"
////				+ "\"description\" : " + "\"Alt2\",\n" + "\"alternativeID\" : 0\n" + "},\n" + "{\n"
////				+ "\"approvals\" : [],\n" + "\"disapprovals\" : [],\n" + "\"feedback\" : [],\n"
////				+ "\"description\" : " + "\"Alt3\",\n" + "\"alternativeID\" : 0\n" + "}\n" + "],\n"
////				+ "\"description\" : " + "\"Description\",\n" + "\"maxNum\" : 10" + "}\n");
//		CreateChoiceRequest c = new Gson().fromJson(
//				"{\n" + "\"alternativeChoices\" : [\n{\n" + "\"approvals\" : [],\n" + "\"disapprovals\" : [],\n"
//						+ "\"feedback\" : [],\n" + "\"description\" : " + "\"AltTest1\",\n" + "\"alternativeID\" : 0\n"
//						+ "},\n" + "{\n" + "\"approvals\" : [],\n" + "\"disapprovals\" : [],\n" + "\"feedback\" : [],\n"
//						+ "\"description\" : " + "\"AltTest2\",\n" + "\"alternativeID\" : 0\n" + "},\n" + "{\n"
//						+ "\"approvals\" : [],\n" + "\"disapprovals\" : [],\n" + "\"feedback\" : [],\n"
//						+ "\"description\" : " + "\"AltTest3\",\n" + "\"alternativeID\" : 0\n" + "}\n" + "],\n"
//						+ "\"description\" : " + "\"Description\",\n" + "\"maxNum\" : 10" + "}\n",
//				CreateChoiceRequest.class);

	@Test
	public void testShouldBeOk() throws Exception{
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		ChoiceDAO choiceDAO = new ChoiceDAO();
		CreateChoiceRequest ccr = new CreateChoiceRequest("CreateChoiceTest", 10, alternatives);
		CreateChoiceHandler cch = new CreateChoiceHandler();

		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));
		try {
			Assert.assertEquals(c_resp.httpCode, 200);
			boolean result = choiceDAO.deleteChoice(c_resp.response);
			Assert.assertTrue(result);
		} catch (Exception ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}

	}
}
