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
	
	/*
	public Hunt getHuntById(int id, String username) {
		
	}
	*/
	
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
