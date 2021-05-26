package logic.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import logic.exception.NotConnectedException;

public abstract class Database {

	private static Connection conn;
	private static Users user;
	private static String name = "root";
    private static String pass = "root";
    private static String dbUrl = "jdbc:mysql://localhost:3306/hunt_db";
    private static String driverClassName = "com.mysql.cj.jdbc.Driver";
	
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
		try {
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
			Class.forName(driverClassName);
			conn = DriverManager.getConnection(dbUrl, name, pass);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
		
	}
}
