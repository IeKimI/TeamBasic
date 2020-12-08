package edu.wpi.cs.basic.demo;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.CompleteChoiceRequest;
import edu.wpi.cs.basic.demo.http.CompleteChoiceResponse;
import edu.wpi.cs.basic.demo.http.GetChoiceResponse;
import edu.wpi.cs.basic.demo.model.Choice;

public class CompleteChoiceHandler implements RequestHandler<CompleteChoiceRequest, CompleteChoiceResponse> {

	LambdaLogger logger;

	@Override
	public CompleteChoiceResponse handleRequest(CompleteChoiceRequest request, Context context) {
		String choiceID = request.getChoiceID();
		String chosenAltID = request.getChosenAltID();
		ChoiceDAO choiceDao = new ChoiceDAO();
		Choice currentChoice;
		try {
			currentChoice = choiceDao.getChoice(choiceID);
			logger = context.getLogger();
			logger.log("Completing choice " + choiceID);
			CompleteChoiceResponse response;
			if (logger != null) {
				logger.log("in completeChoice");
			}
			try {
//				Choice choice = choiceDao.getChoice(choiceID);
				choiceDao.updateChoice(currentChoice, chosenAltID);
				return new CompleteChoiceResponse("Succesfully completed choice", 200);
			} catch (Exception e) {
				logger.log(e.getMessage());
				return new CompleteChoiceResponse("ChoiceID could not be completed", 404);
			}
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			return new CompleteChoiceResponse("ChoiceID could not be completed", 404);
		}
//		logger = context.getLogger();
//		logger.log("Completing choice " + choiceID);
//		CompleteChoiceResponse response;
//		if (logger != null) {
//			logger.log("in completeChoice");
//		}
//		try {
////			Choice choice = choiceDao.getChoice(choiceID);
//			choiceDao.updateChoice(currentChoice, chosenAltID);
//			return new CompleteChoiceResponse("Succesfully completed choice", 200);
//		} catch (Exception e) {
//			logger.log(e.getMessage());
//			return new CompleteChoiceResponse("ChoiceID could not be completed", 404);
//		}

//		List<Choice> deletedChoices = choiceDatabase.getAllChoicesNDaysOld(logger, request.nDaysOld);
//		List<AlternativeChoice> deletedAlternatives = new ArrayList<AlternativeChoice>();

	}

}
