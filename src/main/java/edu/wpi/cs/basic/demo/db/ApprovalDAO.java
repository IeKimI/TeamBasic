package edu.wpi.cs.basic.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

import edu.wpi.cs.basic.demo.http.ApprovalResponse;
import edu.wpi.cs.basic.demo.http.FlipApprovalResponse;
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
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=? AND teamMemberID=?;");
			ps.setInt(1, alternativeID);
			ps.setInt(2, teamMemberID);
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
	 * try { Choice choice = null; PreparedStatement ps =
	 * conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID=?;");
	 * ps.setString(1, uniqueID); ResultSet resultSet = ps.executeQuery();
	 * 
	 * while (resultSet.next()) { choice = generateChoice(resultSet); }
	 * resultSet.close(); ps.close();
	 * 
	 * return choice;
	 * 
	 * } catch (Exception e) { e.printStackTrace(); throw new Exception("Failed in
	 * getting choice: " + e.getMessage()); }
	 * 
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
				if (approval.isApproval()) {
					numOfApprovals++;
					listOfTeamMembers.add(teamMemberDAO.getTeamMemberByID(approval.getTeamMemberID()));
				}

				// check for the vote type?
				// add the num of approvals and teamMembers to the list
			}
			resultSet.close();

			String altDescription = altDAO.getAlternativeChoiceByID(alternativeID);

			return new ApprovalInfo(alternativeID, altDescription, numOfApprovals, listOfTeamMembers);
		} catch (Exception e) {
			throw new Exception("Failed in getting approvals for the alternative: " + alternativeID + e.getMessage());
		}
	}

	public List<ApprovalInfo> getApprovalsChoiceID(LambdaLogger logger, String choiceID) throws Exception {
		List<ApprovalInfo> approvals = new ArrayList<ApprovalInfo>();
		AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();

		List<AlternativeChoice> alternatives = altDAO.getAllAlternatives(choiceID);
		System.out.println(alternatives);
		for (AlternativeChoice alt : alternatives) {
			approvals.add(getApprovalsAltID(logger, alt.getAlternativeID()));
		}

		return approvals;
	}

	public boolean initializeApproval(Approval approval) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO " + tblName + " (teamMemberID,alternativeID,approval,disapproval) values(?,?,?,?);");
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
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO " + tblName + " (teamMemberID,alternativeID,approval,disapproval) values(?,?,?,?);");
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

	public boolean deleteApproval(LambdaLogger logger, int alternativeID) {
		try {
			logger.log("In delteApproval");
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE alternativeID = ?;");
			ps.setInt(1, alternativeID);
			logger.log("The statement is done being constructed.");
			ps.executeUpdate();
			ps.close();
			logger.log("Exiting deleteApproval");
			return true;

		} catch (Exception e) {
			logger.log("Failed to delete the approval: " + e.getMessage());
			return false;
		}
	}

	public FlipApprovalResponse flipApprovalOrDisapproval(LambdaLogger logger, boolean whichIsFlipped, int approvalID)
			throws Exception {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE approval/disapprovalID=?;");
		PreparedStatement insertBack = conn
				.prepareStatement("UPDATE " + tblName + " SET ? = ? WHERE approval/disapprovalID = ?;");
		ps.setInt(1, approvalID);
		ResultSet resultSet = ps.executeQuery();
		Approval toChange = generateApprovals(resultSet);

		if (whichIsFlipped) {
			logger.log("Flipping Approval");
			// Flip approval
			if (toChange.isDisapproved()) {
				logger.log("Nothing could be changed as the approval cannot be approved if it is already disapproved.");
				return new FlipApprovalResponse(
						"Nothing could be changed as the approval cannot be approved if it is already disapproved. ApprovalID: "
								+ approvalID,
						400);
			}
			toChange.setApproval(!toChange.isApproved());
			insertBack.setString(1, "approval");
			insertBack.setBoolean(2, toChange.isApproved());
			insertBack.setInt(3, approvalID);
			logger.log("Changing insertBack statement");
			return new FlipApprovalResponse(
					"Approval was flipped to " + toChange.isApproval() + ". ApprovalID: " + approvalID, 200);
		}

		// Flip disapproval
		logger.log("Flipping Disapproval");
		if (toChange.isApproved()) {
			logger.log("Nothing could be changed as the disapproval cannot be approved if it is already disapproved.");
			return new FlipApprovalResponse(
					"Nothing could be changed as the disapproval cannot be approved if it is already disapproved. ApprovalID: "
							+ approvalID,
					400);
		}
		toChange.setDisapproved(!toChange.isDisapproved());
		insertBack.setString(1, "disapproval");
		insertBack.setBoolean(2, toChange.isDisapproved());
		insertBack.setInt(3, approvalID);
		return new FlipApprovalResponse(
				"Disapproval was flipped to " + toChange.isDisapproved() + ". ApprovalID: " + approvalID, 200);
	}

	// checks the current boolean value for approval or disapproval
	public boolean getApprovalOrDisapprovalStatus(int alternativeID, int teamMemberID, boolean flipApproval)
			throws Exception {
		try {
			boolean result;
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=? AND teamMemberID=?;");

			ps.setInt(1, alternativeID);
			ps.setInt(2, teamMemberID);

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				if (flipApproval) {
					result = resultSet.getBoolean("approval");
					ps.close();
					resultSet.close();
					return result;
				}
				result = resultSet.getBoolean("disapproval");
				ps.close();
				resultSet.close();
				return result;
			}
			throw new Exception("It is not working when trying to flipApproval: " + flipApproval);
		} catch (Exception e) {
			throw new Exception("Failed to get the approval column boolean" + e.getMessage());
		}

	}

	public boolean flipApproval(int alternativeID, int teamMemberID, boolean isApproval) throws Exception {
		try {
			boolean status = getApprovalOrDisapprovalStatus(alternativeID, teamMemberID, isApproval);

			String column;
			if (isApproval) {
				column = "approval";

			} else {
				column = "disapproval";
			}
			// UPDATE Approvals SET approval = false WHERE alternativeID = 646 AND
			// teamMemberID = 46;
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE " + tblName + " SET " + column + "= ? WHERE alternativeID=? AND teamMemberID=?;");

			ps.setBoolean(1, !status);
			ps.setInt(2, alternativeID);
			ps.setInt(3, teamMemberID);

			ps.execute();

			ps.close();

			return true;

		} catch (Exception e) {
			throw new Exception("Failed to flip an approval: " + e.getMessage());
		}
	}

	public boolean whatToFlip(int alternativeID, int teamMemberID, boolean isApproval) throws Exception {
		try {
			boolean status = getApprovalOrDisapprovalStatus(alternativeID, teamMemberID, isApproval);
			boolean statusTwo = getApprovalOrDisapprovalStatus(alternativeID, teamMemberID, !isApproval);
			
			if (status==false && statusTwo==true) {
				flipApproval(alternativeID, teamMemberID, true);
				flipApproval(alternativeID, teamMemberID, false);
			} else {
				flipApproval(alternativeID, teamMemberID, isApproval);
				
			}
			
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to determine what to flip: " + e.getMessage());
		}
	}

	private Approval generateApprovals(ResultSet resultSet) throws Exception {
		return new Approval(resultSet.getInt("approval/disapprovalID"), resultSet.getInt("alternativeID"),
				resultSet.getInt("teamMemberID"), resultSet.getBoolean("approval"),
				resultSet.getBoolean("disapproval"));
	}
}
