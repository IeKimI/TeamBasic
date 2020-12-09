package edu.wpi.cs.basic.demo;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ApprovalDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.FeedbackDAO;
import edu.wpi.cs.basic.demo.db.TeamMemberDAO;
import edu.wpi.cs.basic.demo.http.DeleteChoicesRequest;
import edu.wpi.cs.basic.demo.http.DeleteChoicesResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
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
		ApprovalDAO approvalDatabase = new ApprovalDAO();
		List<Choice> result = database.deleteChoicesNDaysOld(logger, numberOfDays);
//		for (Choice c : result) {
//			List<AlternativeChoice> deletedAlternatives = altDatabase.getAllAlternatives(c.uniqueID);
//			if (altDatabase.deleteAlternativeChoice(logger, c)) {
//				for (AlternativeChoice altChoice : deletedAlternatives) {
//					if (approvalDatabase.deleteApproval(logger, altChoice.getAlternativeID())) {
//						continue;
//					}
//					throw new Exception("Failed to Delete Approval from Alternative Choice ID");
//				}
//				continue;
//			}
//			throw new Exception("Failed to Delete Alternative Choice");
//		}
		if (logger != null)
			logger.log("Returning result to handler");
		return result;

	}

	@Override
	public DeleteChoicesResponse handleRequest(DeleteChoicesRequest request, Context context) {
		Float numberOfDays = request.getNDaysOld();
		ChoiceDAO choiceDatabase = new ChoiceDAO();
		AlternativeChoiceDAO altDatabase = new AlternativeChoiceDAO();
		ApprovalDAO approvalDatabase = new ApprovalDAO();
		TeamMemberDAO teamMemberDatabase = new TeamMemberDAO();
		FeedbackDAO feedbackDatabase = new FeedbackDAO();
		logger = context.getLogger();
		logger.log("Deleting Choices " + numberOfDays + " days old.");
		List<Choice> deletedChoices = choiceDatabase.getAllChoicesNDaysOld(logger, request.nDaysOld);
		List<AlternativeChoice> deletedAlternatives = new ArrayList<AlternativeChoice>();
		for (Choice c : deletedChoices) {
			try {
				deletedAlternatives.addAll(altDatabase.getAlternativeChoice(c.uniqueID));
			} catch (Exception e) {
				logger.log("SQL Error when getting alternatives to delete");
			}
		}
		try {
			for (AlternativeChoice alt : deletedAlternatives) {
				approvalDatabase.deleteApproval(logger, alt.getAlternativeID());
				feedbackDatabase.deleteFeedback(logger, alt.getAlternativeID());
			}
			for (Choice c : deletedChoices) {
				teamMemberDatabase.deleteTeamMember(logger, c.uniqueID);
				altDatabase.deleteAlternativeChoice(logger, c);
			}
			deleteChoice(numberOfDays);
// for (Choice choice : deletedItems) {
// logger.log("Deleted choice " + choice.uniqueID + ".");
//
// List<ApprovalInfo> listOfApprovals = approvalDatabase.getApprovalsChoiceID(logger, choice.uniqueID);
// logger.log(Integer.toString(listOfApprovals.size()));
// for (ApprovalInfo approval : listOfApprovals) {
// for (String teamMemberName : approval.getListOfTeamMembers()) {
// approvalDatabase.deleteApproval(logger, approval.getAlternativeID());
// }
// }
// }
			System.out.println(choiceDatabase.getAllChoices());
			return new DeleteChoicesResponse(numberOfDays, 200, choiceDatabase.getAllChoices(), "Successful");
		} catch (Exception e) {
			logger.log("An exception was caught in the handleRequest when deleting choices " + numberOfDays
					+ " days old. Exception: " + e.toString());
			return new DeleteChoicesResponse(numberOfDays, 400, null, "The Choices Failed to Delete");
		}
	}
}