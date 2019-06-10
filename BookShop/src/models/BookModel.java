package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.records.Book;
import models.records.BookBuilder;
import models.records.Order;

public class BookModel  implements IEntityModel<Book>{
	private DatabaseManager dbManager = DatabaseManager.getInstance();
	private static BookModel instance;
	private BookModel() {}
	
	public static BookModel getInstance() {
		if(instance == null) {
			instance = new BookModel();
		}
		return instance;
	}

	@Override
	public List<Book> fetchMultiple() {
		List<Book> books = new ArrayList<>();
		try {			
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
	        String sql = "SELECT book.id, author, title, pages, released_on, genre.name, price, quantity FROM book INNER JOIN genre ON book.genre_id = genre.id";
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	            BookBuilder builder = new BookBuilder();
	            Book b = builder.setId(rs.getInt("id"))
	            		.setAuthor(rs.getString("author"))
	            		.setTitle(rs.getString("title"))
	            		.setPages(rs.getInt("pages"))
	            		.setReleasedOn(rs.getDate("released_on"))
	            		.setGenre(rs.getString("genre.name"))
	            		.setPrice(rs.getInt("price"))
	            		.setQuantity(rs.getInt("quantity"))
	            		.build();
	            books.add(b);
	        }
	        dbManager.getConnection().close();	        			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return books;		
	}

	@Override
	public Book fetch(int id) {
		Book book = null;
		try {			
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
	        String sql = "SELECT author, title, price, quantity FROM book WHERE id = '"+id+"'";	        			
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	          BookBuilder builder = new BookBuilder();
	          book = builder.setId(id)
	        		  .setAuthor(rs.getString("author"))
	        		  .setTitle(rs.getString("title"))
	        		  .setPrice(rs.getInt("price"))
	        		  .setQuantity(rs.getInt("quantity"))
	        		  .build();	          
	        }
	        dbManager.getConnection().close();	        			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return book;
	}

	@Override
	public boolean persist(Book entity) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "INSERT INTO book (author, title, pages, released_on, genre_id, price, quantity) "
					+ "VALUES('"+entity.getAuthor()+"','"+entity.getTitle()+"','"+entity.getPages()+"',"
							+ "'"+entity.getReleasedOn()+"','"+entity.getGenre()+"', '"+entity.getPrice()+"', '"+entity.getQuantity()+"')";
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
			String sql = "DELETE FROM book WHERE id = '"+id+"'";
			stmt.execute(sql);
			stmt.close();
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return true;		
	}
	
	public List<String> fetchGenres(){
		List<String> genres = new ArrayList<>();
		try {			
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
	        String sql = "SELECT name FROM genre";
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	            String g = rs.getString("name");
	            genres.add(g);
	        }
	        dbManager.getConnection().close();	        			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return genres;		
	}
	
	public List<Book> fetchWithOrder(int orderId){
		List<Book> books = new ArrayList<>();
		try {			
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
	        String sql = "SELECT book.author, book.title, book.price FROM book "
	        		+ "INNER JOIN basket ON book.id = basket.book_id WHERE basket.order_id = '"+orderId+"'";	        			
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	          BookBuilder builder = new BookBuilder();
	          Book book = builder.setAuthor(rs.getString("author")).setTitle(rs.getString("title")).setPrice(rs.getInt("price")).build();
	          books.add(book);
	        }
	        dbManager.getConnection().close();	        			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return books;		
	}
	
	public int fetchQuantity(int bookId) {
		int quantity = 0;
		try {			
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
	        String sql = "SELECT quantity FROM book WHERE id = '"+bookId+"'";
	        ResultSet rs = stmt.executeQuery(sql);
	        while(rs.next()){
	            quantity = rs.getInt("quantity");          
	        }
	        dbManager.getConnection().close();	        			
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return quantity;
	}
	
	public boolean decreaseQuantity(int bookId, int decreaseValue) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "UPDATE book SET quantity = quantity - '"+decreaseValue+"' WHERE id = '"+bookId+"'";
			stmt.execute(sql);				
			stmt.close();
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return true;
	}
	
	public boolean updateQuantity(int bookId, int added) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "UPDATE book SET quantity = quantity + '"+added+"' WHERE id = '"+bookId+"'";
			stmt.execute(sql);
			stmt.close();
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return true;
	}
	
	public boolean modifyPrice(int bookId, int newPrice) {
		try {
			dbManager.createConnection();
			Statement stmt = dbManager.getConnection().createStatement();
			String sql = "UPDATE book SET price = '"+newPrice+"' WHERE id = '"+bookId+"'";
			stmt.execute(sql);
			stmt.close();
		}catch(SQLException ex) {
			System.err.println(ex);
		}
		return true;
	}
}
