package edu.wpi.cs.basic.demo;

import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;


import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.*;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;

public class CreateChoiceHandler implements RequestHandler<CreateChoiceRequest, CreateChoiceResponse> {
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

	boolean createChoice(String uniqueID, CreateChoiceRequest req) throws Exception {
		if (logger != null) {
			logger.log("in createChoice");
		}
		ChoiceDAO choiceDAO = new ChoiceDAO();

		Choice exist = choiceDAO.getChoice(uniqueID);
		Choice choice = new Choice(uniqueID, req.getDescription(), req.getMaxNum());
		if (exist == null) {
			choiceDAO.addChoice(choice);
		} else {
			return false;
		}

		AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();
		List<AlternativeChoice> alternatives = req.getAlternativeChoices();
		for (AlternativeChoice alt : alternatives) {
			alt.setChoiceID(uniqueID);
//			alt.setDescription(alt.getDescription());
			altDAO.addAlternative(alt);
		}
		return true;
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
	void deleteSystemChoice(float daysOld) {
		// TODO: add logic for replacing deleted id numbers
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_2).build();
			logger.log("attach to S3 succeed");
		}
		String folder = BucketManager.getChoiceFolder() + "/";
		s3.deleteObject(new DeleteObjectRequest(BucketManager.bucket, folder + daysOld));
	}

	String createUniqueChoiceID(CreateChoiceRequest req) {
		String uniqueID = Integer.toString(Math.abs(
				req.getDescription().hashCode() + req.getAlternativeChoices().get(1).getDescription().hashCode()));
		return uniqueID;
	}

	@Override
	public CreateChoiceResponse handleRequest(CreateChoiceRequest req, Context context) {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateChoiceResponse response;
		try {
			String uniqueID = createUniqueChoiceID(req);
			if (createChoice(uniqueID, req)) {
				response = new CreateChoiceResponse(uniqueID, 200);
			} else {
				response = new CreateChoiceResponse(uniqueID, 400);
			}

		} catch (Exception e) {
			response = new CreateChoiceResponse(
					"Unable to create Choice: " + req.getDescription() + "(" + e.getMessage() + ")",
					400);
		}

		return response;
	}

}