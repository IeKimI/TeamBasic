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

	// NOTE: this proliferates large number of constants! Be mindful
//	@Test
//	public void testShouldBeOk() {
//
//		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
//
//		AlternativeChoice alt1 = new AlternativeChoice("Alt1");
//		AlternativeChoice alt2 = new AlternativeChoice("Alt2");
//		AlternativeChoice alt3 = new AlternativeChoice("Alt3");
//
//		alternatives.add(alt1);
//		alternatives.add(alt2);
//		alternatives.add(alt3);
//
//
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
//		
//		System.out.println(SAMPLE_INPUT_STRING);
//		String sample = new Gson().toJson(c);
//		System.out.println(sample);
//
//
//		CreateChoiceResponse c_resp = new CreateChoiceHandler().handleRequest(ccr, createContext("create"));
////		CreateChoiceResponse c_resp2 = new CreateChoiceHandler().handleRequest(c, createContext("create"));
//
//
//		try {
//			testSuccessInput(SAMPLE_INPUT_STRING);
//		} catch (IOException ioe) {
//			Assert.fail("Invalid:" + ioe.getMessage());
//		}
////        DeleteConstantRequest dcr = new DeleteConstantRequest(var);
////        DeleteConstantResponse d_resp = new DeleteConstantHandler().handleRequest(dcr, createContext("delete"));
//
//		System.out.println(c_resp.response);
////		System.out.println(c_resp2.toString());
//		
//
//		Assert.assertEquals(ccr.getDescription(), c_resp.response);
//	}
	@Test
	public void testShouldBeOk() throws Exception{
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		CreateChoiceRequest ccr = new CreateChoiceRequest("geege", 10, alternatives);
		String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
		CreateChoiceHandler cch = new CreateChoiceHandler();

		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));
		System.out.print(SAMPLE_INPUT_STRING);
		String choiceID = null;
		try {
			Assert.assertEquals(c_resp.httpCode, 200);
		} catch (Exception ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}

	}
}
