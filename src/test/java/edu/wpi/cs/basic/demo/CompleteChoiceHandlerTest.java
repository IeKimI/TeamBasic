package edu.wpi.cs.basic.demo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

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
		
		CompleteChoiceHandler handler = new CompleteChoiceHandler();
		
		ChoiceDAO database = new ChoiceDAO();
		
//		Choice input = new Choice("123456", "testCompleteChoice", 10);
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		
		CompleteChoiceHandler cch = new CompleteChoiceHandler();
		CompleteChoiceRequest ccr = new CompleteChoiceRequest("1749316646",1232);
		CompleteChoiceResponse c_resp = cch.handleRequest(ccr, createContext("complete"));
		Assert.assertEquals(200, c_resp.statusCode);
		
//		Boolean isCompletedTest;
//		isCompletedTest = c_resp.isComplete;
//		if (isCompletedTest == false) {Assert.fail("Not completed");
//		
//		}
	}
}