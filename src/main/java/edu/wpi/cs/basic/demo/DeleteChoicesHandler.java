package edu.wpi.cs.basic.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.model.Choice;

public class DeleteChoicesHandler implements RequestHandler<Float, Integer> {
	LambdaLogger logger;

	public DeleteChoicesHandler() {
	}

	private List<Choice> deleteChoice(Float numberOfDays) throws Exception {
		if (logger != null)
			logger.log("deleteChoice has been called.");
		ChoiceDAO database = new ChoiceDAO();
		AlternativeChoiceDAO altDatabase = new AlternativeChoiceDAO();
		List<Choice> result = database.deleteChoicesNDaysOld(logger, numberOfDays);
		for (Choice c : result) {
			//altDatabase.deleteChoice(c);
		}
		if (logger != null)
			logger.log("Returning result to handler");
		return result;

	}

	@Override
	public Integer handleRequest(Float numberOfDays, Context context) {
		AlternativeChoiceDAO altDatabase = new AlternativeChoiceDAO();
		logger = context.getLogger();
		logger.log("Deleting Choices " + numberOfDays + " days old.");

		try {
			List<Choice> deletedItems = deleteChoice(numberOfDays);
			for (Choice choice : deletedItems) {
				logger.log("Deleted choice " + choice.uniqueID + ".");
				//altDatabase.deleteChoice(choice);
			}
			return new Integer(deletedItems.size());
		} catch (Exception e) {
			logger.log("An exception was caught in the handleRequest when deleting choices " + numberOfDays
					+ " days old.");
			return 0;
		}
	}
}