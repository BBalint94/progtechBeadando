package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.records.Book;
import models.records.BookBuilder;
import models.records.Order;

public class OrderModel implements IEntityModel<Order>{

	private static OrderModel instance;
	private DatabaseManager dbManager = DatabaseManager.getInstance();
	private OrderModel() {}
	
	public static OrderModel getInstance() {
		if(instance == null) {
			instance = new OrderModel();
		}
		return instance;
	}
	
	@Override
	public List<Order> fetchMultiple() {
		List<Order> orders = new ArrayList<>();
		try {			
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
	        String sql = "SELECT DISTINCT orders.id, user.name, orders.way, orders.sum_price FROM orders "
	        		+ "INNER JOIN basket ON orders.id = basket.order_id INNER JOIN user ON basket.user_id = user.id";		
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	           Order order = new Order();
	           order.setId(rs.getInt("orders.id"));
	           order.setCustomer(rs.getString("user.name"));
	           order.setWay(rs.getString("orders.way"));
	           order.setSumPrice(rs.getInt("orders.sum_price"));	          
	           orders.add(order);
	        }
	        dbManager.getConnection().close();	        			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return orders;		
	}

	@Override
	public Order fetch(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean persist(Order entity) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "INSERT INTO orders (way, sent, sum_price) VALUES ('"+entity.getWay()+"', '0', '"+entity.getSumPrice()+"')";
			stmt.execute(sql);				
			stmt.close();			
		}catch(SQLException ex) {
			System.err.println(ex);
		}		
		return true;
	}

	@Override
	public boolean delete(int id) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "DELETE FROM orders WHERE id = '"+id+"'";
			stmt.execute(sql);
			stmt.close();
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return true;
	}
	
	public int getLastOrderId() {
		int id = 0;
		try {			
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
	        String sql = "SELECT id FROM orders ORDER BY id DESC LIMIT 1";
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	           id = rs.getInt("id");
	        }
	        dbManager.getConnection().close();	        			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return id;		
	}

}
