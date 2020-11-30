package edu.wpi.cs.basic.demo.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.Feedback;
import edu.wpi.cs.basic.demo.model.TeamMember;

/**
 * create table `alternativeChoice` ( `alternativeID` VARCHAR(64) not null
 * default, `choiceID` VARCHAR(64) not null default,
 * 
 * `description` VARCHAR(64), primary key (`alternativeID`)
 * 
 * ) engine=MyISAM default charset=latin1;
 *
 */
public class AlternativeChoiceDAO {

	static java.sql.Connection conn;

	final String tblName = "AlternativeChoice"; // Exact capitalization

	public AlternativeChoiceDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public List<AlternativeChoice> getAlternativeChoice(String uniqueID) throws Exception {

		try {
			List<AlternativeChoice> alternativeChoices = new ArrayList<AlternativeChoice>();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choiceID=?;");
			ps.setString(1, uniqueID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				alternativeChoices.add(generateAltnerativeChoice(resultSet));
			}
			resultSet.close();
			ps.close();

			return alternativeChoices;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting Alternatives: " + e.getMessage());
		}
	}

//	public boolean updateChoice(AlternativeChoice alternativeChoice) throws Exception {
//		try {
//			String query = "UPDATE " + tblName + " SET isCompleted=? WHERE uniqueID=?;";
//			PreparedStatement ps = conn.prepareStatement(query);
//			ps.setString(1, alternativeChoice.getAlternativeID());
////            ps.setString(2, Choice.getChosenAlternative().getAlternativeID());
//			ps.setString(2, alternativeChoice.getChoiceID());
//			ps.setString(3, alternativeChoice.getDescription());
//			int numAffected = ps.executeUpdate();
//			ps.close();
//
//			return (numAffected == 1);
//		} catch (Exception e) {
//			throw new Exception("Failed to update report: " + e.getMessage());
//		}
//	}

	public boolean deleteChoice(Choice Choice) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, Choice.uniqueID);
			int numAffected = ps.executeUpdate();
			ps.close();
			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to delete Alternative: " + e.getMessage());
		}
	}

	public boolean addAlternative(AlternativeChoice alternative) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID = ?;");
			ps.setInt(1, alternative.getAlternativeID());
			ResultSet resultSet = ps.executeQuery();

			// already present?
			while (resultSet.next()) {
				AlternativeChoice ac = generateAltnerativeChoice(resultSet);
				resultSet.close();
				return false;
			}

			ps = conn.prepareStatement("INSERT INTO " + tblName + " (choiceID, description) values(?,?);");
//			alternative.setChoiceID(alternative.getAlternativeID());
			ps.setString(1, alternative.getChoiceID());
			ps.setString(2, alternative.getDescription());
			ps.execute();
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert Alternatives: " + e.getMessage());
		}
	}

	public List<AlternativeChoice> getAllAlternatives(String uniqueID) throws Exception {

		List<AlternativeChoice> alternatives = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choiceID = ?;");
			ps.setString(1, uniqueID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				AlternativeChoice c = generateAltnerativeChoice(resultSet);
				alternatives.add(c);
			}
			resultSet.close();
			statement.close();
			return alternatives;

		} catch (Exception e) {
			throw new Exception("Failed in getting Alternatives: " + e.getMessage());
		}
	}
    public String getAlternativeChoiceByID(int alternativeID) throws Exception {
   	 try {
        	AlternativeChoice alternative = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=?;");
            ps.setInt(1,  alternativeID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	alternative = generateAltnerativeChoice(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return alternative.getDescription();

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting the alternative by its ID: " + e.getMessage());
        }
   }

	private AlternativeChoice generateAltnerativeChoice(ResultSet resultSet) throws Exception {
		int alternativeID = resultSet.getInt("alternativeID");

//		// pass in the alternativeID to get the approvals of a specific alternative
//		ArrayList<TeamMember> approvals= ApprovalDAO.getApprovals(alternativeID);
//		
//		// pass in the altnerativeID to get the disapprovals of a specific alternative
//		ArrayList<TeamMember> disapprovals= DisapprovalDAO.getDisapprovals(alternativeID);
//		
//		// pass in the alternativeID to get the feedback of a specific alternativeID
//		ArrayList<Feedback> feedback = FeedbackDAO.getFeedbacks(alternativeID);

		String choiceID = resultSet.getString("choiceID");
		String description = resultSet.getString("description");

//		AlternativeChoice databaseInquery= 
		return new AlternativeChoice(alternativeID, choiceID, description);
		/**
		 * public AlternativeChoice(ArrayList<TeamMember> approvals,
		 * ArrayList<TeamMember> disapprovals, ArrayList<Feedback> feedback, String
		 * description, String alternativeID, String choiceID) { super(); this.approvals
		 * = approvals; this.disapprovals = disapprovals; this.feedback = feedback;
		 * this.description = description; this.alternativeID = alternativeID;
		 * this.choiceID = choiceID; }
		 * 
		 */
	}
}
