package models.records;

import java.util.List;

public class Basket {	
	
	private List<Book> books;
	private int ownerId;
	
	public Basket(List<Book> books) {
		this.books = books;
	}
	
	public Basket() {
		this.books = null;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}		
	
}
