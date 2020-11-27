package edu.wpi.cs.basic.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;

public class DeleteChoicesHandler implements RequestHandler<Integer, Boolean> {
	LambdaLogger logger;

	public DeleteChoicesHandler() {
	}

	private String deleteChoice(int n) throws Exception {
		if (logger != null)
			logger.log("deleteChoice has been called.");
		ChoiceDAO database = new ChoiceDAO();
		String result = database.deleteChoicesNDaysOld(n);
		if (logger != null)
			logger.log("Returning result to handler");
		return result;

	}

	@Override
	public Boolean handleRequest(Integer numberOfDays, Context context) {
		logger = context.getLogger();
		logger.log("Deleting Choices " + numberOfDays + " days old.");

		try {
			String deleteChoiceOutput = deleteChoice(numberOfDays.intValue());
			logger.log(deleteChoiceOutput);
			return !deleteChoiceOutput.equals("");
		} catch (Exception e) {
			logger.log("An exception was caught in the handleRequest when deleting choices " + numberOfDays
					+ " days old.");
			return false;
		}
	}
}