package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.wpi.cs.basic.demo.db.FeedbackDAO;

public class TestGetFeedback {
	
	@Test
	public void testGetFeedback() {
		FeedbackDAO feedbackDatabase= new FeedbackDAO();
		try {
			assertTrue(feedbackDatabase.getAllFeedback(1211).size()==1);
			assertTrue(feedbackDatabase.getAllFeedback(1212).size()==1);
			assertTrue(feedbackDatabase.getAllFeedback("1232890052").size()==2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
