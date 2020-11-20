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
create table `alternativeChoice` ( 
`alternativeID` VARCHAR(64) not null default, 
`choiceID` VARCHAR(64) not null default,  

`description` VARCHAR(64), 
primary key (`alternativeID`) 

) engine=MyISAM default charset=latin1; 
 *
 */
public class AlternativeChoiceDAO {

java.sql.Connection conn;
	
	final String tblName = "Alternative Choices";   // Exact capitalization

    public AlternativeChoiceDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		conn = null;
    	}
    }

    public AlternativeChoice getAlternativeChoice(String alternativeID) throws Exception {

		try {
			AlternativeChoice altchoice = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID=?;");
			ps.setString(1, alternativeID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				altchoice = generateAlternativeChoice(resultSet);
			}
			resultSet.close();
			ps.close();

			return altchoice;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting choice: " + e.getMessage());
		}
	}
    
    public boolean updateAlternativeChoice(AlternativeChoice altchoice) throws Exception {
        try {
        	String query = "UPDATE " + tblName + " SET isCompleted=? WHERE uniqueID=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, altchoice.alternativeID);
//            ps.setString(2, Choice.getChosenAlternative().getAlternativeID());
            ps.setBoolean(7, altchoice.completeAlternativeChoice(altchoice));
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update report: " + e.getMessage());
        }
    }
    
    public boolean deleteAlternativeChoice(AlternativeChoice AlternativeChoice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE uniqueID = ?;");
            ps.setString(1, AlternativeChoice.alternativeID);
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to insert Alternative Choice: " + e.getMessage());
        }
    }


    public boolean addAlternativeChoice(AlternativeChoice AltChoice) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID = ?;");
            ps.setString(1, AltChoice.alternativeID);
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
                Choice c = generateChoice(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO " + tblName + " (name,value) values(?,?);");
            ps.setString(1,  AltChoice.alternativeID);
            ps.setString(2,  AltChoice.getChosenAlternative().getAlternativeID());
            ps.setInt(3, AltChoice.getParticipatingMembers().size());
            ps.setString(4, AltChoice.getDescription());
            ps.setDate(5,  AltChoice.getDayOfCompletion());
            ps.setFloat(6, AltChoice.getDaysOld());
            ps.setBoolean(7, AltChoice.isComplete());
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert Alternative Choice: " + e.getMessage());
        }
    }

    public List<AlternativeChoice> getAllAlternativeChoices() throws Exception {
        
        List<AlternativeChoice> allAlternativeChoices = new ArrayList<>();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM " + tblName + ";";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                AlternativeChoice ac = generateAlternativeChoice(resultSet);
                allAlternativeChoices.add(ac);
            }
            resultSet.close();
            statement.close();
            return allAlternativeChoices;

        } catch (Exception e) {
            throw new Exception("Failed in getting Alternative Choices: " + e.getMessage());
        }
    }
    
    public AlternativeChoice generateAlternativeChoice(ResultSet resultSet) throws Exception {
        String alternativeID = resultSet.getString("name");
		ArrayList<TeamMember> participatingMembers;
		String description = resultSet.getString("description");
		Date dayOfCompletion = resultSet.getDate("dayOfCompletion");
		float daysOld = resultSet.getFloat("daysOld");
		boolean isCompleted = resultSet.getBoolean("isCompleted");
//		return new Choice(uniqueID, alternativeID, participatingMembers, description, dayOfCompletion, daysOld,
//				isCompleted);
    }
}
