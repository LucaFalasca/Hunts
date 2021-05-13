package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import logic.model.Database;
import logic.model.entity.Map;

public class MapDao {
	
	public Map getMapById(int id) {
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("call get_map_by_id(?);");
			//Input Param
			stmt.setInt(1, id);
			
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
			
			stmt.close();
		}
		catch (Exception e) {
			// TODO: handle exception
		}
			
			
			return null;
	}
	
	public void saveMap(Map map) {
		//TODO
	}
	
	public int getLastIndex() {
		return 0;
	}
}
