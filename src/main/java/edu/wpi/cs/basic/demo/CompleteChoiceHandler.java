package edu.wpi.cs.basic.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.http.CompleteChoiceRequest;
import edu.wpi.cs.basic.demo.http.CompleteChoiceResponse;

public class CompleteChoiceHandler implements RequestHandler<CompleteChoiceRequest, CompleteChoiceResponse>{
	
	LambdaLogger logger;

	@Override
	public CompleteChoiceResponse handleRequest(CompleteChoiceRequest input, Context context) {
		logger = context.getLogger();
		CompleteChoiceResponse response;
		if (logger != null) {
			logger.log("in CompleteChoice");
		}
		return null;
		
		
	}

	

}
