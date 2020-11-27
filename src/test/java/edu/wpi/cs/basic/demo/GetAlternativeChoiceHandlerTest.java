package edu.wpi.cs.basic.demo;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.http.GetAlternativesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetAlternativeChoiceHandlerTest extends LambdaTest {
	@Test
	public void testGetAlternativeChoiceHandler() throws Exception {

		GetAlternativeChoiceHandler handler = new GetAlternativeChoiceHandler();
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);

		String choiceID = null;

		CreateChoiceHandler cch = new CreateChoiceHandler();
		CreateChoiceRequest ccr = new CreateChoiceRequest("testtesttest", 10, alternatives);
		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));

		choiceID = c_resp.response;

		if (choiceID == null) {
			Assert.fail("Created choiceID is null");
		}
		GetAlternativesResponse resp = handler.handleRequest(choiceID, createContext("list"));
		if (resp.httpCode == 404) {
			Assert.fail("ChoiceID not found");
		}

		List<AlternativeChoice> listOfAlternatives = resp.alternatives;
//		for (AlternativeChoice ac : listOfAlternatives) {
//			assertTrue(alternatives.contains(ac));
//		}
		
		Assert.assertEquals(3, listOfAlternatives.size());
	}
}