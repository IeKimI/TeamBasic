package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetChoiceHandlerTest extends LambdaTest {
	@Test
	public void testGetChoiceHandler() throws Exception {
		GetChoiceHandler handler = new GetChoiceHandler();
		ChoiceDAO database = new ChoiceDAO();
		Choice input = new Choice("", "123456", 10);
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		CreateChoiceHandler cch = new CreateChoiceHandler();
		CreateChoiceRequest ccr = new CreateChoiceRequest("123456", 10, alternatives);
		CreateChoiceResponse response = cch.handleRequest(ccr, createContext("create"));
		Choice c = handler.getChoice(response.response);
		input.setUniqueID(response.response);
		input.setAlternativeChoices(alternatives);
		assertTrue(c.equals(input));
	}
}