package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import logic.model.Database;

public class LoginDao {

	public boolean login(String username, String password) {
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("call login(?,?,?);");
			
			//Input Param
			stmt.setString(1, username);
			stmt.setString(2, password);
			
			boolean haveResult = stmt.execute();
			
			
			while(haveResult) {
				ResultSet rs = stmt.getResultSet();
				
				while (rs.next()) {
					/*
			        String coffeeName = rs.getString("COF_NAME");
			        int supplierID = rs.getInt("SUP_ID");
			        float price = rs.getFloat("PRICE");
			        int sales = rs.getInt("SALES");
			        int total = rs.getInt("TOTAL");
			        */
			      }
				
				haveResult = stmt.getMoreResults();
			}
			
			//OutputParam
			boolean log = stmt.getBoolean(3);
			
			return log;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
