package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.http.DeleteChoicesRequest;
import edu.wpi.cs.basic.demo.http.DeleteChoicesResponse;
import edu.wpi.cs.basic.demo.http.GetAlternativesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;

public class DeleteChoicesHandlerTest extends LambdaTest {

	@Test
	public void testDeleteChoices() throws Exception {
		CreateChoiceHandler handler = new CreateChoiceHandler();

		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		CreateChoiceRequest ccr = new CreateChoiceRequest("DeleteChoiceTest", 10, alternatives);
		CreateChoiceResponse choiceResponse = handler.handleRequest(ccr, createContext("create"));
		Assert.assertEquals(200, choiceResponse.httpCode);

		// now delete
		DeleteChoicesRequest dcr = new DeleteChoicesRequest(1);
		DeleteChoicesResponse d_resp = new DeleteChoicesHandler().handleRequest(dcr, createContext("delete"));
		Assert.assertEquals(200, d_resp.statusCode);

		GetAlternativeChoiceHandler gach = new GetAlternativeChoiceHandler();
		GetAlternativesResponse gar = gach.handleRequest(choiceResponse.response, createContext("list"));
//		Assert.assertTrue(!gar.alternatives.isEmpty());
		Assert.assertEquals(200, gar.httpCode);
		
//		// deleting everything
//		DeleteChoicesRequest dcr2 = new DeleteChoicesRequest(0);
//		DeleteChoicesResponse d_resp2 = new DeleteChoicesHandler().handleRequest(dcr2, createContext("delete"));
//		Assert.assertEquals(200, d_resp2.statusCode);
//		GetAlternativeChoiceHandler gach2 = new GetAlternativeChoiceHandler();
//		GetAlternativesResponse gar2 = gach2.handleRequest(choiceResponse.response, createContext("list"));
////		Assert.assertTrue(gar2.alternatives.isEmpty());
//		Assert.assertEquals(200, gar2.httpCode);
//		for(AlternativeChoice alt: gar2.alternatives) {
//			
//		}
	}
} 