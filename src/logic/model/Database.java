package logic.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import logic.exception.NotConnectedException;

public class Database {
	
	private static Connection conn;
	private static Users user;
	
	private Database() {
		
	}

	public static Connection getConnection() {
		if(conn == null) {
			conn = connect(Users.NOT_LOGGED);
		}
		return conn;
	}
	
	public static void changeDatabaseUser(Users user) throws NotConnectedException {
		if(conn == null) {
			throw new NotConnectedException();
		}
		if(Database.user != user) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = connect(user);
		}
	}
	
	private static Connection connect(Users user) {
		File fileConfig = new File("db_conf.txt");
		try (Scanner myReader = new Scanner(fileConfig)){
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
			String conf[] = new String[4];
			int i = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				conf[i] = data;
				i++;
			}
			Class.forName(conf[1]);
			conn = DriverManager.getConnection(conf[0], conf[2], conf[3]);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
		
	}
}
