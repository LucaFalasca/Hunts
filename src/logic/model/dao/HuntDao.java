package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.model.Database;

public class HuntDao {
	
	Connection conn;
	
	public int createHunt() {
		
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		
		return -1;
	}
	
	
}
