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

	boolean deleteFeedback(Feedback Choice) throws Exception {
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
		return false;
	}

	public boolean addFeedback(Feedback feedback) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO " + tblName + " (text,alternativeID,timestamp,teamMemberID) values(?,?,?,?);");
			ps.setString(1, feedback.getText());
			ps.setInt(2, feedback.getAlternativeChoiceID());
			ps.setTimestamp(3, feedback.getTimeStamp());
			ps.setInt(4, feedback.getTeamMemberID());
			ps.execute();
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert Feedback: " + e.getMessage());
		}
	}

	public static Feedback getFeedback(int feedbackID) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + "WHERE feedbackID=?;");
			ps.setInt(1, feedbackID);
			ResultSet resultSet = ps.executeQuery();
			Feedback feedback = generateFeedback(resultSet);
			resultSet.close();
			ps.close();
			if (feedback == null)
				throw new Exception();
			return feedback;

		} catch (Exception e) {
			throw new Exception("Failed in getting Choices: " + e.getMessage());
		}
	}

	public static List<Feedback> getAllFeedbackForAlternative(int altID) throws Exception {
		List<Feedback> allFeedback = new ArrayList<>();
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM " + AlternativeChoiceDAO.tblName + " WHERE alternativeID=?;");
			ps.setInt(1, altID);
			ResultSet resultSet = ps.executeQuery();
			while (resultSet.next()) {
				Feedback feedback = generateMultipleFeedback(resultSet);
				allFeedback.add(feedback);
			}
			resultSet.close();
			ps.close();
			return allFeedback;
		} catch (Exception e) {
			throw new Exception("Failed in getting Choices: " + e.getMessage());
		}
	}

	static Feedback generateFeedback(ResultSet resultSet) throws Exception {
		while (resultSet.next()) {
			int alternativeID = resultSet.getInt("alternativeChoiceID");
			int feedbackID = resultSet.getInt("feedbackID");
			int teamMemberID = resultSet.getInt("teamMemberID");
			Timestamp timestamp = resultSet.getTimestamp("timestamp");
			String text = resultSet.getString("text");
			return new Feedback(timestamp, text, teamMemberID, alternativeID, feedbackID);
		}
		return null;
	}

	static Feedback generateMultipleFeedback(ResultSet resultSet) throws Exception {
		int alternativeID = resultSet.getInt("alternativeChoiceID");
		int feedbackID = resultSet.getInt("feedbackID");
		int teamMemberID = resultSet.getInt("teamMemberID");
		Timestamp timestamp = resultSet.getTimestamp("timestamp");
		String text = resultSet.getString("text");
		return new Feedback(timestamp, text, teamMemberID, alternativeID, feedbackID);
	}

}
