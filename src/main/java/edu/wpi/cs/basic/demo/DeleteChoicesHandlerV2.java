package edu.wpi.cs.basic.demo;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.http.DeleteChoicesRequest;
import edu.wpi.cs.basic.demo.http.DeleteChoicesResponse;
import edu.wpi.cs.basic.demo.model.Choice;

public class DeleteChoicesHandlerV2 implements RequestHandler<DeleteChoicesRequest,DeleteChoicesResponse> {
	public LambdaLogger logger = null;

	@Override
	public DeleteChoicesResponse handleRequest(DeleteChoicesRequest req, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java Lambda handler to delete");

		DeleteChoicesResponse response = null;
		logger.log(req.toString());

		ChoiceDAO dao = new ChoiceDAO();

		// MAKE sure that we prevent attempts to delete system constants...
		
		// See how awkward it is to call delete with an object, when you only
		// have one part of its information?
		try {
			if (dao.deleteNDaysOld(req.nDaysOld)) {
				response = new DeleteChoicesResponse(req.nDaysOld, 200);
			} else {
				response = new DeleteChoicesResponse(req.nDaysOld, 422, "Unable to delete constant.");
			}
		} catch (Exception e) {
			response = new DeleteChoicesResponse(req.nDaysOld, 403, "Unable to delete constant: " + req.nDaysOld + "(" + e.getMessage() + ")");
		}

		return response;
	}
}
