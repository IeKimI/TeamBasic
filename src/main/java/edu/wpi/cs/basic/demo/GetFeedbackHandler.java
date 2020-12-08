package edu.wpi.cs.basic.demo;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.FeedbackDAO;
import edu.wpi.cs.basic.demo.db.FeedbackDAO;
import edu.wpi.cs.basic.demo.http.GetFeedbackResponse;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.Feedback;

public class GetFeedbackHandler implements RequestHandler<String, GetFeedbackResponse> {

	LambdaLogger logger;

	@Override
	public GetFeedbackResponse handleRequest(String choiceID, Context context) {
		logger = context.getLogger();
		logger.log("Attempting to get feedback with ChoiceID: " + choiceID);

		if (logger != null) {
			logger.log("in handleRequest");
		}
		// check if present
		try {
			List<Feedback> feedback = FeedbackDAO.getAllFeedback(choiceID);
			return new GetFeedbackResponse("Succesfully fetched feedback: " + feedback.toString(), 200, feedback);
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetFeedbackResponse("FeedbackID could not be found", 400, new ArrayList<Feedback>());
		}
	}

}