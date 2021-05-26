package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import logic.model.Database;
import logic.model.entity.Map;
import logic.model.entity.Zone;

public class MapDao {
	
	public Map getMapById(int id) {
		return null;
		
	}
	
	public Map getMapByName(String username, String nameMap) {
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("call get_map_by_id(?);");
			//Input Param
			//stmt.setInt(1, id);
			
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
	
	public void saveMap(String username, Map map) {
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("call save_map(?, ?, ?);");
			//Input Param
			stmt.setString(1, username);
			stmt.setString(2, map.getName());
			stmt.setString(3, "");
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(map.getZones() != null) {
			List<Zone> zones = map.getZones();
			int i = 0;
			for(Zone zone : zones) {
				try {
					stmt = conn.prepareCall("call add_zone_to_map(?, ?, ?, ?, ?, ?, ?, ?);");
					//Input Param
					stmt.setString(1, zone.getName() + i++);
					stmt.setString(2, map.getName());
					stmt.setString(3, username);
					stmt.setInt(4, (int) zone.getStartX());
					stmt.setInt(5, (int) zone.getStartY());
					stmt.setInt(6, (int) zone.getEndX());
					stmt.setInt(7, (int) zone.getEndY());
					int shape = 0;
					switch(zone.getType()) {
						case RECTANGLE: shape = 1;
						break;
						case OVAL: shape = 2;
						default: shape = 1;
					}
					stmt.setInt(8, shape);
					
					boolean haveResult = stmt.execute();
					
					while(haveResult) {
						haveResult = stmt.getMoreResults();
					}
					
					stmt.close();
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<Map> getMapList(String username){
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		List<Map> maps = new ArrayList<Map>();
		try {
			stmt = conn.prepareCall("call get_maps(?);");
			//Input Param
			stmt.setString(1, username);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					
			        String nomeMappa = rs.getString("Nome");
			        String pathImmagine = rs.getString("Immagine");
			        
			        Map map = new Map(nomeMappa);
			        //setImmagine
			        
			        maps.add(map);
			      }
				haveResult = stmt.getMoreResults();
			}
			
			
			
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return maps;
	}
}
