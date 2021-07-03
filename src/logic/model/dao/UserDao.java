package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.model.Database;

public class UserDao {
	
	public boolean login(String username, String password) {
		var conn = Database.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("call login(?,?,?);");
			
			//Input Param
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setBoolean(3, false);
			
			boolean haveResult = stmt.execute();
			
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
			//OutputParam
			
			return stmt.getBoolean(3);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void register(String username, String password) {
		var conn = Database.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("call add_user(?,?);");
			
			//Input Param
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			boolean haveResult = stmt.execute();
			
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> getAllUser() {
		var conn = Database.getConnection();
		CallableStatement stmt = null;
		var allUsers = new ArrayList<String>();
		try {
			stmt = conn.prepareCall("call get_all_user(?,?);");
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				var rs = stmt.getResultSet();
				
				while(rs.next()) {
					allUsers.add(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return allUsers;
	}
}
