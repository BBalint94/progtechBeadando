package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.records.Basket;
import models.records.Book;
import models.records.BookBuilder;

public class BasketModel  implements IEntityModel<Basket>{
	
	private DatabaseManager dbManager = DatabaseManager.getInstance();
	private static BasketModel instance;
	private BasketModel() {}
	
	public static BasketModel getInstance() {
		if(instance == null) {
			instance = new BasketModel();
		}
		return instance;
	}

	@Override
	public List<Basket> fetchMultiple() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Basket fetch(int id) {
		Basket basket = new Basket();
		List<Book> bookList = new ArrayList<>();
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
	        String sql = "SELECT basket.id, basket.book_id, book.author, book.title, book.price FROM basket "
	        		+ "INNER JOIN book ON basket.book_id = book.id WHERE basket.user_id = '"+id+"' AND basket.order_id IS NULL";
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	            BookBuilder builder = new BookBuilder();
	            Book b = builder.setId(rs.getInt("basket.book_id"))
	            		.setAuthor(rs.getString("book.author"))
	            		.setTitle(rs.getString("book.title"))  	            		
	            		.setPrice(rs.getInt("book.price"))
	            		.setInstanceId(rs.getInt("basket.id"))	            		
	            		.build();
	            bookList.add(b);
	        }
	        dbManager.getConnection().close();
	        basket.setBooks(bookList);
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return basket;
	}

	@Override
	public boolean persist(Basket entity) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "INSERT INTO basket (user_id, book_id) "
					+ "VALUES('"+entity.getOwnerId()+"','"+entity.getBooks().get(0).getId()+"')";
			stmt.execute(sql);				
			stmt.close();			
		}catch(SQLException ex) {
			System.err.println(ex);
		}		
		return true;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}
	
	public boolean remove(int instanceId, int customerId) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "DELETE FROM basket WHERE id = '"+instanceId+"' AND user_id = '"+customerId+"'";
			stmt.execute(sql);				
			stmt.close();			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return true;
	}
	
	public boolean setOrder(int customerId, int orderId) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "UPDATE basket SET order_id = '"+orderId+"' WHERE user_id = '"+customerId+"' AND order_id IS NULL";					
			stmt.execute(sql);				
			stmt.close();			
		}catch(SQLException ex) {
			System.err.println(ex);
		}		
		return true;
	}
	
	public Map<Integer, Integer> bookCount(Map<Integer,Integer> bookCount, int orderId) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "SELECT COUNT(book_id) AS bcount, book_id FROM basket WHERE order_id = '"+orderId+"' GROUP BY book_id";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				bookCount.put(rs.getInt("book_id"), rs.getInt("bcount"));
			}
			dbManager.getConnection().close();			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return bookCount;
	}
	
	public boolean containsThisBook(int bookId) {
		int count = 0;
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "SELECT COUNT(book_id) AS bcount FROM basket WHERE book_id = '"+bookId+"'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {				
				count = rs.getInt("bcount");
			}
			dbManager.getConnection().close();
			if(count > 0) {
				return true;
			}
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return false;
	}

}
