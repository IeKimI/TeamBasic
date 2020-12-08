package edu.wpi.cs.basic.demo;

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
	public GetFeedbackResponse handleRequest(String feedbackID, Context context) {
		logger = context.getLogger();
		logger.log("Attempting to get feedbackID " + feedbackID);

		if (logger != null) {
			logger.log("in getChoice");
		}
		FeedbackDAO feedbackDAO = new FeedbackDAO();

		// check if present
		try {
			Feedback feeback = feedbackDAO.getFeedback(feedbackID);
			return new GetFeedbackResponse("Succesfully fetched feedback", feeback);
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetFeedbackResponse(404, "FeedbackID could not be found");
		}
	}

}