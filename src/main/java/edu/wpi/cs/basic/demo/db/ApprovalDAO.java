package edu.wpi.cs.basic.demo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.LambdaLogger;

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
			int numOfDisapprovals = 0;
			List<String> listOfTeamMembers = new ArrayList<String>();
			List<String> listOfTeamMemberDisapproval = new ArrayList<String>();
			TeamMemberDAO teamMemberDAO = new TeamMemberDAO();
			AlternativeChoiceDAO altDAO = new AlternativeChoiceDAO();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=?;");
			ps.setInt(1, alternativeID);
			logger.log("preparedstatemnt?");

			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Approval approval = generateApprovals(resultSet);
				System.out.println(approval);
				System.out.println(approval.isApproval());
				System.out.println(approval.isDisapproved());

				if (approval.isApproval()) {
					numOfApprovals++;
					listOfTeamMembers.add(teamMemberDAO.getTeamMemberByID(approval.getTeamMemberID()));
				}
				if (approval.isDisapproved()) {
					numOfDisapprovals++;
					listOfTeamMemberDisapproval.add(teamMemberDAO.getTeamMemberByID(approval.getTeamMemberID()));
				}

				// check for the vote type?
				// add the num of approvals and teamMembers to the list
			}
			resultSet.close();

			String altDescription = altDAO.getAlternativeChoiceByID(alternativeID);

			return new ApprovalInfo(alternativeID, altDescription, numOfApprovals, numOfDisapprovals, listOfTeamMembers,
					listOfTeamMemberDisapproval);
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

	public FlipApprovalResponse flipApprovalOrDisapproval(LambdaLogger logger, boolean whichIsFlipped,
			int alternativeID, int teamMemberID, List<ApprovalInfo> list) {
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE alternativeID=? AND teamMemberID=?;");
			PreparedStatement insertBack = null;
			ps.setInt(1, alternativeID);
			ps.setInt(2, teamMemberID);
			logger.log("Done creating statements");
			ResultSet resultSet = ps.executeQuery();
			Approval toChange = generateApprovalsFlip(resultSet);

			if (whichIsFlipped) {
				logger.log("Flipping Approval");
				// Flip approval
				if (toChange.isDisapproved()) {
					logger.log("Setting Dissapproval to False");
					PreparedStatement changeDissaproval = conn.prepareStatement(
							"UPDATE " + tblName + " SET disapproval = ? WHERE approvalAndDisapprovalID = ?;");
					changeDissaproval.setBoolean(1, false);
					changeDissaproval.setInt(2, toChange.getApprovalID());
					changeDissaproval.execute();
				}
				logger.log("Setting insert back statement");
				toChange.setApproval(!toChange.isApproved());
				insertBack = conn.prepareStatement(
						"UPDATE " + tblName + " SET approval = ? WHERE approvalAndDisapprovalID = ?;");
				insertBack.setBoolean(1, toChange.isApproved());
				insertBack.setInt(2, toChange.getApprovalID());
				logger.log("Changing insertBack statement");
				insertBack.execute();
				return new FlipApprovalResponse("Approval was flipped to " + toChange.isApproval() + ". ApprovalID: "
						+ toChange.getApprovalID(), 200, list);
			}

			// Flip disapproval
			logger.log("Flipping Disapproval");
			if (toChange.isApproved()) {
				logger.log("Setting Dissapproval to False");
				PreparedStatement changeDissaproval = conn.prepareStatement(
						"UPDATE " + tblName + " SET approval = ? WHERE approvalAndDisapprovalID = ?;");
				changeDissaproval.setBoolean(1, false);
				changeDissaproval.setInt(2, toChange.getApprovalID());
				changeDissaproval.execute();
			}
			toChange.setDisapproved(!toChange.isDisapproved());
			insertBack = conn
					.prepareStatement("UPDATE " + tblName + " SET disapproval = ? WHERE approvalAndDisapprovalID = ?;");
			insertBack.setBoolean(1, toChange.isDisapproved());
			insertBack.setInt(2, toChange.getApprovalID());
			logger.log("Changing insertBack statement");
			insertBack.execute();
			return new FlipApprovalResponse("Disapproval was flipped to " + toChange.isDisapproved() + ". ApprovalID: "
					+ toChange.getApprovalID(), 200, list);
		} catch (Exception e) {
			logger.log(e.toString());
			return new FlipApprovalResponse("An exception was caught ", 400);
		}
	}


	private Approval generateApprovals(ResultSet resultSet) throws Exception {
			int approvalID = resultSet.getInt("approvalAndDisapprovalID");
			int altID = resultSet.getInt("alternativeID");
			int teamMemberID = resultSet.getInt("teamMemberID");
			boolean approval = resultSet.getBoolean("approval");
			boolean disapproval = resultSet.getBoolean("disapproval");
			return new Approval(approvalID, altID, teamMemberID, approval, disapproval);
	}
	private Approval generateApprovalsFlip(ResultSet resultSet) throws Exception {
		while(resultSet.next()) {
		int approvalID = resultSet.getInt("approvalAndDisapprovalID");
		int altID = resultSet.getInt("alternativeID");
		int teamMemberID = resultSet.getInt("teamMemberID");
		boolean approval = resultSet.getBoolean("approval");
		boolean disapproval = resultSet.getBoolean("disapproval");
		return new Approval(approvalID, altID, teamMemberID, approval, disapproval);

		}
		return null;
}
	
}
