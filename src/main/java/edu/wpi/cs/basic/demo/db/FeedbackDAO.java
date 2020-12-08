package edu.wpi.cs.basic.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.Feedback;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class FeedbackDAO {
	static java.sql.Connection conn;

	final static String tblName = "AlternativeChoices"; // Exact capitalization

	public FeedbackDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public AlternativeChoice getAlternativeChoice(String uniqueID) throws Exception {

		try {
			AlternativeChoice alternativeChoice = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=?;");
			ps.setString(1, alternativeChoice.getAlternativeID());
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				alternativeChoice = generateApprovals(resultSet); // return a list of teamMembers and not an Approval object
			}
			resultSet.close();
			ps.close();

			return alternativeChoice;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting choice: " + e.getMessage());
		}
	}

	public boolean updateChoice(AlternativeChoice alternativeChoice) throws Exception {
		try {
			String query = "UPDATE " + tblName + " SET isCompleted=? WHERE uniqueID=?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, alternativeChoice.getAlternativeID());
//            ps.setString(2, Choice.getChosenAlternative().getAlternativeID());
			ps.setString(2, alternativeChoice.getChoiceID());
			ps.setString(3, alternativeChoice.getDescription());
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
				AlternativeChoice c = generateAltnerativeChoice(resultSet);
				resultSet.close();
				return false;
			}

			ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,value) values(?,?);");
			ps.setString(1, Choice.uniqueID);
			ps.setString(2, Choice.getChosenAlternative().getAlternativeID());
			ps.setInt(3, Choice.getParticipatingMembers().size());
			ps.setString(4, Choice.getDescription());
			ps.setDate(5, Choice.getDateOfCompletion());
			ps.setDate(6, Choice.getDateOfCreation());
			ps.setBoolean(7, Choice.isComplete());
			ps.execute();
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert Choice: " + e.getMessage());
		}
	}

	public static ArrayList<Feedback> getFeedbacks(String uniqueID) throws Exception {

		ArrayList<Feedback> allChoices = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM " + tblName + ";";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Feedback c = generateFeedback(resultSet);
				allChoices.add(c);
			}
			resultSet.close();
			statement.close();
			return allChoices;

		} catch (Exception e) {
			throw new Exception("Failed in getting Choices: " + e.getMessage());
		}
	}

	static Feedback generateFeedback(ResultSet resultSet) throws Exception {
		String alternativeID = resultSet.getString("alternativeID");
		String feedbackID = resultSet.getString("feedbackID");
		String teamMemberName = resultSet.getString("teamMemberName");
		Timestamp timestamp = resultSet.getTimestamp("timestamp");
		String text = resultSet.getString("text");
		TeamMemberDAO teamMemberDatabase = new TeamMemberDAO();

		// passing in feedbackID?
		List<TeamMember> teamMembers = teamMemberDatabase.getAllTeamMembers(feedbackID);

//		AlternativeChoice databaseInquery= 
		return new Feedback(timestamp, text, teamMembers);
		/**
		 * 
		 * public Feedback(String timeS, String txt, ArrayList<TeamMember> users) {
		 * super(); this.timeStamp=timeS; this.text=txt; this.users=users;
		 * 
		 * 
		 * }
		 * 
		 * create table `feedback` (
		 * 
		 * ‘feedbackID’ VARCHAR(64) not null default,
		 * 
		 * ‘alternativeID’, VARCHAR(64) not null default,
		 * 
		 * ‘teamMemberName’ VARCHAR(64) not null default,
		 * 
		 * `timestamp` TIMESTAMP(), `text` VARCHAR(64),
		 * 
		 * primary key (`feedbackID`)
		 * 
		 * ) engine=MyISAM default charset=latin1;
		 */
	}

}
