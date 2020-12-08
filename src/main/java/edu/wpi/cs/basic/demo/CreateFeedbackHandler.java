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
import edu.wpi.cs.basic.demo.http.*;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import java.sql.Timestamp;

public class CreateFeedbackHandler implements RequestHandler<CreateFeedbackRequest, CreateFeedbackResponse> {
//	boolean pruneDatabaseFunction() {
//		return false;
//	}
//
//	Choice[] getAllChoices() {
//		return null;
//	}
//
	private int id = 0;
	public Timestamp timeStamp;
	




	LambdaLogger logger;

	// To access S3 storage
	private AmazonS3 s3 = null;



	boolean createFeedback(String uniqueID, CreateFeedbackRequest req) throws Exception {
		if (logger != null) {
			logger.log("in createFeedback");
		}
		FeedbackDAO feedbackDAO = new FeedbackDAO();

		Feedback exist = feedbackDAO.getFeedback(uniqueID);
		Feedback feedback = new Feedback(); //make actual constructor for feedback here
		if (exist == null) {
			choiceDAO.addChoice(feedback);
		} else {
			return false;
		}

//		AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();
//		List<AlternativeChoice> alternatives = req.getAlternativeChoices();
//		for (AlternativeChoice alt : alternatives) {
//			alt.setChoiceID(uniqueID);
////			alt.setDescription(alt.getDescription());
//			altDAO.addAlternative(alt);
//		}
		return true;
	}


//
//	String createUniqueChoiceID(CreateFeedbackRequest req) {
//		String uniqueID = Integer.toString(Math.abs(
//				req.getDescription().hashCode() + req.getAlternativeChoices().get(1).getDescription().hashCode()*req.getAlternativeChoices().get(2).getDescription().hashCode()));
//		return uniqueID;
//	}

	@Override
	public CreateFeedbackResponse handleRequest(CreateFeedbackRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

		
		
		CreateFeedbackResponse response;
		try {
			String uniqueID = addFeedback(req);
			if (createChoice(uniqueID, req)) {
				response = new CreateChoiceResponse(uniqueID, 200);
			} else {
				response = new CreateChoiceResponse(uniqueID, 400);
			}

		} catch (Exception e) {
			response = new CreateChoiceResponse(
					"Unable to create Choice: " + req.getDescription() + "(" + e.getMessage() + ")",
					400);
		}

		return response;
	}

}