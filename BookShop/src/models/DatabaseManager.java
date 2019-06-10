package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DatabaseManager {
	private static DatabaseManager instance;
	private Connection con;
	
	private DatabaseManager() {	}
	
	public synchronized static DatabaseManager getInstance(){
		if(instance == null) {
			instance = new DatabaseManager();
		}
		return instance;
	}
	
	public void createConnection() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bookshop?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", ""
			);
		}catch(ClassNotFoundException ex) {		
			System.err.println(ex.getMessage());
		}
	}
	
	public Connection getConnection() {
		return con;
	}

}
