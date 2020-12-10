package edu.wpi.cs.basic.demo;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.FeedbackDAO;
import edu.wpi.cs.basic.demo.db.TeamMemberDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateFeedbackRequest;
import edu.wpi.cs.basic.demo.http.CreateTeamMemberRequest;
import edu.wpi.cs.basic.demo.http.GetFeedbackResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Feedback;

public class CreateFeedbackTest extends LambdaTest {

	@Test
	public void testCreateAndGetFeedback() throws Exception {
		String choiceID = (new CreateChoiceHandler())
				.handleRequest(
						new CreateChoiceRequest("TestingCreateFeedback", 5, Arrays.asList((new AlternativeChoice("1")),
								(new AlternativeChoice("2")), (new AlternativeChoice("3")))),
						createContext("create")).response;
		(new LoginHandler()).handleRequest(new CreateTeamMemberRequest("username", "password", choiceID),
				createContext("create"));
		(new CreateFeedbackHandler())
				.handleRequest(
						new CreateFeedbackRequest("TestingCreateFeedbackHandler",
								(new TeamMemberDAO()).getTeamMemberID("username", choiceID),
								(new GetAlternativeChoiceHandler()).handleRequest(choiceID,
										createContext("get")).alternatives.get(0).getAlternativeID()),
						createContext("create"));
		Feedback feedback = (new GetFeedbackHandler()).handleRequest(choiceID, createContext("get")).getFeedback()
				.get(0);
		Assert.assertTrue(feedback.getText().equals("TestingCreateFeedbackHandler")
				&& feedback.getTeamMemberName().equals("username"));
	}

}
