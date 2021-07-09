package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import logic.enumeration.Type;
import logic.model.Database;
import logic.model.entity.Map;
import logic.model.entity.Zone;

public class MapDao {
	
	public Map getMapById(String username, int idMap) {
		var conn = Database.getConnection();
		var map = new Map(idMap);
		try(CallableStatement stmt = conn.prepareCall("call get_map_by_id(?, ?);");) {
			
			//Input Param
			stmt.setString(1, username);
			stmt.setInt(2, idMap);
			
			boolean haveResult = stmt.execute();
			var i = 0;
			
			List<Zone> zones = new ArrayList<>();
			while(haveResult) {
				if(i == 0) {
					var rs = stmt.getResultSet();
					
					while (rs.next()) {
						var name = rs.getString("Nome");
						var image = rs.getString("Immagine");
				        map.setName(name);
				        map.setImagePath(image);
				      }
				}
				else if (i == 1){
					var rs = stmt.getResultSet();
					
					while (rs.next()) {
				        var name = rs.getString("Nome");
				        double startX = rs.getFloat("StartX");
				        double startY = rs.getFloat("StartY");
				        double endX = rs.getFloat("EndX");
				        double endY = rs.getFloat("EndY");
				        var shape = rs.getString("Shape");
				        
				        var zone = new Zone();
				        zone.setName(name);
				        zone.setStartX(startX);
				        zone.setStartY(startY);
				        zone.setEndX(endX);
				        zone.setEndY(endY);
				        switch(shape){
				        	case "RECT": zone.setType(Type.RECTANGLE);
				        		break;
				        	case "OVAL": zone.setType(Type.OVAL);
				        		break;
				        	default: zone.setType(Type.RECTANGLE);
				        }
				        
				        zones.add(zone);
				      }
				
				}
				haveResult = stmt.getMoreResults();
				i++;
			}
			map.setZones(zones);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public int saveMap(String username, Map map) {
		var conn = Database.getConnection();
		var id = 0;
		try(CallableStatement stmt = conn.prepareCall("call save_map(?, ?, ?, ?);");) {
			
			//Input Param
			if(map.getId() != 0) {
				stmt.setInt(1, map.getId());
			}
				
			stmt.setString(2, username);
			stmt.setString(3, map.getName());
			stmt.setString(4, map.getImagePath());
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
			//Output Param
			id = stmt.getInt(1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		if(map.getZones() != null) {
			List<Zone> zones = map.getZones();
			for(Zone zone : zones) {
				zone.setName(zone.getName());
				addZoneToMap(zone, id);
			}
		}
		return id;
	}
	
	private void addZoneToMap(Zone zone, int idMap) {
		var conn = Database.getConnection();
		try(CallableStatement stmt = conn.prepareCall("call add_zone_to_map(?, ?, ?, ?, ?, ?, ?);");) {
			
			//Input Param
			stmt.setString(1, zone.getName());
			stmt.setInt(2, idMap);
			stmt.setFloat(3, (float) zone.getStartX());
			stmt.setFloat(4, (float) zone.getStartY());
			stmt.setFloat(5, (float) zone.getEndX());
			stmt.setFloat(6, (float) zone.getEndY());
			var shape = 0;
			switch(zone.getType()) {
				case RECTANGLE: shape = 1;
					break;
				case OVAL: shape = 2;
					break;
				default: shape = 1;
			}
			stmt.setInt(7, shape);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Map> getMapList(String username){
		var conn = Database.getConnection();
		
		List<Map> maps = new ArrayList<>();
		try(var stmt = conn.prepareCall("call get_maps(?);")) {
			
			//Input Param
			stmt.setString(1, username);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				
				var rs = stmt.getResultSet();
				while (rs.next()) {
					
					var id = rs.getInt("IdMap");
			        var nomeMappa = rs.getString("Nome");
			        var pathImmagine = rs.getString("Immagine");
			        
			        var map = new Map(id);
			        map.setName(nomeMappa);
			        map.setImagePath(pathImmagine);
			        
			        maps.add(map);
			      }
				haveResult = stmt.getMoreResults();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return maps;
	}
	
	public void deleteMap(int id, String username) {
		var conn = Database.getConnection();
		
		try(CallableStatement stmt = conn.prepareCall("call delete_map_by_id(?, ?);")) {
			
			//Input Param
			stmt.setInt(1, id);
			stmt.setString(2, username);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
