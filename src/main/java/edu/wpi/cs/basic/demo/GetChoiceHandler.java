package edu.wpi.cs.basic.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.GetChoiceResponse;
import edu.wpi.cs.basic.demo.model.Choice;

public class GetChoiceHandler implements RequestHandler<String, GetChoiceResponse> {
	LambdaLogger logger;

	public GetChoiceHandler() {

	}

	Choice getChoice(String choiceID) throws Exception {
		if (logger != null)
			logger.log("getChoice has been called.");
		ChoiceDAO database = new ChoiceDAO();
		Choice request = database.getChoice(choiceID);
		if (logger != null)
			logger.log("The choice has been got.");
		return request;

	}
	
	public GetChoiceResponse handleRequest(String choiceID, Context c) {
		logger = c.getLogger();
		logger.log("Getting Choice " + choiceID);

		try {
			return new GetChoiceResponse(choiceID, getChoice(choiceID));
		} catch (Exception e) {
			logger.log("An exception was caught in the handleRequest when getting the choice.");
			return null;
		}
	}

}
