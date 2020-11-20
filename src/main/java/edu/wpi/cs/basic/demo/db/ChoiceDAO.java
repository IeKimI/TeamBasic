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
 * create table `choice` ( 
`uniqueID` VARCHAR(64) not null default, 
`description` VARCHAR(64) not null default, 

‘maxNumOfteamMembers’ INT, 

‘chosenAlternativeID’ VARCHAR(64), 

‘isCompleted’ BOOLEAN, 

‘dateOfCompletion’ DATE, 

'dateOfCreation’ DATE, 
primary key (`uniqueID`) 

) engine=MyISAM default charset=latin1; 
 * @author eri
 *
 */
public class ChoiceDAO {

java.sql.Connection conn;
	
	final String tblName = "Choices";   // Exact capitalization

    public ChoiceDAO() {
    	try  {
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
    
    public boolean updateChoice(Choice Choice) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET value=? WHERE uniqueID=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, Choice.uniqueID);
            ps.setString(2, Choice.alternativeID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    
    public boolean deleteChoice(Choice Choice) throws Exception {
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
            ps.setString(1,  Choice.uniqueID);
            ps.setString(2,  Choice.getChosenAlternative().getAlternativeID());
            ps.execute();
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
    
    private Choice generateChoice(ResultSet resultSet) throws Exception {
        String uniqueID  = resultSet.getString("name");
        String alternativeID = resultSet.getString("alternativeChoice");
		ArrayList<TeamMember> participatingMembers;
		String description = resultSet.getString("description");
		Date dayOfCompletion = resultSet.getDate("dayOfCompletion");
		float daysOld = resultSet.getFloat("daysOld");
		boolean isCompleted = resultSet.getBoolean("isCompleted");
		return new Choice(uniqueID, alternativeID, participatingMembers, description, dayOfCompletion, daysOld,
				isCompleted);
    }
}
