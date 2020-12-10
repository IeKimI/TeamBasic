package edu.wpi.cs.basic.demo.db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

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

	final static String tblName = "AlternativeChoice"; // Exact capitalization

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

	public boolean deleteAlternativeChoice(LambdaLogger logger, Choice Choice) {
		try {
			logger.log("deleteAlternativeChoice has been entered");
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE choiceID = ?;");
			ps.setString(1, Choice.uniqueID);
			int numAffected = ps.executeUpdate();
			ps.close();
			logger.log("Exiting deleteAlternativeChoice");
			return (numAffected >= 1);

		} catch (Exception e) {
			return false;
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
    
    public int getAltIDByChoiceIDAndDesc(String choiceID, String description) throws Exception {
    	try {
         	 AlternativeChoice alternative = null;
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE choiceID=? AND description=?;");
             ps.setString(1,  choiceID);
             ps.setString(2, description);
             
             ResultSet resultSet = ps.executeQuery();
             
             while (resultSet.next()) {
             	alternative = generateAltnerativeChoice(resultSet);
             }
             resultSet.close();
             ps.close();
             
             return alternative.getAlternativeID();

         } catch (Exception e) {
         	e.printStackTrace();
             throw new Exception("Failed in getting the alternativeID by choiceID and description: " + e.getMessage());
         }
    }

    public String getChoiceIDByAltID (int altID) throws Exception {
    	try {
        	AlternativeChoice alternative = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=?;");
            ps.setInt(1,  altID);
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
            	alternative = generateAltnerativeChoice(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return alternative.getChoiceID();

        } catch (Exception e) {
        	e.printStackTrace();
            throw new Exception("Failed in getting the choiceID by altID: " + e.getMessage());
        }
    }

	public AlternativeChoice generateAltnerativeChoice(ResultSet resultSet) throws Exception {
		int alternativeID = resultSet.getInt("alternativeID");


		String choiceID = resultSet.getString("choiceID");
		String description = resultSet.getString("description");

		return new AlternativeChoice(alternativeID, choiceID, description);

	}
}
