package edu.wpi.cs.basic.demo.db;

import java.util.Collection;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.wpi.cs.basic.demo.model.Choice;

public class ChoiceDatabaseHandler implements RequestHandler<>{
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
}
