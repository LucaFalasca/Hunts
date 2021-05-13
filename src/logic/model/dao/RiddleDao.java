package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.model.Database;
import logic.model.entity.Clue;
import logic.model.entity.Hunt;
import logic.model.entity.Object;
import logic.model.entity.Riddle;
import logic.model.entity.Zone;

public class RiddleDao {
	
	int res;
	
	public void addRiddle(Riddle riddle, Object object, Hunt hunt, Zone zone) {
		
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		try {
			
			stmt = conn.prepareCall("call nomeRoutine(?,?,?,?,?,?,?);");
			
			stmt.setInt(1, riddle.getnRiddle());
			stmt.setInt(2, hunt.getIdHunt());
			stmt.setString(3, riddle.getRiddleText());
			stmt.setString(4, riddle.getSolutionText());
			stmt.setString(5, object.getName());
			stmt.setString(6, null);
			stmt.setString(7, zone.getName());
			
			stmt.execute();
			
				
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteRiddleById(Hunt hunt, Riddle riddle) {
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		try {
			stmt = conn.prepareCall("call nomeRoutine(?,?);");
			
			stmt.setInt(1, riddle.getnRiddle());
			stmt.setInt(2, hunt.getIdHunt());
			
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		try {
			if(stmt != null)
				stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	}
	
	public Riddle getRiddleById(Hunt hunt, Riddle riddle) {
		
		List<String> clue = new ArrayList<>();
		
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		boolean result = false;
		
		try {
			stmt = conn.prepareCall("call nomeRoutine(?,?);");
			
			stmt.setInt(1, riddle.getnRiddle());
			stmt.setInt(2, hunt.getIdHunt());
			
			result = stmt.execute();
			
			if(result) {
				ResultSet rs = stmt.getResultSet();
				
				while (rs.next()) {
					riddle.setRiddleText("Domanda");
					riddle.setSolutionText("Risposta");
					riddle.setReward("Premio");
					for(int i = 0; i < riddle.getClueList().size(); i++)
						riddle.setClueListElement(i, "Testo");
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return riddle;
	}

	public void addClueToRiddle(Clue clue, Hunt hunt, Riddle riddle) {
		
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		try {
			
			stmt = conn.prepareCall("call nomeRoutine(?,?,?,?);");
			
			stmt.setInt(1, clue.getClueIndex());
			stmt.setInt(2, riddle.getnRiddle());
			stmt.setInt(3, hunt.getIdHunt());
			stmt.setString(4, clue.getText());
			
			stmt.execute();
			
				
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}