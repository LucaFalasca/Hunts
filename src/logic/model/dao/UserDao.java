package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;

import logic.model.Database;
import logic.model.entity.User;

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
}
