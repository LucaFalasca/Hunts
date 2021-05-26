package logic.model.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.model.entity.Hunt;
import logic.model.entity.Object;
import logic.model.entity.Zone;
import logic.model.Database;

public class ObjectDao {
	
	public void addObject(Object object, Hunt hunt, Zone zone) {
		
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		try {
			
			stmt = conn.prepareCall("call addObject(?,?,?,?,?);");
			
			stmt.setString(1, object.getName());
			stmt.setString(2, object.getPath());
			stmt.setString(3, object.getDescription());
			stmt.setInt(4, hunt.getIdHunt());
			stmt.setString(5, zone.getName());
				
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
	
	
	public Object getObjectByName(Object object, int idHunt) {
		
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		try {
			
			
			stmt = conn.prepareCall("call login(?,?,?);");
			
			//Input Param
			stmt.setString(1, object.getName());
			stmt.setInt(2, idHunt);
			
			boolean haveResult = stmt.execute();
			
			
			while(haveResult) {
				ResultSet rs = stmt.getResultSet();
				
				while (rs.next()) {
					object.setDescription(rs.getString("Descrizione"));
					object.setPath(rs.getString("Immagine"));
			    }
				
			}
				
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				
				if(stmt != null)
					stmt.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		return object;
	}


	public List<Integer> getObjectInRiddle(Object object, Hunt hunt) {
		Connection conn = Database.getConnection();
		CallableStatement stmt = null;
		
		List<Integer> riddleIndex = new ArrayList<>();
		
		try {
			
			stmt = conn.prepareCall("call addObject(?,?,?);");
			
			stmt.setInt(1, hunt.getIdHunt());
			stmt.setString(2, object.getName());
			
			boolean result = stmt.execute();
			
			if(result) {
				ResultSet rs = stmt.getResultSet();
				
				while (rs.next()) {
					riddleIndex.add(rs.findColumn("Numero"));
				}
			}
				
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
		
		return riddleIndex;
	}
}
