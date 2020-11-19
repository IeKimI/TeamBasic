package edu.wpi.cs.basic.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.TeamMember;

/**
 * `uniqueID` VARCHAR(64) not null default, 
`description` VARCHAR(64) not null default, 

‘maxNumOfteamMembers’ INT, 

‘chosenAlternativeID’ VARCHAR(64), 

‘isCompleted’ BOOLEAN, 

‘dateOfCompletion’ DATE, 

'dateOfCreation’ DATE, 

 */

public class ChoiceDatabase {

	java.sql.Connection conn;

	final String tblName = "Choice"; // Exact capitalization

	public ChoiceDatabase() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public Choice getChoice(String uniqueID) throws Exception {

		try {
			Choice choice = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID=?;");
			ps.setString(1, uniqueID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				choice = generateConstant(resultSet);
			}
			resultSet.close();
			ps.close();

			return choice;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting choice: " + e.getMessage());
		}
	}

	public boolean deleteConstant(Choice choice) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, choice.uniqueID);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to insert constant: " + e.getMessage());
		}
	}

	public boolean addChoice(Choice choice) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, choice.uniqueID);
			ResultSet resultSet = ps.executeQuery();

			// already present?
			while (resultSet.next()) {
				Choice c = generateConstant(resultSet);
				resultSet.close();
				return false;
			}

			// INSERT INTO table_name (column1, column2, column3, ...)
			// VALUES (value1, value2, value3, ...);
			ps = conn.prepareStatement("INSERT INTO " + tblName
					+ " (uniqueID, alternativeChoices, participatingMembers, description, dayOfCompletion, daysOld, isCompleted) values(?,?,?,?,?,?,?);");
			ps.setString(1, choice.uniqueID);
//			ps.setDouble(2, choice.value);
			ps.setArray(2, (Array) choice.getAlternativeChoices());
			ps.setArray(3, (Array) choice.getParticipatingMembers()); 
			ps.setString(4, choice.getDescription());
			ps.setDate(5, choice.getDayOfCompletion());
			ps.setFloat(6, choice.getDaysOld());
			ps.setBoolean(7, choice.isComplete());
			ps.execute();
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert constant: " + e.getMessage());
		}
	}

	public List<Choice> getAllChoices() throws Exception {

		List<Choice> allChoices = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM " + tblName + ";";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Choice c = generateConstant(resultSet);
				allChoices.add(c);
			}
			resultSet.close();
			statement.close();
			return allChoices;

		} catch (Exception e) {
			throw new Exception("Failed in getting constants: " + e.getMessage());
		}
	}

	private Choice generateConstant(ResultSet resultSet) throws Exception {
		String uniqueID = resultSet.getString("uniqueID");
		ArrayList<AlternativeChoice> alternativeChoices = (ArrayList<AlternativeChoice>) resultSet
				.getArray("alternativeChoices");
		ArrayList<TeamMember> participatingMembers = (ArrayList<TeamMember>) resultSet.getArray("participatingMembers");
		String description = resultSet.getString("description");
		Date dayOfCompletion = resultSet.getDate("dayOfCompletion");
		float daysOld = resultSet.getFloat("daysOld");
		boolean isCompleted = resultSet.getBoolean("isCompleted");
		return new Choice(uniqueID, alternativeChoices, participatingMembers, description, dayOfCompletion, daysOld,
				isCompleted);
	}

}
