package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;

public class DeleteChoicesHandlerTest extends LambdaTest {

	@Test
	public void testDeleteChoices() throws Exception {
		GetChoiceHandler handler = new GetChoiceHandler();
		ChoiceDAO database = new ChoiceDAO();
		DeleteChoicesHandler dch = new DeleteChoicesHandler();
		CreateChoiceHandler cch = new CreateChoiceHandler();
		CreateChoiceRequest ccr = new CreateChoiceRequest("Test1", 10, null);
		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));

		dch.handleRequest((float) 0, createContext("delete"));
		assertTrue(database.getAllChoices().size() == 0);

		ccr = new CreateChoiceRequest("Test1", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		ccr = new CreateChoiceRequest("Test2", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		ccr = new CreateChoiceRequest("Test3", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));

		dch.handleRequest((float) 0, createContext("delete"));
		assertTrue(database.getAllChoices().size() == 0);

		ccr = new CreateChoiceRequest("Test1", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		dch.handleRequest((float) 1, createContext("delete"));

		assertTrue(database.getAllChoices().size() == 1);

		ccr = new CreateChoiceRequest("Test1", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		ccr = new CreateChoiceRequest("Test2", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));
		ccr = new CreateChoiceRequest("Test3", 10, null);
		c_resp = cch.handleRequest(ccr, createContext("create"));

		dch.handleRequest((float) 1, createContext("delete"));
		assertTrue(database.getAllChoices().size() == 3);
	}

	@Test
	public void testDeleteAlternatives() throws Exception {
		AlternativeChoiceDAO database = new AlternativeChoiceDAO();
		DeleteChoicesHandler dch = new DeleteChoicesHandler();
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);

		CreateChoiceHandler cch = new CreateChoiceHandler();
		CreateChoiceRequest ccr = new CreateChoiceRequest("TestDeleteAlternatives", 10, alternatives);
		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));

		dch.handleRequest((float) 0, createContext("delete"));

		assertTrue(database.getAllAlternatives(c_resp.response).size() == 0);
	}

}
