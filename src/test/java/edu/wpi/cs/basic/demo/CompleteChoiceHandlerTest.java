package edu.wpi.cs.basic.demo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CompleteChoiceRequest;
import edu.wpi.cs.basic.demo.http.CompleteChoiceResponse;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;

public class CompleteChoiceHandlerTest extends LambdaTest {
	@Test
	public void testCompleteChoiceHandler() throws Exception {
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		ChoiceDAO choiceDAO = new ChoiceDAO();
		CreateChoiceRequest ccr = new CreateChoiceRequest("CompleteChoiceTeset", 10, alternatives);
		CreateChoiceHandler cch = new CreateChoiceHandler();

		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));

		Assert.assertEquals(c_resp.httpCode, 200);


		CompleteChoiceHandler handler = new CompleteChoiceHandler();
//		Choice input = new Choice("123456", "testCompleteChoice", 10);

		AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();
		
		int altID = altDAO.getAltIDByChoiceIDAndDesc(c_resp.response, "alt1_description");
		
		CompleteChoiceRequest completeRequest = new CompleteChoiceRequest(c_resp.response, altID);
		CompleteChoiceResponse completeResponse = handler.handleRequest(completeRequest, createContext("complete"));
		Assert.assertEquals(200, completeResponse.statusCode);

		// delete the created choice so that the test passes all the time
		boolean result = choiceDAO.deleteChoice(c_resp.response);
		Assert.assertTrue(result);
	}
}