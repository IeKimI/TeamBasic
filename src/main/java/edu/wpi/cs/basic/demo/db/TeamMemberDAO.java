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
create table `teamMember` ( 
`uniqueName` VARCHAR(64), 
`password` VARCHAR(64), 

‘choiceID’ VARCHAR(64) not null default, 
primary key (`uniqueName`) 

) engine=MyISAM default charset=latin1; 
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

//	public TeamMember getTeamMember(String name) throws Exception {
//
//		try {
//			TeamMember teamMember = null;
//			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE teamMemberName=?;");
//			ps.setString(1, teamMember.getName());
////			ps.setString(2, teamMember.getPassword());
////			ps.setString(3, teamMember.getChoiceID());
//
//			ResultSet resultSet = ps.executeQuery();
//
//			while (resultSet.next()) {
//				teamMember = generateTeamMember(resultSet);
//			}
//			resultSet.close();
//			ps.close();
//			return teamMember;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("Failed in getting team member: " + e.getMessage());
//		}
//	}
//
//	public boolean updateTeamMember(TeamMember teamMember) throws Exception {
//		try {
//			String query = "UPDATE " + tblName + " SET isCompleted=? WHERE uniqueID=?;";
//			PreparedStatement ps = conn.prepareStatement(query);
//			ps.setString(1, teamMember.getName());
//			ps.setString(2, teamMember.getPassword());
//			ps.setString(3, teamMember.getChoiceID());
//			int numAffected = ps.executeUpdate();
//			ps.close();
//
//			return (numAffected == 1);
//		} catch (Exception e) {
//			throw new Exception("Failed to update report: " + e.getMessage());
//		}
//	}

//	boolean d(Choice Choice) throws Exception {
//		try {
//			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE uniqueID = ?;");
//			ps.setString(1, Choice.uniqueID);
//			int numAffected = ps.executeUpdate();
//			ps.close();
//
//			return (numAffected == 1);
//
//		} catch (Exception e) {
//			throw new Exception("Failed to insert Choice: " + e.getMessage());
//		}
//	}

	public boolean addTeamMember(TeamMember teamMember) throws Exception {
		try {
//			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE teamMemberName = ?;");
//			ps.setString(1, teamMember.getName());
//
//
//			ResultSet resultSet = ps.executeQuery();
//
//			// already present?
//			while (resultSet.next()) {
//				TeamMember tm = generateTeamMember(resultSet);
//				resultSet.close();
//				return false;
//			}

			PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (uniqueID, password, choiceID) values(?,?,?);");
//			ps.setString(1, Choice.uniqueID);
//			ps.setString(2, Choice.getChosenAlternative().getAlternativeID());
//			ps.setInt(3, Choice.getParticipatingMembers().size());
//			ps.setString(4, Choice.getDescription());
//			ps.setDate(5, Choice.getDayOfCompletion());
//			ps.setFloat(6, Choice.getDaysOld());
//			ps.setBoolean(7, Choice.isComplete());
			ps.setString(1, teamMember.getName());
			ps.setString(2, teamMember.getPassword());
			ps.setString(3, teamMember.getChoiceID());
			ps.execute();
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert teamMember: " + e.getMessage());
		}
	}

	public List<TeamMember> getAllTeamMembers(String uniqueID) throws Exception {

		List<TeamMember> allTM = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM " + tblName + " WHERE choiceID=?;";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				TeamMember tm = generateTeamMember(resultSet);
				allTM.add(tm);
			}
			resultSet.close();
			statement.close();
			return allTM;

		} catch (Exception e) {
			throw new Exception("Failed in getting teamMembers: " + e.getMessage());
		}
	}

	 TeamMember generateTeamMember(ResultSet resultSet) throws Exception {
		String name = resultSet.getString("uniqueID");
		String password = resultSet.getString("password");
		String choiceID = resultSet.getString("choiceID");
		
		return new TeamMember(name, password, choiceID);

	}


}
