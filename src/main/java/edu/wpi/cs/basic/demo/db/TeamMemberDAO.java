package edu.wpi.cs.basic.demo.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Approval;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.TeamMember;

/**
 * create table `teamMember` ( `uniqueName` VARCHAR(64), `password` VARCHAR(64),
 * 
 * ‘choiceID’ VARCHAR(64) not null default, primary key (`uniqueName`)
 * 
 * ) engine=MyISAM default charset=latin1;
 *
 */
public class TeamMemberDAO {
	static java.sql.Connection conn;

	final static String tblName = "TeamMember"; // Exact capitalization

	public TeamMemberDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}
	


	public boolean addTeamMember(TeamMember teamMember) throws Exception {
		try {

			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO " + tblName + " (uniqueID, password, choiceID) values(?,?,?);");

			ps.setString(1, teamMember.getName());
			ps.setString(2, teamMember.getPassword());
			ps.setString(3, teamMember.getChoiceID());

			ps.execute();

			List<TeamMember> listOfMembersFromChoice = getAllTeamMembers(teamMember.getChoiceID());

			return addTeamMemberToApprovalsDatabase(teamMember.getChoiceID(), getTeamMemberID(teamMember.getName()));

		} catch (Exception e) {
			throw new Exception("Failed to insert teamMember: " + e.getMessage());
		}
	}

	boolean addTeamMemberToApprovalsDatabase(String choiceID, int teamMemberID) throws Exception {
		AlternativeChoiceDAO altDatabase = new AlternativeChoiceDAO();
		List<AlternativeChoice> listOfAlternatives = altDatabase.getAllAlternatives(choiceID);

		ApprovalDAO approvalDatabase = new ApprovalDAO();
		for (AlternativeChoice altChoice : listOfAlternatives) {
			approvalDatabase.initializeApproval(new Approval(altChoice.getAlternativeID(), teamMemberID));
		}
		return true;
	}

	public List<TeamMember> getAllTeamMembers(String choiceID) throws Exception {

		List<TeamMember> participants = new ArrayList<TeamMember>();
		try {
			Statement statement = conn.createStatement();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choiceID=?;");
			ps.setString(1, choiceID);
			// String query = "SELECT * FROM " + tblName + "GROUP BY alternative_id HAVING
			// MAX(choice_id) = " + choiceID + " AND MIN(choice_id) = "+choiceID + ";";
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				TeamMember p = generateTeamMember(resultSet);
				participants.add(p);
			}
			resultSet.close();
			statement.close();
			return participants;

		} catch (Exception e) {
			throw new Exception("Failed in getting teamMembers: " + e.getMessage());
		}
	}

	public int getTeamMemberID(String teamMemberName) throws Exception {
		try {
			TeamMember teamMember = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID=?;");
			ps.setString(1, teamMemberName);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				teamMember = generateTeamMember(resultSet);
			}
			resultSet.close();
			ps.close();

			return teamMember.getTeamMemberID();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting participant: " + e.getMessage());
		}
	}

	public String getTeamMemberByID(int teamMemberID) throws Exception {
		try {
			TeamMember teamMember = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE teamMemberID=?;");
			ps.setInt(1, teamMemberID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				teamMember = generateTeamMember(resultSet);
			}
			resultSet.close();
			ps.close();

			return teamMember.getName();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting participant: " + e.getMessage());
		}
	}

	TeamMember generateTeamMember(ResultSet resultSet) throws Exception {
		String name = resultSet.getString("uniqueID");
		String password = resultSet.getString("password");
		String choiceID = resultSet.getString("choiceID");
		int teamMemberID = resultSet.getInt("teamMemberID");

		return new TeamMember(name, password, choiceID, teamMemberID);

	}
	
	public boolean deleteTeamMember(LambdaLogger logger, String uniqueID) {
		try {
			logger.log("In delteTeamMember");
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE choiceID = ?;");
			ps.setString(1, uniqueID);
			logger.log("The statement is done being constructed.");
			ps.executeUpdate();
			ps.close();
			logger.log("Exiting deleteTeamMember");
			return true;

		} catch (Exception e) {
			logger.log("Failed to delete the teamMember: " + e.getMessage());
			return false;
		}
	}

}
