package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.enumeration.Type;
import logic.model.Database;
import logic.model.entity.Hunt;
import logic.model.entity.Map;
import logic.model.entity.Object;
import logic.model.entity.Riddle;
import logic.model.entity.Zone;

public class HuntDao {
	
	public int saveHunt(Hunt hunt, String username) {
		int idHunt  = addHunt(hunt.getIdHunt(), hunt.getHuntName(), username, true, -1);
		
		List<Object> objects = hunt.getObjectList();
		for(Object object : objects) {
			addObjectToHunt(object.getName(), idHunt, null, object.getDescription(), null, -1);
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
		Connection conn = Database.getConnection();
		Hunt hunt = new Hunt();
		
		int mapId = -1;
		List<Object> objects = new ArrayList<>();
		List<Riddle> riddles = new ArrayList<>();
		List<Integer> riddleNumbers = new ArrayList<>();
		try(CallableStatement stmt = conn.prepareCall("call get_hunt_by_id(?, ?);");) {
			
			//Input Param
			stmt.setInt(1, id);
			stmt.setString(2, username);
			
			boolean haveResult = stmt.execute();
			int i = 1;
			
			
			while(haveResult) {
				ResultSet rs = stmt.getResultSet();
				
				switch(i) {
				case 1:
					while(rs.next()) {
						String nome = rs.getString(1);
						boolean indoor = rs.getBoolean(2);
						mapId = rs.getInt(3);
						hunt.setHuntName(nome);
						//hunt.setIndoor(indoor);
					}
					break;
				case 2:
					while(rs.next()) {
						String nome = rs.getString(1);
						String pathImage = rs.getString(2);
						String descrizione = rs.getString(3);
						String zona = rs.getString(4);
						
						Object ob = new Object();
						ob.setName(nome);
						ob.setPath(pathImage);
						ob.setDescription(descrizione);
						//ob.setZone(zona);
						
						objects.add(ob);
					}
					break;
				case 3:
					while(rs.next()) {
						int numero = rs.getInt(1);
						String domanda = rs.getString(2);
						String risposta = rs.getString(3);
						String oggettoPremio = rs.getString(4);
						String oggettoRisposta = rs.getString(5);
						String zona = rs.getString(6);
						
						Riddle riddle = new Riddle();
						riddle.setRiddleText(domanda);
						riddle.setSolutionText(risposta);
						riddle.setReward(oggettoPremio);
						//riddle.setZone(zona);
						//riddle.setSolutionObject(oggettoRisposta);
						riddleNumbers.add(numero);
						riddles.add(riddle);
					}
					break;
					default:
						
				}
				
				haveResult = stmt.getMoreResults();
				i++;
			}
			stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i < riddleNumbers.size(); i++) {
			riddles.get(i).setClueList(getClueByRiddle(riddleNumbers.get(i), hunt.getIdHunt(), username));
		}
		
		if(mapId != -1) {
			MapDao mapDao = new MapDao();
			Map map = mapDao.getMapById(username, mapId);
			//hunt.setMap(map);
		}
		
		hunt.setObjectList(objects);
		hunt.setRiddleList(riddles);
		
		
		return hunt;
	}
	
	
	private List<String> getClueByRiddle(int riddle, int hunt, String username){
		Connection conn = Database.getConnection();
		List<String> clues = new ArrayList<>();
		
		
		try (CallableStatement stmt = conn.prepareCall("call get_clue_by_riddle(?, ?, ?)")){
			
			//Input param
			stmt.setInt(1, riddle);
			stmt.setInt(2, hunt);
			stmt.setString(3, username);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				haveResult = stmt.getMoreResults();
				ResultSet rs = stmt.getResultSet();
				while(rs.next()) {
					int numero = rs.getInt(1);
					String testo = rs.getString(2);
					
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
		Connection conn = Database.getConnection();
		
		List<Hunt> hunts = new ArrayList<>();
		try(CallableStatement stmt = conn.prepareCall("call get_hunts(?);")) {
			
			//Input Param
			stmt.setString(1, username);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					
					int codice = rs.getInt(1);
			        String nomeHunt = rs.getString(2);
			        boolean indoor = rs.getBoolean(3);
			        int idMap = rs.getInt(4);
			        
			        Hunt hunt = new Hunt();
			        hunt.setIdHunt(codice);
			        hunt.setHuntName(nomeHunt);
			        //hunt.setIndoor(indoor);
			        
			        hunts.add(hunt);
			      }
				haveResult = stmt.getMoreResults();
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return hunts;
	}
	
	public List<Hunt> getHuntList(){
		Connection conn = Database.getConnection();
		
		List<Hunt> hunts = new ArrayList<>();
		try(CallableStatement stmt = conn.prepareCall("call get_all_hunts();")) {
			
			//Input Param
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				
				ResultSet rs = stmt.getResultSet();
				while (rs.next()) {
					
					int codice = rs.getInt(1);
					String creatore_hunt = rs.getString(2);
			        String nomeHunt = rs.getString(3);
			        boolean indoor = rs.getBoolean(4);
			        int idMap = rs.getInt(5);
			        
			        Hunt hunt = new Hunt();
			        hunt.setIdHunt(codice);
			        hunt.setHuntName(nomeHunt);
			        //hunt.setIndoor(indoor);
			        
			        hunts.add(hunt);
			      }
				haveResult = stmt.getMoreResults();
			}
			stmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return hunts;
	}
	
	private int addHunt(int codice, String nome, String username, boolean indoor, int idMap) {
		Connection conn = Database.getConnection();
		int id = 0;
		
		try (CallableStatement stmt = conn.prepareCall("call save_hunt(?, ?, ?, ?, ?)")){
			
			//Input param
			if(codice != -1)
				stmt.setInt(1, codice);
			stmt.setString(2, nome);
			stmt.setString(3, username);
			stmt.setBoolean(4, indoor);
			if(idMap != -1)
				stmt.setInt(5, idMap);
			
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
		Connection conn = Database.getConnection();
		
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
		Connection conn = Database.getConnection();
		int idRiddle = 0;
		
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
		Connection conn = Database.getConnection();
		
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
}
