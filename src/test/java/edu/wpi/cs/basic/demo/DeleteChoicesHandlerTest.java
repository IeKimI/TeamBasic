package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;

public class DeleteChoicesHandlerTest extends LambdaTest {

	@Test
	public boolean testDeleteChoices() {
		GetChoiceHandler handler = new GetChoiceHandler();
		ChoiceDAO database = new ChoiceDAO();

		CreateChoiceHandler cch = new CreateChoiceHandler();
		CreateChoiceRequest ccr = new CreateChoiceRequest("Test1", 10, null);
		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));

		database.deleteChoicesNDaysOld(0);
		assertTrue(database.getAllChoices().size() == 0);
		/*
		 * if (resp.httpCode == 404) { Assert.fail("ChoiceID not found"); }
		 * 
		 * Choice choice = resp.choice;
		 * 
		 * Assert.assertTrue(choice.getDescription().equals("testtesttest"));
		 * Assert.assertTrue(choice.getMaxNumOfTeamMembers() == 10);
		 * 
		 * ccr = new CreateChoiceRequest("Test2", 10, null); c_resp =
		 * cch.handleRequest(ccr, createContext("create")); ccr = new
		 * CreateChoiceRequest("Test2", 10, null); c_resp = cch.handleRequest(ccr,
		 * createContext("create"));
		 */
	}

}
