package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.basic.demo.db.FeedbackDAO;
import edu.wpi.cs.basic.demo.http.GetFeedbackResponse;

public class TestGetFeedback extends LambdaTest{
	
	@Test
	public void testGetFeedback()  {
		FeedbackDAO feedbackDatabase= new FeedbackDAO();
		try {
			GetFeedbackHandler handler = new GetFeedbackHandler();
			GetFeedbackResponse g_resp = handler.handleRequest("1232890052", createContext("getFeedback"));
			
			assertEquals(g_resp.httpCode, 200);
			System.out.println(g_resp.getFeedback());
			
			assertTrue(feedbackDatabase.getAllFeedback(1211).size()==1);
			assertTrue(feedbackDatabase.getAllFeedback(1212).size()==1);
			assertTrue(feedbackDatabase.getAllFeedback("1232890052").size()==2);
			System.out.println(feedbackDatabase.getAllFeedback("1232890052"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
