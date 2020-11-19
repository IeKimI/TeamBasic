package edu.wpi.cs.basic.demo.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialArray;

import edu.wpi.cs.basic.demo.model.AlternativeChoice;
import edu.wpi.cs.basic.demo.model.Choice;
import edu.wpi.cs.basic.demo.model.TeamMember;

/**
 * String uniqueID, ArrayList<AlternativeChoice> alternativeChoices,
 * ArrayList<TeamMember> participatingMembers, String description, Date
 * dayOfCompletion, float daysOld, boolean isCompleted
 * 
 * @author eri
 *
 */
public class ChoiceDatabase {

	java.sql.Connection conn;

	final String tblName = "Choice"; // Exact capitalization

	public ChoiceDatabase() {
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
				choice = generateConstant(resultSet);
			}
			resultSet.close();
			ps.close();

			return choice;

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Failed in getting choice: " + e.getMessage());
		}
	}

	public boolean deleteConstant(Choice choice) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, choice.uniqueID);
			int numAffected = ps.executeUpdate();
			ps.close();

			return (numAffected == 1);

		} catch (Exception e) {
			throw new Exception("Failed to insert constant: " + e.getMessage());
		}
	}

	public boolean addChoice(Choice choice) throws Exception {
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM " + tblName + " WHERE uniqueID = ?;");
			ps.setString(1, choice.uniqueID);
			ResultSet resultSet = ps.executeQuery();

			// already present?
			while (resultSet.next()) {
				Choice c = generateConstant(resultSet);
				resultSet.close();
				return false;
			}

			// INSERT INTO table_name (column1, column2, column3, ...)
			// VALUES (value1, value2, value3, ...);
			ps = conn.prepareStatement("INSERT INTO " + tblName
					+ " (uniqueID, alternativeChoices, participatingMembers, description, dayOfCompletion, daysOld, isCompleted) values(?,?,?,?,?,?,?);");
			ps.setString(1, choice.uniqueID);
//			ps.setDouble(2, choice.value);
			
			Array alternativeChoicesArray = new Array() {
				Choice clone = choice;
				@Override
				public void free() throws SQLException {
					// TODO Auto-generated method stub

				}

				@Override
				public Object getArray() throws SQLException {
					return clone.getAlternativeChoices().toArray();
				}

				@Override
				public Object getArray(Map<String, Class<?>> arg0) throws SQLException {
					return clone.getAlternativeChoices().toArray();
				}

				@Override
				public Object getArray(long arg0, int arg1) throws SQLException {
					return clone.getAlternativeChoices().toArray();
				}

				@Override
				public Object getArray(long arg0, int arg1, Map<String, Class<?>> arg2) throws SQLException {
					return clone.getAlternativeChoices().toArray();
				}

				@Override
				public int getBaseType() throws SQLException {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public String getBaseTypeName() throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ResultSet getResultSet() throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ResultSet getResultSet(Map<String, Class<?>> arg0) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ResultSet getResultSet(long arg0, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ResultSet getResultSet(long arg0, int arg1, Map<String, Class<?>> arg2) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

			};
			Array participatingMembersArray = new Array() {
				Choice clone = choice;
				@Override
				public void free() throws SQLException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public Object getArray() throws SQLException {
					// TODO Auto-generated method stub
					return  clone.getParticipatingMembers();
				}

				@Override
				public Object getArray(Map<String, Class<?>> map) throws SQLException {
					// TODO Auto-generated method stub
					return  clone.getParticipatingMembers();
				}

				@Override
				public Object getArray(long index, int count) throws SQLException {
					// TODO Auto-generated method stub
					return  clone.getParticipatingMembers();
				}

				@Override
				public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
					// TODO Auto-generated method stub
					return  clone.getParticipatingMembers();
				}

				@Override
				public int getBaseType() throws SQLException {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public String getBaseTypeName() throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ResultSet getResultSet() throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ResultSet getResultSet(long index, int count) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
					// TODO Auto-generated method stub
					return null;
				}
				
			};
			ps.setArray(2, alternativeChoicesArray);
			ps.setArray(3, participatingMembersArray);
			ps.setString(4, choice.getDescription());
			ps.setDate(5, choice.getDayOfCompletion());
			ps.setFloat(6, choice.getDaysOld());
			ps.setBoolean(7, choice.isCompleted());
			ps.execute();
			return true;

		} catch (Exception e) {
			throw new Exception("Failed to insert constant: " + e.getMessage());
		}
	}

	public List<Choice> getAllChoices() throws Exception {

		List<Choice> allChoices = new ArrayList<>();
		try {
			Statement statement = conn.createStatement();
			String query = "SELECT * FROM " + tblName + ";";
			ResultSet resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				Choice c = generateConstant(resultSet);
				allChoices.add(c);
			}
			resultSet.close();
			statement.close();
			return allChoices;

		} catch (Exception e) {
			throw new Exception("Failed in getting constants: " + e.getMessage());
		}
	}

	private Choice generateConstant(ResultSet resultSet) throws Exception {
		String uniqueID = resultSet.getString("uniqueID");
		ArrayList<AlternativeChoice> alternativeChoices = new ArrayList<AlternativeChoice>();
		Array data= resultSet
		.getArray("alternativeChoices");
		for(int i=0;i<data.length;i++) { //Need to find a way to traverse the Array and copy all elements to the ArrayList
			
		}
		ArrayList<TeamMember> participatingMembers = (ArrayList<TeamMember>) resultSet.getArray("participatingMembers");
		String description = resultSet.getString("description");
		Date dayOfCompletion = resultSet.getDate("dayOfCompletion");
		float daysOld = resultSet.getFloat("daysOld");
		boolean isCompleted = resultSet.getBoolean("isCompleted");
		return new Choice(uniqueID, alternativeChoices, participatingMembers, description, dayOfCompletion, daysOld,
				isCompleted);
	}

}
