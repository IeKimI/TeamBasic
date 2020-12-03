package edu.wpi.cs.basic.demo;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.ApprovalDAO;
import edu.wpi.cs.basic.demo.http.FlipApprovalRequest;
import edu.wpi.cs.basic.demo.http.FlipApprovalResponse;


public class FlipApprovalHandlerV2 implements RequestHandler<FlipApprovalRequest, FlipApprovalResponse> {
	public LambdaLogger logger;

/**
 * ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		CreateChoiceRequest ccr = new CreateChoiceRequest("testChoice4", 10, alternatives);
		String SAMPLE_INPUT_STRING = new Gson().toJson(ccr);
		System.out.print(SAMPLE_INPUT_STRING);
		String choiceID = null;
		try {
			choiceID = testSuccessInput(SAMPLE_INPUT_STRING);
		} catch (IOException ioe) {
			Assert.fail("Invalid:" + ioe.getMessage());
		}
 */
	@Override 
	public FlipApprovalResponse handleRequest(FlipApprovalRequest req, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to flip the approval for alt : "+ req.getAlternativeID() + "teamMemberID: " + req.getTeamMemberID());
		
		ApprovalDAO approvalDAO = new ApprovalDAO();
		try {
			if (approvalDAO.whatToFlip(req.getAlternativeID(), req.getTeamMemberID(), req.isWhichToFlip())) {
				logger.log("It's working");
				FlipApprovalResponse resp = new FlipApprovalResponse("Successful", 200);
				logger.log(resp.toString());
				return resp;
			}
			logger.log("NOPE");
			return new FlipApprovalResponse("BAD", 400);
		
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new FlipApprovalResponse("Failed Exception called: " + e.getMessage(), 400);
		}
	}

}
