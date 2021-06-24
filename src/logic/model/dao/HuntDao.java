package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.model.Database;
import logic.model.entity.Hunt;
import logic.model.entity.RealObject;
import logic.model.entity.Riddle;

public class HuntDao {
	
	public int saveHunt(Hunt hunt) {
		int idHunt  = addHunt(hunt.getIdHunt(), hunt.getHuntName(), hunt.getCreatorName(), true, -1);
		
		List<RealObject> objects = hunt.getObjectList();
		if(objects != null) {
			for(RealObject object : objects) {
				addObjectToHunt(object.getName(), idHunt, null, object.getDescription(), null, -1);
			}
		}
		
		List<Riddle> riddles = hunt.getRiddleList();
		for(Riddle riddle: riddles) {
			int numeroRiddle = addRiddleToHunt(idHunt, riddle.getRiddleText(), riddle.getSolutionText(), riddle.getReward(), null, null, -1);
			List<String> clues = riddle.getClueList();
			for(String clue : clues) {
				addClueToRiddle(numeroRiddle, idHunt, clue);
			}
		}
		
		return idHunt;
	}
	
	
	public Hunt getHuntById(int id, String username) {
		var conn = Database.getConnection();
		var hunt = new Hunt();
		
		int mapId = -1;
		List<RealObject> objects = new ArrayList<>();
		List<Riddle> riddles = new ArrayList<>();
		List<Integer> riddleNumbers = new ArrayList<>();
		try(CallableStatement stmt = conn.prepareCall("call get_hunt_by_id(?, ?);");) {
			
			//Input Param
			stmt.setInt(1, id);
			stmt.setString(2, username);
			
			boolean haveResult = stmt.execute();
			var i = 1;
			
			
			while(haveResult) {
				var rs = stmt.getResultSet();
				
				switch(i) {
				case 1:
					while(rs.next()) {
						var name = rs.getString(1);
						mapId = rs.getInt(3);
						hunt.setHuntName(name);
					}
					break;
				case 2:
					while(rs.next()) {
						var name = rs.getString(1);
						var pathImage = rs.getString(2);
						var description = rs.getString(3);
						
						var ob = new RealObject();
						ob.setName(name);
						ob.setPath(pathImage);
						ob.setDescription(description);
						
						objects.add(ob);
					}
					break;
				case 3:
					while(rs.next()) {
						var number = rs.getInt(1);
						var riddleText = rs.getString(2);
						var riddleSolution = rs.getString(3);
						var reward = rs.getString(4);
						var zone = rs.getString(6);
						
						var riddle = new Riddle();
						riddle.setRiddleText(riddleText);
						riddle.setSolutionText(riddleSolution);
						riddle.setReward(reward);
						riddle.setZone(zone);
						riddleNumbers.add(number);
						riddles.add(riddle);
					}
					break;
					default:
						
				}
				
				haveResult = stmt.getMoreResults();
				i++;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for(var i = 0; i < riddleNumbers.size(); i++) {
			riddles.get(i).setClueList(getClueByRiddle(riddleNumbers.get(i), hunt.getIdHunt(), username));
		}
		
		if(mapId != -1) {
			var mapDao = new MapDao();
			var map = mapDao.getMapById(username, mapId);
			hunt.setMap(map);
		}
		
		hunt.setObjectList(objects);
		hunt.setRiddleList(riddles);
		
		
		return hunt;
	}
	
	
	private List<String> getClueByRiddle(int riddle, int hunt, String username){
		var conn = Database.getConnection();
		List<String> clues = new ArrayList<>();
		
		
		try (CallableStatement stmt = conn.prepareCall("call get_clue_by_riddle(?, ?, ?)")){
			
			//Input param
			stmt.setInt(1, riddle);
			stmt.setInt(2, hunt);
			stmt.setString(3, username);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
				var rs = stmt.getResultSet();
				while(rs.next()) {
					var testo = rs.getString(2);
					
					clues.add(testo);
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return clues;
	}
	
	public List<Hunt> getHuntList(String username){
		var conn = Database.getConnection();
		
		List<Hunt> hunts = new ArrayList<>();
		try(CallableStatement stmt = conn.prepareCall("call get_hunts(?);")) {
			
			//Input Param
			stmt.setString(1, username);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				
				var rs = stmt.getResultSet();
				while (rs.next()) {
					
					var id = rs.getInt(1);
			        var huntName = rs.getString(2);
			        var idMap = rs.getInt(4);
			        
			        var hunt = new Hunt();
			        hunt.setIdHunt(id);
			        hunt.setHuntName(huntName);
			        
			        hunts.add(hunt);
			      }
				haveResult = stmt.getMoreResults();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return hunts;
	}
	
	public List<Hunt> getHuntList(){
		var conn = Database.getConnection();
		
		List<Hunt> hunts = new ArrayList<>();
		try(CallableStatement stmt = conn.prepareCall("call get_all_hunts();")) {
			
			//Input Param
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				
				var rs = stmt.getResultSet();
				while (rs.next()) {
					
					var id = rs.getInt(1);
					var creatorName = rs.getString(2);
					var nameHunt = rs.getString(3);
					var idMap = rs.getInt(5);
			        
					var hunt = new Hunt();
					
			        hunt.setIdHunt(id);
			        hunt.setCreatorName(creatorName);
			        hunt.setHuntName(nameHunt);
			        
			        hunts.add(hunt);
			      }
				haveResult = stmt.getMoreResults();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return hunts;
	}
	
	private int addHunt(int codice, String nome, String username, boolean indoor, int idMap) {
		var conn = Database.getConnection();
		var id = 0;
		
		try (CallableStatement stmt = conn.prepareCall("call save_hunt(?, ?, ?, ?, ?)")){
			
			//Input param
			if(codice != -1)
				stmt.setInt(1, codice);
			stmt.setString(2, nome);
			stmt.setString(3, username);
			stmt.setBoolean(4, indoor);
			if(idMap != -1)
				stmt.setInt(5, idMap);
			else
				stmt.setNull(5, java.sql.Types.INTEGER);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
			//Output param
			id = stmt.getInt(1);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	private void addObjectToHunt(String nome, int hunt, String immagine, String descrizione, String nomeZona, int mappaZona) {
		var conn = Database.getConnection();
		
		try (CallableStatement stmt = conn.prepareCall("call add_object_to_hunt(?, ?, ?, ?, ?, ?)")){
			
			//Input param
			stmt.setString(1, nome);
			stmt.setInt(2, hunt);
			stmt.setString(3, immagine);
			stmt.setString(4, descrizione);
			if(!(nomeZona == null || mappaZona == -1)) {
				stmt.setString(5, nomeZona);
				stmt.setInt(6, mappaZona);
			}
			else {
				stmt.setNull(5, java.sql.Types.VARCHAR);
				stmt.setNull(6, java.sql.Types.INTEGER);
			}
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private int addRiddleToHunt(int hunt, String domanda, String risposta, String objectPremio, String objectRisposta, String nomeZona, int mappaZona) {
		var conn = Database.getConnection();
		var idRiddle = 0;
		
		try (CallableStatement stmt = conn.prepareCall("call add_riddle_to_hunt(?, ?, ?, ?, ?, ?, ?, ?)")){
			
			//Input param
			stmt.setInt(1, hunt);
			stmt.setString(2, domanda);
			stmt.setString(3, risposta);
			stmt.setString(4, objectPremio);
			stmt.setString(5, objectRisposta);
			if(!(nomeZona == null || mappaZona == -1)) {
				stmt.setString(6, nomeZona);
				stmt.setInt(7, mappaZona);
			}
			else {
				stmt.setNull(6, java.sql.Types.VARCHAR);
				stmt.setNull(7, java.sql.Types.INTEGER);
			}
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
			idRiddle = stmt.getInt(8);
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return idRiddle;
	}

	private void addClueToRiddle(int riddle, int hunt, String testo) {
		var conn = Database.getConnection();
		
		try (CallableStatement stmt = conn.prepareCall("call add_clue_to_riddle(?, ?, ?)")){
			
			//Input param
			stmt.setInt(1, riddle);
			stmt.setInt(2, hunt);
			stmt.setString(3, testo);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeHunt(Hunt hunt) {
		var conn = Database.getConnection();
		
		try (CallableStatement stmt = conn.prepareCall("call delete_hunt_by_id(?, ?)")){
			
			//Input param
			stmt.setInt(1, hunt.getIdHunt());
			stmt.setString(2, hunt.getCreatorName());
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
