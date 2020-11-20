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

public class TeamMemberDAO {
	static java.sql.Connection conn;

	final String tblName = "Choices"; // Exact capitalization

	public TeamMemberDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public TeamMember getTeamMember(String uniqueID) throws Exception {

		try {
			Choice choice = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID=?;");
			ps.setString(1, uniqueID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				choice = generateChoice(resultSet);
			}
			resultSet.close();
			ps.close();

			return choice;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting choice: " + e.getMessage());
		}
	}

	public boolean updateChoice(Choice choice) throws Exception {
		try {
			String query = "UPDATE " + tblName + " SET isCompleted=? WHERE uniqueID=?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, choice.uniqueID);
//            ps.setString(2, Choice.getChosenAlternative().getAlternativeID());
			ps.setBoolean(7, choice.completeChoice(choice));
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);
		} catch (Exception e) {
			throw new Exception("Failed to update report: " + e.getMessage());
		}
	}

	boolean deleteChoice(Choice Choice) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, Choice.uniqueID);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to insert Choice: " + e.getMessage());
		}
	}

	public boolean addChoice(Choice Choice) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, Choice.uniqueID);
			ResultSet resultSet = ps.executeQuery();

			// already present?
			while (resultSet.next()) {
				Choice c = generateChoice(resultSet);
				resultSet.close();
				return false;
			}

			ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,value) values(?,?);");
			ps.setString(1, Choice.uniqueID);
			ps.setString(2, Choice.getChosenAlternative().getAlternativeID());
			ps.setInt(3, Choice.getParticipatingMembers().size());
			ps.setString(4, Choice.getDescription());
			ps.setDate(5, Choice.getDayOfCompletion());
			ps.setFloat(6, Choice.getDaysOld());
			ps.setBoolean(7, Choice.isComplete());
			ps.execute();
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert Choice: " + e.getMessage());
		}
	}

	public static ArrayList<TeamMember> getAllTeamMembers(String uniqueID) throws Exception {

		ArrayList<TeamMember> allChoices = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM " + tblName + ";";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				TeamMember c = generateTeamMember(resultSet);
				allChoices.add(c);
			}
			resultSet.close();
			statement.close();
			return allChoices;

		} catch (Exception e) {
			throw new Exception("Failed in getting Choices: " + e.getMessage());
		}
	}

	TeamMember generateTeamMember(ResultSet resultSet) throws Exception {
		String uniqueID = resultSet.getString("name");
		String alternativeID = resultSet.getString("alternativeChoice");
		ArrayList<AlternativeChoice> alternativeChoices = AlternativeChoiceDatabase.getAllAlternatives(uniqueID);
		ArrayList<TeamMember> participatingMembers;
		String description = resultSet.getString("description");
		Date dayOfCompletion = resultSet.getDate("dayOfCompletion");
		float daysOld = resultSet.getFloat("daysOld");
		boolean isCompleted = resultSet.getBoolean("isCompleted");
//		AlternativeChoice databaseInquery= 
		return new Choice(uniqueID, alternativeChoices, participatingMembers, description, dayOfCompletion, daysOld,
				isCompleted);
	}

}
