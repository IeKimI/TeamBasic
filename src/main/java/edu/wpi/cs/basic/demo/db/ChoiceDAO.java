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
 * create table `choice` ( `uniqueID` VARCHAR(64) not null default,
 * `description` VARCHAR(64) not null default,
 * 
 * ‘maxNumOfteamMembers’ INT,
 * 
 * ‘chosenAlternativeID’ VARCHAR(64),
 * 
 * ‘isCompleted’ BOOLEAN,
 * 
 * ‘dateOfCompletion’ DATE,
 * 
 * 'dateOfCreation’ DATE, primary key (`uniqueID`)
 * 
 * ) engine=MyISAM default charset=latin1;
 * 
 * @author teamBasic
 *
 */
public class ChoiceDAO {

	java.sql.Connection conn;

	final String tblName = "Choice"; // Exact capitalization

	public ChoiceDAO() {
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

	public boolean addChoice(Choice choice) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, choice.getUniqueID());
			ResultSet resultSet = ps.executeQuery();

			// already present?
			while (resultSet.next()) {
				Choice c = generateChoice(resultSet);
				resultSet.close();
				return false;
			}

			ps = conn.prepareStatement("INSERT INTO " + tblName
					+ " (uniqueID, description, maxNumOfTeamMembers, chosenAlternativeID, isCompleted, dateOfCompletion, dateOfCreation) values(?,?,?,?,?,?,?);");
			ps.setString(1, choice.getUniqueID());
			ps.setString(2, choice.getDescription());
			ps.setInt(3, choice.getParticipatingMembers().size());
//			ps.setString(4, choice.getChosenAlternative().getAlternativeID());
			ps.setString(4, null);
			ps.setBoolean(5, choice.isComplete());
			ps.setDate(6, choice.getDateOfCompletion());
			ps.setDate(7, choice.getDateOfCreation());

			ps.execute();
			System.out.println(choice.getDescription());

			return true;
		} catch (Exception e) {
			throw new Exception("Failed to insert Choice: " + e.getMessage());
		}
	}

	public List<Choice> getAllChoices() throws Exception {

		List<Choice> allChoices = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM " + tblName + ";";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Choice c = generateChoice(resultSet);
				allChoices.add(c);
			}
			resultSet.close();
			statement.close();
			return allChoices;

		} catch (Exception e) {
			throw new Exception("Failed in getting Choices: " + e.getMessage());
		}
	}

	Choice generateChoice(ResultSet resultSet) throws Exception {
		String uniqueID = resultSet.getString("uniqueID");
//		String alternativeID = resultSet.getString("alternativeChoice");
//		//Do we need an alternativeID in the choice?
//		//No, the table that stores choice will just have that index. Then when you get the choice, you take the choice ID
//		//check the alternative table for the alternativeChocies with corresponding forgein key (the choice ID)

		// pass in the choiceID to get the list of alternatives that belongs to the
		// specific choice
//		ArrayList<AlternativeChoice> alternativeChoices = AlternativeChoiceDAO.getAllAlternatives(uniqueID);

		// pass in the choiceID to get the list of participating members that belongs to
		// the specific choice
//		ArrayList<TeamMember> participatingMembers = TeamMemberDAO.getAllTeamMembers(uniqueID);
		String description = resultSet.getString("description");
		int maxNum = resultSet.getInt("maxNumOfTeamMembers");
		Date dayOfCompletion = resultSet.getDate("dateOfCompletion");
		Date dayOfCreation = resultSet.getDate("dateOfCreation");
		String chosenAlternativeID = resultSet.getString("chosenAlternativeID");
		AlternativeChoice chosenAlternative = null;
//		float daysOld = resultSet.getFloat("daysOld");
		boolean isCompleted = resultSet.getBoolean("isCompleted");

		Choice output = new Choice(uniqueID, maxNum, description, chosenAlternativeID, isCompleted, dayOfCompletion,
				dayOfCreation);

		// set the chosenAlternative to null when generating the choice
		output.setChosenAlternative(null);
//		AlternativeChoice databaseInquery= 
		return output;
		/**
		 * public Choice(String uniqueID, ArrayList<AlternativeChoice>
		 * alternativeChoices, ArrayList<TeamMember> participatingMembers, String
		 * description, Date dateOfCompletion, Date dateOfCreation, boolean isCompleted)
		 * { this.uniqueID = uniqueID; this.alternativeChoices = alternativeChoices;
		 * this.participatingMembers = participatingMembers; this.description =
		 * description; this.dateOfCompletion = dateOfCompletion; this.dateOfCreation =
		 * dateOfCreation; this.isCompleted = isCompleted; }
		 */
	}
}
