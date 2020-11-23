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
import edu.wpi.cs.basic.demo.http.AllChoicesResponse;
import edu.wpi.cs.basic.demo.model.Choice;

/**
 * Eliminated need to work with JSON
 */
public class ListAllChoicesHandler implements RequestHandler<Object,AllChoicesResponse> {

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
	
	/**
	 * Retrieve all SYSTEM constants. This code is surprisingly dangerous since there could
	 * be an incredible number of objects in the bucket. Ignoring this for now.
	 */
//	List<Choice> systemConstants() throws Exception {
//		logger.log("in systemConstants");
//		if (s3 == null) {
//			logger.log("attach to S3 request");
//			s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
//			logger.log("attach to S3 succeed");
//		}
//		ArrayList<Choice> sysConstants = new ArrayList<>();
//	    
//		// retrieve listing of all objects in the designated bucket
//		ListObjectsV2Request listObjectsRequest = new ListObjectsV2Request()
//				  .withBucketName(BucketManager.bucket)                          // top-level bucket
//				  .withPrefix(BucketManager.getChoiceFolder());            	 // sub-folder declarations here (i.e., a/b/c)
//		
//		// request the s3 objects in the s3 bucket 'calculator-example/constants' -- change based on your bucket name
//		logger.log("process request");
//		ListObjectsV2Result result = s3.listObjectsV2(listObjectsRequest);
//		logger.log("process request succeeded");
//		List<S3ObjectSummary> objects = result.getObjectSummaries();
//		
//		for (S3ObjectSummary os: objects) {
//	      String name = os.getKey();
//		  logger.log("S3 found:" + name);
//
//	      // If name ends with slash it is the 'constants/' bucket itself so you skip
//	      if (name.endsWith("/")) { continue; }
//			
//	      S3Object obj = s3.getObject(BucketManager.bucket, name);
//	    	
//	    	try (S3ObjectInputStream constantStream = obj.getObjectContent()) {
//				Scanner sc = new Scanner(constantStream);
//				String val = sc.nextLine();
//				sc.close();
//				
//				// just grab name *after* the slash. Note this is a SYSTEM constant
//				int postSlash = name.indexOf('/');
//				sysConstants.add(new Choice(name.substring(postSlash+1), Double.valueOf(val), true));
//			} catch (Exception e) {
//				logger.log("Unable to parse contents of " + name);
//			}
//	    }
//		
//		return sysConstants;
//	}
	
	@Override
	public AllChoicesResponse handleRequest(Object input, Context context)  {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to list all choices");

		AllChoicesResponse response;
		try {
			// get all user defined constants AND system-defined constants.
			// Note that user defined constants override system-defined constants.
			List<Choice> list = getChoices();
//			for (Choice c : systemConstants()) {
//				if (!list.contains(c)) {
//					list.add(c);
//				}
//			}
			response = new AllChoicesResponse(list, 200);
		} catch (Exception e) {
			response = new AllChoicesResponse(400, e.getMessage());
		}
		
		return response;
	}
}
