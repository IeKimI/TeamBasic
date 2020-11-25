package edu.wpi.cs.basic.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.http.GetAlternativesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;

public class GetAlternativeChoiceHandler implements RequestHandler<String, GetAlternativesResponse> {
	LambdaLogger logger;

	public GetAlternativeChoiceHandler() {
		// TODO Auto-generated constructor stub
	}

	List<AlternativeChoice> getAlternativeChoices(String choiceID) throws Exception {
		if (logger != null)
			logger.log("getAlternativeChoices has been called.");
		AlternativeChoiceDAO database = new AlternativeChoiceDAO();
		List<AlternativeChoice> request = database.getAlternativeChoice(choiceID);
		if (logger != null)
			logger.log("The alternatives have been got.");
		return request;
	}

	public GetAlternativesResponse handleRequest(String choiceID, Context c) {
		logger = c.getLogger();
		logger.log("Getting Alternatives for Choice " + choiceID + ".");

		try {
			return new GetAlternativesResponse(choiceID, getAlternativeChoices(choiceID));
		} catch (Exception e) {
			logger.log(
					"An exception was caught in the handleRequest when getting the alternatives associated with choice "
							+ choiceID + ".");
			return null;
		}
	}
}
