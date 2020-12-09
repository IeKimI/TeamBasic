package edu.wpi.cs.basic.demo;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.FeedbackDAO;
import edu.wpi.cs.basic.demo.http.CompleteChoiceResponse;
import edu.wpi.cs.basic.demo.http.GetChoiceResponse;
import edu.wpi.cs.basic.demo.http.GetFeedbackResponse;
import edu.wpi.cs.basic.demo.model.Feedback;

public class GetCompleteChoiceHandler implements RequestHandler<String, CompleteChoiceResponse>{
	LambdaLogger logger;

	public CompleteChoiceResponse handleRequest(String choiceID, Context context) {
		logger = context.getLogger();
		logger.log("Attempting to get feedback with ChoiceID: " + choiceID);

		if (logger != null) {
			logger.log("in handleRequest");
		}
		// check if present
		try {
			ChoiceDAO choiceDAO = new ChoiceDAO();
			return new CompleteChoiceResponse(200, choiceDAO.isCompleted(choiceID));
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new CompleteChoiceResponse(400, false);
		}
	}
}
