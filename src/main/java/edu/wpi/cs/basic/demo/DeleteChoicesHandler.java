package edu.wpi.cs.basic.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ApprovalDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.TeamMemberDAO;
import edu.wpi.cs.basic.demo.http.DeleteChoicesRequest;
import edu.wpi.cs.basic.demo.http.DeleteChoicesResponse;
import edu.wpi.cs.basic.demo.model.Approval;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.Choice;

public class DeleteChoicesHandler implements RequestHandler<DeleteChoicesRequest, DeleteChoicesResponse> {
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
			if (altDatabase.deleteAlternativeChoice(logger, c))
				continue;
			throw new Exception("Failed to Delete Alternative Choice");
		}
		if (logger != null)
			logger.log("Returning result to handler");
		return result;
 
	}

	@Override
	public DeleteChoicesResponse handleRequest(DeleteChoicesRequest request, Context context) {
		Float numberOfDays = request.getNDaysOld();
		AlternativeChoiceDAO altDatabase = new AlternativeChoiceDAO();
		ApprovalDAO approvalDatabase = new ApprovalDAO();
		TeamMemberDAO teamMemberDatabase = new TeamMemberDAO();
		logger = context.getLogger();
		logger.log("Deleting Choices " + numberOfDays + " days old.");

		try {
			List<Choice> deletedItems = deleteChoice(numberOfDays);
			
			for (Choice choice : deletedItems) {
				logger.log("Deleted choice " + choice.uniqueID + ".");
			
				List<ApprovalInfo> listOfApprovals = approvalDatabase.getApprovalsChoiceID(logger, choice.getUniqueID());
				logger.log(Integer.toString(listOfApprovals.size()));
				for (ApprovalInfo approval : listOfApprovals) {
					for (String teamMemberName : approval.getListOfTeamMembers()) {
						approvalDatabase.deleteApproval(logger, approval.getAlternativeID(),
								teamMemberDatabase.getTeamMemberID(teamMemberName));
					}
				}
				altDatabase.deleteAlternativeChoice(logger, choice);
			}
			return new DeleteChoicesResponse(numberOfDays, 200);
		} catch (Exception e) {
			logger.log("An exception was caught in the handleRequest when deleting choices " + numberOfDays
					+ " days old.");
			return new DeleteChoicesResponse(numberOfDays, 400, "The Choices Failed to Delete");
		}
	}
}