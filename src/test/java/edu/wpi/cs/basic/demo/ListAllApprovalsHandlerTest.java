package edu.wpi.cs.basic.demo;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.basic.demo.db.AlternativeChoiceDAO;
import edu.wpi.cs.basic.demo.db.ChoiceDAO;
import edu.wpi.cs.basic.demo.db.TeamMemberDAO;
import edu.wpi.cs.basic.demo.http.CreateChoiceRequest;
import edu.wpi.cs.basic.demo.http.CreateChoiceResponse;
import edu.wpi.cs.basic.demo.http.CreateTeamMemberRequest;
import edu.wpi.cs.basic.demo.http.FlipApprovalRequest;
import edu.wpi.cs.basic.demo.http.FlipApprovalResponse;
import edu.wpi.cs.basic.demo.http.GetAllApprovalsResponse;
import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class ListAllApprovalsHandlerTest extends LambdaTest {
	@Test 
	public void testGetList() {
		
		ArrayList<AlternativeChoice> alternatives = new ArrayList<AlternativeChoice>();
		AlternativeChoice alt1 = new AlternativeChoice("alt1_description");
		AlternativeChoice alt2 = new AlternativeChoice("alt2_description");
		AlternativeChoice alt3 = new AlternativeChoice("alt3_description");
		alternatives.add(alt1);
		alternatives.add(alt2);
		alternatives.add(alt3);
		String uniqueID = null; 

		
		ChoiceDAO choiceDAO = new ChoiceDAO();
		AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();
		TeamMemberDAO teamMemberDAO = new TeamMemberDAO();
		CreateChoiceRequest ccr = new CreateChoiceRequest("ListAllApprovalTest", 10, alternatives);
		CreateChoiceHandler cch = new CreateChoiceHandler();

		CreateChoiceResponse c_resp = cch.handleRequest(ccr, createContext("create"));
		uniqueID = c_resp.response;
		
		TeamMember tm1 = new TeamMember("TeamMember1", "", uniqueID);
//		TeamMember tm1 = new TeamMember(uniqueID, "name", "password");
//		TeamMember tm1 = new TeamMember(uniqueID, "name", "password");
//		TeamMember tm1 = new TeamMember(uniqueID, "name", "password");
//		String sampleInput = "{\n" + 
//				"    \"uniqueID\" : \"hello\",\n" + 
//				"    \"password\" : \"ERERERERE\",\n" + 
//				"    \"choiceID\" : " + uniqueID + 
//				"}";

		CreateTeamMemberRequest req1 = new CreateTeamMemberRequest(tm1.getName(), tm1.getPassword(), tm1.getChoiceID());
//		CreateTeamMemberRequest req1 = new Gson().fromJson(sampleInput, CreateTeamMemberRequest.class);
		
		LoginHandler login = new LoginHandler();
		Assert.assertEquals(200, login.handleRequest(req1, createContext("register1")).httpCode);
		
		
		FlipApprovalHandler handler = new FlipApprovalHandler();

		int altID = 0;
		try {
			altID = altDAO.getAltIDByChoiceIDAndDesc(uniqueID, "alt1_description");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int teamMemberID = 0;
		try {
			teamMemberID = teamMemberDAO.getTeamMemberID(tm1.getName(), uniqueID);
			System.out.println(teamMemberID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FlipApprovalRequest far = new FlipApprovalRequest(altID, teamMemberID , true);

		FlipApprovalResponse f_resp = handler.handleRequest(far, createContext("flip"));
		
//		System.out.println(f_resp.toString());

		Assert.assertEquals(f_resp.httpCode, 200);
		GetApprovalsHandler getAppovalsHandler = new GetApprovalsHandler();

		GetAllApprovalsResponse resp = getAppovalsHandler.handleRequest(c_resp.response, createContext("list"));

		Assert.assertEquals(200, resp.statusCode);
		
		try {
			choiceDAO.deleteChoice(c_resp.response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
