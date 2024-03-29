package logic.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import logic.exception.NotConnectedException;
import logic.exception.DatabaseException;

public class Database {
	
	private static Connection conn;
	private static Users user;
	
	private Database() {
		
	}

	public static Connection getConnection() throws DatabaseException {
		if(conn == null) {
			conn = connect(Users.NOT_LOGGED);
		}
		return conn;
	}
	
	public static void changeDatabaseUser(Users user) throws NotConnectedException, DatabaseException {
		if(conn == null) {
			throw new NotConnectedException();
		}
		if(Database.user != user) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DatabaseException();
			}
			conn = connect(user);
		}
	}
	
	private static Connection connect(Users user) throws DatabaseException {
		URL url = Database.class.getResource("/db_conf.txt");
		
		File fileConfig = new File(url.getPath());
		try (var myReader = new Scanner(fileConfig)){
			switch(user) {
				case NOT_LOGGED:
					Database.user = Users.NOT_LOGGED;
					break;
				case USER:
					Database.user = Users.USER;
					break;
				default:
					Database.user = Users.NOT_LOGGED;
			}
			var conf = new String[4];
			var i = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				conf[i] = data;
				i++;
			}
			Class.forName(conf[1]);
			conn = DriverManager.getConnection(conf[0], conf[2], conf[3]);
		} catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
			throw new DatabaseException();
		} 
		return conn;
		
	}
}
