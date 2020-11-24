package edu.wpi.cs.basic.demo;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.TeamMemberDAO;
import edu.wpi.cs.basic.demo.http.*;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class LoginHandler implements RequestHandler<CreateTeamMemberRequest, CreateTeamMemberResponse> {

	LambdaLogger logger;

	// To access S3 storage
	private AmazonS3 s3 = null;

	// Note: this works, but it would be better to move this to
	// environment/configuration mechanisms
	// which you don't have to do for this project.
	// public static final String REAL_BUCKET = "constants/";

	/**
	 * Store into RDS.
	 * 
	 * @throws Exception
	 */

	boolean createTeamMember(CreateTeamMemberRequest req) throws Exception {
		if (logger != null) {
			logger.log("in createTeamMember");
		}

		TeamMemberDAO teamMemberDAO = new TeamMemberDAO();
		ChoiceDAO choiceDAO = new ChoiceDAO();

		List<TeamMember> list = teamMemberDAO.getAllTeamMembers(req.getChoiceID());
		
//		int maxNum = choiceDAO.getMaxNum(req.getChoiceID());
//		if (list.size() >= maxNum) { return false;}
		
		TeamMember exist = new TeamMember(req.getName(), req.getPassword(), req.getChoiceID());
//		TeamMember tm = new TeamMember(name);
//		if (exist == null) {
//			teamMemberDAO.addTeamMember(tm);
//		} else {
//			return false;
//		}
//		return true;
		for (TeamMember tm: list) {
			if(exist.equals(tm)) {
				return false;
			}
		}
		
		return teamMemberDAO.addTeamMember(exist);
	}

//	/** Create S3 bucket
//	 * 
//	 * @throws Exception 
//	 */
//	boolean createSystemChioce(String uniqueID, ArrayList<AlternativeChoice> alternatives, ArrayList<TeamMember> teamMembers, String description, Date dateOfCompletion, float daysOld, boolean isCompleted) throws Exception {
//		if (logger != null) { logger.log("in createSystemConstant"); }
//		
//		if (s3 == null) {
//			logger.log("attach to S3 request");
//			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
//			logger.log("attach to S3 succeed");
//		}
//
//		String folder = BucketManager.getChoiceFolder() + "/";
//		
//		byte[] contents = ("" + alternatives.get(1)).getBytes();
//		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
//		ObjectMetadata omd = new ObjectMetadata();
//		omd.setContentLength(contents.length);
//		
//		// makes the object publicly visible
//		PutObjectResult res = s3.putObject(new PutObjectRequest(BucketManager.bucket, folder + uniqueID, bais, omd)
//				.withCannedAcl(CannedAccessControlList.PublicRead));
//		
//		// if we ever get here, then whole thing was stored
//		return true;
//	}



	@Override
	public CreateTeamMemberResponse handleRequest(CreateTeamMemberRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateTeamMemberResponse response;
		try {
			
			if (createTeamMember(req)) {
				response = new CreateTeamMemberResponse("Sucessful: " + req.getName(), 200);
			} else {
				response = new CreateTeamMemberResponse("Cannot create a teamMember" + req.getName(), 400);
			}
		}catch (Exception e) {
			response = new CreateTeamMemberResponse("Cant" + req.getName() + e.getMessage());
		}	

		return response;
	}

}