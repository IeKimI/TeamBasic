package edu.wpi.cs.basic.demo;

import java.io.ByteArrayInputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

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


public class CreateChoiceHandler implements RequestHandler<CreateChoiceRequest, CreateChoiceResponse> {
//	boolean pruneDatabaseFunction() {
//		return false;
//	}
//
//	Choice[] getAllChoices() {
//		return null;
//	}
//
//	public String getNextID() {
//
//		return null;
//	}
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
		
	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	// public static final String REAL_BUCKET = "constants/";
	
	/** Store into RDS.
	 * 
	 * @throws Exception 
	 */
	boolean createChoice(String uniqueID, String description, int maxNumTeamMember) throws Exception { 
		if (logger != null) { logger.log("in createConstant"); }
		ChoiceDAO dao = new ChoiceDAO();
		
		/**
		 * 	public Choice(String uniqueID, ArrayList<AlternativeChoice> alternativeChoices,
			ArrayList<TeamMember> participatingMembers, String description, Date dateOfCompletion, Date dateOfCreation,
			boolean isCompleted, int maxNumOfTeamMembers) {
		this.maxNumOfTeamMembers = maxNumOfTeamMembers;
		this.uniqueID = uniqueID;
		this.setAlternativeChoices(alternativeChoices);
		this.setParticipatingMembers(participatingMembers);
		this.description = description;
		this.dateOfCompletion = dateOfCompletion;
		this.dateOfCreation = dateOfCreation;
		this.isCompleted = isCompleted;
	}
		 */
		// check if present
		Choice exist = dao.getChoice(uniqueID);
		Choice choice = new Choice (uniqueID, AlternativeChoiceDAO.getAllAlternatives(uniqueID), TeamMemberDAO.getAllTeamMembers(uniqueID), description, null, new java.sql.Date(System.currentTimeMillis()), false, maxNumTeamMember);
		if (exist == null) {
			return dao.addChoice(choice);
		} else {
			return false;
		}
	}
	
	/** Create S3 bucket
	 * 
	 * @throws Exception 
	 */
	boolean createSystemChioce(String name, double value) throws Exception {
		if (logger != null) { logger.log("in createSystemConstant"); }
		
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}

		String folder = BucketManager.getConstantsFolder() + "/";
		
		byte[] contents = ("" + value).getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(contents);
		ObjectMetadata omd = new ObjectMetadata();
		omd.setContentLength(contents.length);
		
		// makes the object publicly visible
		PutObjectResult res = s3.putObject(new PutObjectRequest(BucketManager.bucket, folder + name, bais, omd)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		
		// if we ever get here, then whole thing was stored
		return true;
	}
	
	/** Here primarily to clean up testing. */
	void deleteSystemConstant(String name) {
		if (s3 == null) {
			logger.log("attach to S3 request");
			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
			logger.log("attach to S3 succeed");
		}
		String folder = BucketManager.getConstantsFolder() + "/";
		s3.deleteObject(new DeleteObjectRequest(BucketManager.bucket, folder + name));
	}
	
	@Override 
	public CreateConstantResponse handleRequest(CreateConstantRequest req, Context context)  {
		logger = context.getLogger();
		logger.log(req.toString());

		CreateConstantResponse response;
		try {
			if (req.system) {
				if (createSystemConstant(req.name, req.value)) {
					response = new CreateConstantResponse(req.name);
				} else {
					response = new CreateConstantResponse(req.name, 422);
				}
			} else {
				if (createConstant(req.name, req.value)) {
					response = new CreateConstantResponse(req.name);
				} else {
					response = new CreateConstantResponse(req.name, 422);
				}
			}
		} catch (Exception e) {
			response = new CreateConstantResponse("Unable to create constant: " + req.name + "(" + e.getMessage() + ")", 400);
		}

		return response;
	}
	
}
