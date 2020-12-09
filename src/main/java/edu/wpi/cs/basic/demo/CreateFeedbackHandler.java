package edu.wpi.cs.basic.demo;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.FeedbackDAO;
import edu.wpi.cs.basic.demo.http.*;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.Feedback;

import java.sql.Timestamp;

public class CreateFeedbackHandler implements RequestHandler<CreateFeedbackRequest, CreateFeedbackResponse> {
	LambdaLogger logger;

	boolean createFeedback(CreateFeedbackRequest req) throws Exception {
		if (logger != null) {
			logger.log("in createFeedback");
		}
		FeedbackDAO feedbackDAO = new FeedbackDAO();
		// String text, int teamMemberID, int alternativeChoiceID, int feedbackID
		Feedback feedback = new Feedback(req.getText(), req.getTeamMemberID(), req.getAlternativeChoiceID()); // make actual constructor for feedback here
		return feedbackDAO.addFeedback(feedback);
	}

	@Override
	public CreateFeedbackResponse handleRequest(CreateFeedbackRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

		try {
			if (!createFeedback(req)) {
				throw new Exception();
			}
			return new CreateFeedbackResponse("Text: " + req.getText() + "FeedbackID:" + req.getFeedbackID(), 200);
		} catch (Exception e) {
			return new CreateFeedbackResponse(
					"Unable to create Feedback: " + req.getFeedbackID() + "(" + e.getMessage() + ")", 400);

		}
	}

}