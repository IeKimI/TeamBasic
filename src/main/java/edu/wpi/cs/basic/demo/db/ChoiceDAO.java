package edu.wpi.cs.basic.demo.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.Days;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

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

	final static String tblName = "Choice"; // Exact capitalization

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

	public int getMaxNum(String uniqueID) throws Exception {
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

			if (choice == null) {
				throw new Exception("No choice with : " + uniqueID);
			}
			return choice.getMaxNumOfTeamMembers();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Cannot get the maxNum for: " + uniqueID + e.getMessage());
		}
	}

	public boolean updateChoice(Choice choice, int chosenAltID) throws Exception {
		try {
			/**
			 * UPDATE `sys`.`Choice` SET `chosenAlternativeID` = '1226', `isCompleted` = '1' WHERE (`uniqueID` = '1749316646');
			 */
			String query = "UPDATE " + tblName + " SET chosenAlternativeID=?, isCompleted=? WHERE uniqueID=?;";
			PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, chosenAltID);
			ps.setBoolean(2, true);
			ps.setString(3, choice.getUniqueID());
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);
		} catch (Exception e) {
			throw new Exception("Failed to update the choice: " + e.getMessage());
		}
	}

	public boolean isCompleted(String choiceID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, choiceID);
			ResultSet resultSet = ps.executeQuery();

			// already present?
			while (resultSet.next()) {
				Choice c = generateChoice(resultSet);
				if (c.isComplete()) {
					return true;
				}
				resultSet.close();
				return false;
			}

		} catch (Exception e) {
			throw new Exception("Failed to find a completed Choice: " + e.getMessage());
		}
		return false;
	}
	
	public int altID (String choiceID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, choiceID);
			ResultSet resultSet = ps.executeQuery();

			// already present?
			while (resultSet.next()) {
				Choice c = generateChoice(resultSet);
				if (c.isComplete()) {
					return Integer.valueOf(c.getChosenAlternative());
				}
				resultSet.close();
				throw new Exception("Choice isn't complete ");
			}

		} catch (Exception e) {
			throw new Exception("Failed to get the chosen altID: " + e.getMessage());
		}
		throw new Exception("Failed to get the chosen altID: ");
	}
	public boolean deleteChoice(String choiceID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, choiceID);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to delete Choice: " + e.getMessage());
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
			ps.setInt(3, choice.getMaxNumOfTeamMembers());
//			ps.setString(4, choice.getChosenAlternative().getAlternativeID());
			ps.setString(4, null);
			ps.setBoolean(5, choice.isComplete());
			ps.setDate(6, choice.getDateOfCompletion());
			ps.setTimestamp(7, choice.getDateOfCreation());

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
		Timestamp dayOfCreation = resultSet.getTimestamp("dateOfCreation");
		String chosenAlternativeID = resultSet.getString("chosenAlternativeID");
//		AlternativeChoice chosenAlternative = null;
//		float daysOld = resultSet.getFloat("daysOld");
		boolean isCompleted = resultSet.getBoolean("isCompleted");
		Choice output = new Choice(uniqueID, maxNum, description, chosenAlternativeID, isCompleted, dayOfCompletion,
				dayOfCreation);
		return output;

		// set the chosenAlternative to null when generating the choice
//		output.setChosenAlternative(null);
//		AlternativeChoice databaseInquery= 
//		return output;
	}

	public List<Choice> deleteChoicesNDaysOld(LambdaLogger logger, float n) {
		try {
			List<Choice> resultLog = getAllChoicesNDaysOld(logger, n);
			long currentMilisecondsFrom = System.currentTimeMillis();
			long milisecondsPassed = (long) (n * 86400000); // converting n days into n miliseconds
			long dateCutOff = currentMilisecondsFrom - milisecondsPassed;
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE dateOfCreation<=?;");
			ps.setString(1, (new Timestamp(dateCutOff).toString()));
			ps.executeUpdate();
			ps.close();
			return resultLog;
		} catch (Exception e) {
			return new ArrayList<Choice>();
		}

	}

	public List<Choice> getAllChoicesNDaysOld(LambdaLogger logger, float n) {
		try {
			logger.log("getChoicesNDaysOld has been called");
			List<Choice> resultLog = new ArrayList<Choice>();
			long milisecondsPassed = (long) (n * 86400000); // converting n days into n miliseconds
			logger.log("Got all choices and converted to miliseconds");
			long currentMilisecondsFrom = System.currentTimeMillis();
			long dateCutOff = currentMilisecondsFrom - milisecondsPassed;
			logger.log("Current Miliseconds is: " + currentMilisecondsFrom + "\nMiliseconds Passed is :"
					+ milisecondsPassed);
			logger.log("PreparedStatement construction");
			PreparedStatement getAllChoices = conn
					.prepareStatement("SELECT * FROM " + tblName + " WHERE dateOfCreation<=?;");
			getAllChoices.setTimestamp(1, (new Timestamp(dateCutOff)));
			ResultSet resultSet = getAllChoices.executeQuery();
			while (resultSet.next()) {
				resultLog.add(generateChoice(resultSet));
			}
			return resultLog;
		} catch (Exception e) {
			logger.log(e.toString());
		}
		return new ArrayList<Choice>();
	}
	
	
//	public void setChoiceToComplete(String choiceID, String chosenAltID) {
//		try {
//			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID=?;");
//			
//			ResultSet resultSet = ps.executeQuery();
//		} catch (Exception e) {
//			throw new Exception("Failed to complete report: " + e.getMessage());
//		}
//	}
}
