package edu.wpi.cs.basic.demo;

import java.util.Collection;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.http.*;
import edu.wpi.cs.basic.demo.model.Choice;

public class ChoiceDatabaseHandler implements RequestHandler<CreateChoiceRequest, CreateChoiceResponse> {
	boolean pruneDatabaseFunction() {
		return false;
	}

	Choice[] getAllChoices() {
		return null;
	}

	public String getNextID() {

		return null;
	}

	public boolean pushChoice(Choice choice) {
		return false;
	}

	@Override
	public CreateChoiceResponse handleRequest(CreateChoiceRequest input, Context context) {
		// TODO Auto-generated method stub
		return null;
	}
}
