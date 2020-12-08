package edu.wpi.cs.basic.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.GetAllChoicesResponse;
import edu.wpi.cs.basic.demo.model.Choice;

/**
 * Eliminated need to work with JSON
 */
public class ListAllChoicesHandler implements RequestHandler<Object,GetAllChoicesResponse> {

	public LambdaLogger logger;

	// Note: this works, but it would be better to move this to environment/configuration mechanisms
	// which you don't have to do for this project.
	// public static final String REAL_BUCKET = "constants";

	/** Load from RDS, if it exists
	 * 
	 * @throws Exception 
	 */
	List<Choice> getChoices() throws Exception {
		logger.log("in getChoices");
		ChoiceDAO dao = new ChoiceDAO();
		
		return dao.getAllChoices();
	}
	
	// I am leaving in this S3 code so it can be a LAST RESORT if the constant is not in the database
	private AmazonS3 s3 = null;
	

	@Override
	public GetAllChoicesResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all choices");

		GetAllChoicesResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Choice> list = getChoices();
//			for (Choice c : systemConstants()) {
//				if (!list.contains(c)) {
//					list.add(c);
//				}
//			}
			response = new GetAllChoicesResponse(list, 200);
		} catch (Exception e) {
			response = new GetAllChoicesResponse(400, e.getMessage());
		}
		
		return response;
	}
}
