package edu.wpi.cs.basic.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;

public class GetAlternativeChoiceHandler implements RequestHandler<String, AlternativeChoice> {
	LambdaLogger logger;
	public GetAlternativeChoiceHandler() {
		// TODO Auto-generated constructor stub
	}

	AlternativeChoice getAlternativeChoice(String altID) throws Exception {
		if (logger != null)
			logger.log("getChoice has been called.");
		AlternativeChoiceDAO database = new AlternativeChoiceDAO();
		AlternativeChoice request = database.getAlternativeChoice(altID);
		if (logger != null)
			logger.log("The choice has been got.");
		return request;
	}
	
	public AlternativeChoice handleRequest(String altID, Context c) {
		logger = c.getLogger();
		logger.log("Getting Choice " + altID);

		try {
			return getAlternativeChoice(altID);
		} catch (Exception e) {
			logger.log("An exception was caught in the handleRequest when getting the choice.");
			return null;
		}
	}
}
