package edu.wpi.cs.basic.demo;

import java.sql.SQLException;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ApprovalDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.ApprovalResponse;
import edu.wpi.cs.basic.demo.http.FlipApprovalRequest;
import edu.wpi.cs.basic.demo.http.FlipApprovalResponse;
import edu.wpi.cs.basic.demo.http.GetAlternativesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.Choice;

public class FlipApprovalHandler implements RequestHandler<FlipApprovalRequest, FlipApprovalResponse> {
	LambdaLogger logger;

	public FlipApprovalHandler() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public FlipApprovalResponse handleRequest(FlipApprovalRequest request, Context context) {
		logger = context.getLogger();
		if (logger != null) {
			logger.log("in flipApprovalChoice");
		}
		ApprovalDAO approvalDatabase = new ApprovalDAO(); 
		ChoiceDAO choiceDAO = new ChoiceDAO();
		try {
			if (choiceDAO.isCompleted(request.getChoiceID())) {
				return new FlipApprovalResponse("Choice is completed", 400);
			}
			List<ApprovalInfo> list = approvalDatabase.getApprovalsChoiceID(logger, request.getChoiceID());
			return approvalDatabase.flipApprovalOrDisapproval(logger, request.isWhichToFlip(), request.getAlternativeID(),
					request.getTeamMemberID(), list);
		} catch (Exception e) {
			logger.log(e.toString());
		}
		return new FlipApprovalResponse("Failed", 400);
		
	}
}
