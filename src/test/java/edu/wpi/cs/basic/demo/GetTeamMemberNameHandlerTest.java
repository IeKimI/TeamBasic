package edu.wpi.cs.basic.demo;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.TeamMemberDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.http.CreateTeamMemberRequest;
import edu.wpi.cs.basic.demo.http.GetAllApprovalsResponse;
import edu.wpi.cs.basic.demo.http.GetTeamMemberNameByIDHandlerResponse;
import edu.wpi.cs.basic.demo.http.GetTeamMemberNameByIDRequest;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class GetTeamMemberNameHandlerTest extends LambdaTest {
	@Test
	public void testGetTeamMemberName() {
		LoginHandler handler = new LoginHandler();
		ChoiceDAO choiceDAO = new ChoiceDAO();

		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();

		AlternativeChoice alt1 = new AlternativeChoice("12");
		AlternativeChoice alt2 = new AlternativeChoice("123");
		AlternativeChoice alt3 = new AlternativeChoice("1234");

		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		String uniqueID = null;

		CreateChoiceHandler choiceHandler = new CreateChoiceHandler();
		CreateChoiceRequest ccr = new CreateChoiceRequest("GetTeamMemberNameTest", 3, alternatives);

		CreateChoiceResponse c_resp = choiceHandler.handleRequest(ccr, createContext("create"));
		uniqueID = c_resp.response;

		TeamMember tm1 = new TeamMember("TeamMember1", "", uniqueID);

		CreateTeamMemberRequest req1 = new CreateTeamMemberRequest(tm1.getName(), tm1.getPassword(), tm1.getChoiceID());

		if (uniqueID == null) {
			Assert.fail("FAILED");
		}

		Assert.assertEquals(200, handler.handleRequest(req1, createContext("register1")).httpCode);

		GetTeamMemberNameByIDHandler getNameHandler = new GetTeamMemberNameByIDHandler();

		TeamMemberDAO teamMemberDAO = new TeamMemberDAO();
		try {
			int teamMemberID = teamMemberDAO.getTeamMemberID(tm1.getName());
			GetTeamMemberNameByIDRequest req = new GetTeamMemberNameByIDRequest(uniqueID,
					Integer.toString(teamMemberID));
			GetTeamMemberNameByIDHandlerResponse resp = getNameHandler.handleRequest(req, createContext("list"));

			Assert.assertEquals(200, resp.statusCode);
			boolean result = choiceDAO.deleteChoice(c_resp.response);
			Assert.assertTrue(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
