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

public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {
//	boolean pruneDatabaseFunction() {
//		return false;
//	}
//
//	Choice[] getAllChoices() {
//		return null;
//	}
//
	private int id = 0;

	public String getNextID() {
		// Can through database for earliest available ID
		return (new Integer(id++)).toString();
	}

//
//	public boolean pushChoice(Choice choice) {
//		return false;
//	}
//
//	@Override
//	public CreateChoiceResponse handleRequest(CreateChoiceRequest input, Context context) {
//		// TODO Auto-generated method stub
//		return null;
//	}

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
//	public boolean createChoice(String description, int maxNumTeamMember,
//			ArrayList<AlternativeChoice> alternatives) throws Exception {
//		if (logger != null) {
//			logger.log("in createChoice");
//		}
//		String uniqueID = Integer.toString(description.hashCode()+alternatives.get(1).getDescription().hashCode());
//		ChoiceDAO choiceDao = new ChoiceDAO();
//		/**
//		 * public Choice(String uniqueID, ArrayList<AlternativeChoice>
//		 * alternativeChoices, ArrayList<TeamMember> participatingMembers, String
//		 * description, Date dateOfCompletion, Date dateOfCreation, boolean isCompleted,
//		 * int maxNumOfTeamMembers) { this.maxNumOfTeamMembers = maxNumOfTeamMembers;
//		 * this.uniqueID = uniqueID; this.setAlternativeChoices(alternativeChoices);
//		 * this.setParticipatingMembers(participatingMembers); this.description =
//		 * description; this.dateOfCompletion = dateOfCompletion; this.dateOfCreation =
//		 * dateOfCreation; this.isCompleted = isCompleted; }
//		 */
//		// check if present
//		Choice exist = choiceDao.getChoice(uniqueID);
//
//		// go through alternatives and put each in alternativeDAO
//		AlternativeChoiceDAO alternativeDAO = new AlternativeChoiceDAO();
//
//		for (AlternativeChoice alt : alternatives) {
//			alt.setChoiceID(uniqueID);
//			alt.setDescription(alt.getDescription());
//			alternativeDAO.addAlternative(alt);
//		}
//		Choice choice = new Choice(uniqueID, AlternativeChoiceDAO.getAllAlternatives(uniqueID),
//				new ArrayList<TeamMember>(maxNumTeamMember), description, null, null, // Change to an array instead of
//																						// an arrayList
//				new java.sql.Date(System.currentTimeMillis()), false);
//		if (exist == null) {
//			return choiceDao.addChoice(choice);
//		} else {
//			return false;
//		}
//	}
//	//Overloaded function
//	public boolean createChoice(Choice c) throws Exception {
//		return createChoice(c.getDescription(), c.getMaxNumOfTeamMembers(), c.getAlternativeChoices());
//
//	}

	boolean createTeamMember(LoginRequest req) throws Exception {
		if (logger != null) {
			logger.log("in createTeamMember");
		}

		TeamMemberDAO teamMemberDAO = new TeamMemberDAO();
		ChoiceDAO choiceDAO = new ChoiceDAO();

		List<TeamMember> list = TeamMemberDAO.getAllTeamMembers(req.getChoiceID());
		
		int maxNum = choiceDAO.getMaxNum(req.getChoiceID());
		if (list.size() >= maxNum) { return false;}
		
		TeamMember exist = new TeamMember(req.getChoiceID(), req.getName(), req.getPassword());
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

	/** Here primarily to clean up testing. */


	String createUniqueUsername(LoginRequest req) {
		String username = req.getUsername().hashCode() + req.getPassword();
		return username;
	}

	@Override
	public LoginResponse handleRequest(LoginRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

		LoginResponse response;
		try {
//			String uniqueUsername = createUniqueUsername(req);
//			if (createTeamMember(uniqueUsername, req)) {
//				response = new LoginResponse(uniqueUsername, 200);
//			} else {
//				response = new LoginResponse(uniqueUsername, 400);
//			}
//
//		} catch (Exception e) {
//			response = new LoginResponse(
//					"Unable to create Team Member: " + req.getUsername() + "(" + e.getMessage() + ")",
//					400);
//		}
			
			if (createTeamMember(req)) {
				response = new LoginResponse("Sucessful: " + req.getName(), 200);
			} else {
				response = new LoginResponse("Cannot create a teamMember" + req.getName(), 400);
			}
		}catch (Exception e) {
			response = new LoginResponse("Cant" + req.getName() + e.getMessage());
		}
				
				

		return response;
	}

}