package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import logic.model.Database;
import logic.model.entity.Hunt;
import logic.model.entity.PlayedHunt;

public class PlayHuntDao {

	public List<PlayedHunt> getPlayedHunt(String username) {
		var conn = Database.getConnection();
		List<PlayedHunt> hunts = new ArrayList<>();
		
		
		try (CallableStatement stmt = conn.prepareCall("call get_played_hunt(?);")){
			
			//Input param
			stmt.setString(1, username);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				
				var rs = stmt.getResultSet();
				while (rs.next()) {
					
					var id = rs.getInt(1);
					var nameHunt = rs.getString(2);
					var creatorName = rs.getString(3);
					var finished = rs.getBoolean(4);
					var rating = rs.getDouble(5);
			        
					var hunt = new Hunt();
					
			        hunt.setIdHunt(id);
			        hunt.setCreatorName(creatorName);
			        hunt.setHuntName(nameHunt);
			        
			        var playedHunt = new PlayedHunt();
			        
			        playedHunt.setHunt(hunt);
			        playedHunt.setFinished(finished);
			        playedHunt.setRating(rating);
			        
			        hunts.add(playedHunt);
			      }
				haveResult = stmt.getMoreResults();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return hunts;
		
	}
	
	public void setHuntAsPlayed(int idHunt, String player, LocalDate date) {
		var conn = Database.getConnection();
		
		try (CallableStatement stmt = conn.prepareCall("call set_hunt_as_played(?, ?, ?, ?);")){
			
			//Input param
			stmt.setInt(1, idHunt);
			stmt.setString(2, player);
			stmt.setBoolean(3, false);
			stmt.setDate(4, Date.valueOf(date));
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				var rs = stmt.getResultSet();
				while (rs.next()) {
			      }
				haveResult = stmt.getMoreResults();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setHuntAsFinished(int idHunt, String player) {
		var conn = Database.getConnection();
		
		try (CallableStatement stmt = conn.prepareCall("call set_hunt_as_played(?, ?, ?, ?);")){
			
			//Input param
			stmt.setInt(1, idHunt);
			stmt.setString(2, player);
			stmt.setBoolean(3, true);
			stmt.setNull(4, java.sql.Types.DATE);
			
			boolean haveResult = stmt.execute();
			
			while(haveResult) {
				var rs = stmt.getResultSet();
				while (rs.next()) {
			      }
				haveResult = stmt.getMoreResults();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
