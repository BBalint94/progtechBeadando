package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import models.records.User;
import models.records.UserBuilder;

public class UserModel  implements IEntityModel<User>{	
	private DatabaseManager dbManager = DatabaseManager.getInstance();
	private static UserModel instance;
	private UserModel() {}
	
	public synchronized static UserModel getInstance() {
		if(instance == null) {
			instance = new UserModel();
		}
		return instance;
	}	

	@Override
	public List<User> fetchMultiple() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User fetch(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public User findUser(String email, String password) {
		User u = null;
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql ="SELECT id, name, email, permission FROM user WHERE email = '" + email +"'";
			if(password != null && !"".equals(password)) {
				sql += " AND password = '"+ password +"' ";
			}
			ResultSet rs = stmt.executeQuery(sql);
			UserBuilder builder = new UserBuilder();			
			while(rs.next()) {				
				u = builder.setId(rs.getInt("id")).setName(rs.getString("name"))
						.setEmail(rs.getString("email")).setPermission(rs.getByte("permission")).build();
			}
			dbManager.getConnection().close();
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return u;
	}

	@Override
	public boolean persist(User entity) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "INSERT INTO user (name, email, password, birth_date, address) "
					+ "VALUES('"+entity.getName()+"','"+entity.getEmail()+"','"+entity.getPassword()+"','"+entity.getBirthDate()+"','"+entity.getAddress()+"')";
			stmt.execute(sql);				
			stmt.close();			
		}catch(SQLException ex) {
			System.err.println(ex);
		}		
		return true;		
	}

	@Override
	public boolean delete(int id) {
		//TODO Auto-generated method stub
		return false;
		
	}
}
