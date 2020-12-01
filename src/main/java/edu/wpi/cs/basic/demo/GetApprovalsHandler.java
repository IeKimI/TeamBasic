package edu.wpi.cs.basic.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ApprovalDAO;
import edu.wpi.cs.basic.demo.http.ApprovalResponse;
import edu.wpi.cs.basic.demo.http.GetAllApprovalsResponse;
import edu.wpi.cs.basic.demo.http.GetAlternativesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;

public class GetApprovalsHandler implements RequestHandler<String, GetAllApprovalsResponse> {
public LambdaLogger logger;

	
	@Override 
	public GetAllApprovalsResponse handleRequest(String choiceID, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all approvals for ID: "+ choiceID);
		
		try {
			ApprovalDAO dao = new ApprovalDAO();
			List<ApprovalInfo> approvals = dao.getApprovalsChoiceID(choiceID);
			return new GetAllApprovalsResponse(approvals, 200);
			
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetAllApprovalsResponse(404, "Approvals not found " + e.getMessage());
		}
	}

}
