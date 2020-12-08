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

public class CreateChoiceHandler implements RequestHandler<CreateChoiceRequest, CreateChoiceResponse> {
//	boolean pruneDatabaseFunction() {
//		return false;
//	}
//
//	Choice[] getAllChoices() {
//		return null;
//	}
//
	private int id = 0;




	LambdaLogger logger;

	// To access S3 storage
	private AmazonS3 s3 = null;



	boolean createChoice(String uniqueID, CreateChoiceRequest req) throws Exception {
		if (logger != null) {
			logger.log("in createChoice");
		}
		ChoiceDAO choiceDAO = new ChoiceDAO();

		Choice exist = choiceDAO.getChoice(uniqueID);
		Choice choice = new Choice(uniqueID, req.getDescription(), req.getMaxNum());
		if (exist == null) {
			choiceDAO.addChoice(choice);
		} else {
			return false;
		}

		AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();
		List<AlternativeChoice> alternatives = req.getAlternativeChoices();
		for (AlternativeChoice alt : alternatives) {
			alt.setChoiceID(uniqueID);
//			alt.setDescription(alt.getDescription());
			altDAO.addAlternative(alt);
		}
		return true;
	}



	String createUniqueChoiceID(CreateChoiceRequest req) {
		String uniqueID = Integer.toString(Math.abs(
				req.getDescription().hashCode() + req.getAlternativeChoices().get(1).getDescription().hashCode()*req.getAlternativeChoices().get(2).getDescription().hashCode()));
		return uniqueID;
	}

	@Override
	public CreateChoiceResponse handleRequest(CreateChoiceRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateChoiceResponse response;
		try {
			String uniqueID = createUniqueChoiceID(req);
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