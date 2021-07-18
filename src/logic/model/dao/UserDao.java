package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.SQLException;

import logic.exception.DatabaseException;
import logic.model.Database;

public class UserDao {
	
	public boolean login(String username, String password) throws DatabaseException {
		var conn = Database.getConnection();
		
		try(CallableStatement stmt = conn.prepareCall("call login(?,?,?);")) {
			
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
			throw new DatabaseException();
		} 
	}
	
	public void register(String username, String password) throws DatabaseException {
		var conn = Database.getConnection();
		
		try(CallableStatement stmt = conn.prepareCall("call add_user(?,?);");) {
			
			//Input Param
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			boolean haveResult = stmt.execute();
			
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
		} catch (SQLException e) {
			throw new DatabaseException();
		} 
	}
}
