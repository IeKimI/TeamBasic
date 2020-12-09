package edu.wpi.cs.basic.demo;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.http.GetAllChoicesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;

public class ListAllChoicesHandlerTest extends LambdaTest {

	@Test
	public void testGetList() {
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		ChoiceDAO choiceDAO = new ChoiceDAO();
		CreateChoiceRequest ccr = new CreateChoiceRequest("ListAllChoicesTest", 10, alternatives);
		CreateChoiceHandler cch = new CreateChoiceHandler();

		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));
		try {
			Assert.assertEquals(c_resp.httpCode, 200);
			boolean result = choiceDAO.deleteChoice(c_resp.response);
			Assert.assertTrue(result);
		} catch (Exception ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}
		ListAllChoicesHandler handler = new ListAllChoicesHandler();

		GetAllChoicesResponse resp = handler.handleRequest(null, createContext("list"));

		
		Assert.assertEquals(200, resp.statusCode);

	}
}
