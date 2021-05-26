package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import logic.model.Database;

public class HuntDao {
	
	public int createHunt() {
		
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		int id = 0;
		
		try {
			
			stmt = conn.prepareCall("call addObject(?, ?);");
			
			stmt.setString(1, "name");
			
			boolean result = stmt.execute();
			
			if(result)
				id = stmt.getInt(3);
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return id;
	}
	
	
}
