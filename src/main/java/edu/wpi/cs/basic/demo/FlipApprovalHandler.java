package edu.wpi.cs.basic.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.http.GetAlternativesResponse;

public class FlipApprovalHandler implements RequestHandler<FlipApprovalRequest, FlipApprovalResponse> {
	LambdaLogger logger;

	public FlipApprovalHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FlipApprovalResponse handleRequest(FlipApprovalRequest request, Context context) {
		logger = context.getLogger();
		// TODO Auto-generated method stub
		return null;
	}

}
