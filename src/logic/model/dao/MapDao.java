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
	
	public Map getMapByName(String username, String nameMap) {
		Connection conn = Database.getConnection();
		
		Map map = new Map(nameMap);
		try(CallableStatement stmt = conn.prepareCall("call get_map_by_name(?, ?);");) {
			
			//Input Param
			stmt.setString(1, username);
			stmt.setString(2, nameMap);
			
			boolean haveResult = stmt.execute();
			int i = 0;
			
			List<Zone> zones = new ArrayList<Zone>();
			while(haveResult) {
				if(i == 0) {
					ResultSet rs = stmt.getResultSet();
					
					while (rs.next()) {
				        String image = rs.getString("Immagine");
				        map.setImagePath(image);
				      }
				}
				else if (i == 1){
					ResultSet rs = stmt.getResultSet();
					
					while (rs.next()) {
				        String name = rs.getString("Nome");
				        double startX = rs.getInt("StartX");
				        double startY = rs.getInt("StartY");
				        double endX = rs.getInt("EndX");
				        double endY = rs.getInt("EndY");
				        String shape = rs.getString("Shape");
				        
				        Zone zone = new Zone();
				        zone.setName(name);
				        zone.setStartX(startX);
				        zone.setStartY(startY);
				        zone.setEndX(endX);
				        zone.setEndY(endY);
				        switch(shape){
				        	case "RECT": zone.setType(Type.RECTANGLE);
				        	break;
				        	case "OVAL": zone.setType(Type.OVAL);
				        }
				        
				        zones.add(zone);
				      }
				
				}
				haveResult = stmt.getMoreResults();
				i++;
			}
			map.setZones(zones);
			stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public void saveMap(String username, Map map) {
		Connection conn = Database.getConnection();
		try(CallableStatement stmt = conn.prepareCall("call save_map(?, ?, ?);");) {
			
			//Input Param
			stmt.setString(1, username);
			stmt.setString(2, map.getName());
			stmt.setString(3, map.getImagePath());
			
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
				try(CallableStatement stmt = conn.prepareCall("call add_zone_to_map(?, ?, ?, ?, ?, ?, ?, ?);");) {
					
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
		
		List<Map> maps = new ArrayList<Map>();
		try(CallableStatement stmt = conn.prepareCall("call get_maps(?);")) {
			
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
