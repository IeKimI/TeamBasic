package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.FeedbackDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.http.GetFeedbackResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;

public class GetFeedbackTest extends LambdaTest{ 
	
	@Test
	public void testGetFeedback()  {
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		ChoiceDAO choiceDAO = new ChoiceDAO();
		AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();
		CreateChoiceRequest ccr = new CreateChoiceRequest("GetFeedbackTest", 10, alternatives);
		CreateChoiceHandler cch = new CreateChoiceHandler();

		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));
		try {
			Assert.assertEquals(c_resp.httpCode, 200);
			boolean result = choiceDAO.deleteChoice(c_resp.response);
			Assert.assertTrue(result);
		} catch (Exception ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}

		FeedbackDAO feedbackDatabase= new FeedbackDAO();
		try {
			GetFeedbackHandler handler = new GetFeedbackHandler();
			GetFeedbackResponse g_resp = handler.handleRequest(c_resp.response, createContext("getFeedback"));
			
			assertEquals(g_resp.httpCode, 200);
			System.out.println(g_resp.getFeedback());
			
			int alt1ID = altDAO.getAltIDByChoiceIDAndDesc(c_resp.response, "alt1_description");
			
			assertTrue(feedbackDatabase.getAllFeedback(alt1ID).size()==0);
	
			System.out.println(feedbackDatabase.getAllFeedback(c_resp.response));
			
			Assert.assertEquals(feedbackDatabase.getAllFeedback(alt1ID), feedbackDatabase.getAllFeedback(c_resp.response));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
