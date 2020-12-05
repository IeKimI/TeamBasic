package edu.wpi.cs.basic.demo;

import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;

import edu.wpi.cs.basic.demo.db.ApprovalDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.TeamMemberDAO;
import edu.wpi.cs.basic.demo.http.CreateTeamMemberRequest;
import edu.wpi.cs.basic.demo.http.CreateTeamMemberResponse;
import edu.wpi.cs.basic.demo.http.GetAllApprovalsResponse;
import edu.wpi.cs.basic.demo.http.GetTeamMemberNameByIDHandlerResponse;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class GetTeamMemberNameByIDHandler {
public LambdaLogger logger;

	
	public GetTeamMemberNameByIDHandlerResponse handleRequest(String choiceID, int teamMemberID, Context context){
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to get the team member name given their ID: "+ teamMemberID);
		
		try {
			TeamMemberDAO dao = new TeamMemberDAO();
			String teamMemberName = dao.getTeamMemberByID(teamMemberID);
			return new GetTeamMemberNameByIDHandlerResponse(teamMemberName, 200, "Successful");
			
		} catch (Exception e) {
			logger.log(e.getMessage());
			return new GetTeamMemberNameByIDHandlerResponse("", 400 , "Failed");
		}
	}
}
