package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;

public class DeleteChoicesHandlerTest extends LambdaTest {

	@Test
	public void testDeleteChoices() throws Exception {
		GetChoiceHandler handler = new GetChoiceHandler();
		ChoiceDAO database = new ChoiceDAO();

		CreateChoiceHandler cch = new CreateChoiceHandler();
		CreateChoiceRequest ccr = new CreateChoiceRequest("Test1", 10, null);
		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));

		database.deleteChoicesNDaysOld(100);
		assertTrue(database.getAllChoices().size() == 0);

		ccr = new CreateChoiceRequest("Test1", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		ccr = new CreateChoiceRequest("Test2", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		ccr = new CreateChoiceRequest("Test3", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));

		database.deleteChoicesNDaysOld(100);
		assertTrue(database.getAllChoices().size() == 0);

		ccr = new CreateChoiceRequest("Test1", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));

		database.deleteChoicesNDaysOld(100);
		assertTrue(database.getAllChoices().size() == 1);

		ccr = new CreateChoiceRequest("Test1", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		ccr = new CreateChoiceRequest("Test2", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		ccr = new CreateChoiceRequest("Test3", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));

		database.deleteChoicesNDaysOld(100);
		assertTrue(database.getAllChoices().size() == 3);

	}

}
