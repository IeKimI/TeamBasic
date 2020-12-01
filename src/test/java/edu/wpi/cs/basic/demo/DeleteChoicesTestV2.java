package edu.wpi.cs.basic.demo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.http.DeleteChoicesRequest;
import edu.wpi.cs.basic.demo.http.DeleteChoicesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;

public class DeleteChoicesTestV2 extends LambdaTest{

	@Test
	public void testCreateAndDeleteConstant() {
		// create choice
		CreateChoiceHandler handler = new CreateChoiceHandler();

		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		CreateChoiceRequest ccr = new CreateChoiceRequest("TESTING3", 10, alternatives);
		CreateChoiceResponse resp = handler.handleRequest(ccr, createContext("create"));
		Assert.assertEquals(200, resp.httpCode);

		// now delete
		DeleteChoicesRequest dcr = new DeleteChoicesRequest(100);
		DeleteChoicesResponse d_resp = new DeleteChoicesHandlerV2().handleRequest(dcr, createContext("delete"));
		Assert.assertEquals(200, d_resp.statusCode);

		// try again and this should fail
		d_resp = new DeleteChoicesHandlerV2().handleRequest(dcr, createContext("delete"));
		Assert.assertEquals(422, d_resp.statusCode);
	}

}
