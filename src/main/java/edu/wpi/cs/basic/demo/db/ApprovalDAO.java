package edu.wpi.cs.basic.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Approval;
import edu.wpi.cs.basic.demo.model.ApprovalInfo;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.TeamMember;

public class ApprovalDAO {
	

	static java.sql.Connection conn;

	final static String tblName = "Approvals"; // Exact capitalization

	public ApprovalDAO() {
		try {
			conn = DatabaseUtil.connect();
		} catch (Exception e) {
			conn = null;
		}
	}

	public Approval getApproval(int alternativeID, int teamMemberID) throws Exception {

		try {
			Approval approval = null;
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=? AND teamMemberID=?;");
			ps.setInt(1, alternativeID);
			ps.setInt(2,  teamMemberID);
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				approval = generateApprovals(resultSet);
			}
			resultSet.close();
			ps.close();

			return approval;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting the approval: " + e.getMessage());
		}
	}

	/**
	 * try {
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
	 * @param logger 
	 * @param alternativeID
	 * @return
	 * @throws Exception
	 */
	public ApprovalInfo getApprovalsAltID(LambdaLogger logger, int alternativeID) throws Exception {
		try {
			logger.log("In getApprovalsAltID");
			int numOfApprovals = 0;
			List<String> listOfTeamMembers = new ArrayList<String>();
			TeamMemberDAO teamMemberDAO = new TeamMemberDAO();
			AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();
			
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=?;");
			ps.setInt(1, alternativeID);
			logger.log("preparedstatemnt?");

			ResultSet resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				Approval approval = generateApprovals(resultSet);
				numOfApprovals++;
				listOfTeamMembers.add(teamMemberDAO.getTeamMemberByID(approval.getTeamMemberID()));

				// check for the vote type?
				// add the num of approvals and teamMembers to the list
			}
			resultSet.close();
			
			String altDescription = altDAO.getAlternativeChoiceByID(alternativeID);
			
			return new ApprovalInfo(alternativeID, altDescription, numOfApprovals, listOfTeamMembers);
		}
		catch (Exception e) {
			throw new Exception ("Failed in getting approvals for the alternative: " + alternativeID + e.getMessage());
		}
	}
	
	
	public List<ApprovalInfo> getApprovalsChoiceID(LambdaLogger logger, String choiceID) throws Exception {
    	List<ApprovalInfo> approvals = new ArrayList<ApprovalInfo>();
    	AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();
    	
    	List<AlternativeChoice> alternatives = altDAO.getAllAlternatives(choiceID);
    	for(AlternativeChoice alt : alternatives) {
    		approvals.add(getApprovalsAltID(logger, alt.getAlternativeID()));
    	} 
    	
    	return approvals;
    }
	public boolean initializeApproval(Approval approval) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (teamMemberID,alternativeID,approval,disapproval) values(?,?,?,?);");
            ps.setInt(1, approval.getTeamMemberID());
            ps.setInt(2, approval.getAlternativeID());
            ps.setBoolean(3, false);  
            ps.setBoolean(4, false);      

            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add an approval: " + e.getMessage());
        }
    }
	@Deprecated
	public boolean addApproval(Approval approval) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + tblName + " (teamMemberID,alternativeID,approval,disapproval) values(?,?,?,?);");
            ps.setInt(1, approval.getTeamMemberID());
            ps.setInt(2, approval.getAlternativeID());
            ps.setBoolean(3, true);  
            ps.setBoolean(4, false);      

            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to add an approval: " + e.getMessage());
        }
    }

	public boolean deleteApproval(LambdaLogger logger, int alternativeID, int teamMemberID) throws Exception {
		 try {
			 logger.log("In delteApproval");
	            PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE alternativeID = ? AND teamMemberID = ?;");
	            ps.setInt(1, alternativeID);
	            ps.setInt(2, teamMemberID);
	            logger.log("The statement is done being constructed.");
	            int numAffected = ps.executeUpdate();
	            ps.close();
	            
	            return (numAffected >= 1);

	        } catch (Exception e) {
	            throw new Exception("Failed to delete the approval: " + e.getMessage());
	        }
	}



	private Approval generateApprovals(ResultSet resultSet) throws Exception {
		int approvalOrDisapprovalID = resultSet.getInt("approval/disapprovalID");
    	int teamMemberID = resultSet.getInt("teamMemberID");
    	int alternativeID = resultSet.getInt("alternativeID");
        return new Approval(approvalOrDisapprovalID, alternativeID, teamMemberID);
    }
}
