package edu.wpi.cs.basic.demo.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
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

	final String tblName = "TeamMember"; // Exact capitalization

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
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert teamMember: " + e.getMessage());
		} 
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
			throw new Exception("Failed in getting alternatives: " + e.getMessage());
		}
	}

	TeamMember generateTeamMember(ResultSet resultSet) throws Exception {
		String name = resultSet.getString("uniqueID");
		String password = resultSet.getString("password");
		String choiceID = resultSet.getString("choiceID");

		return new TeamMember(name, password, choiceID);

	}

}
