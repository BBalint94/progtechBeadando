package models.records;

import java.util.Date;

public class BookBuilder {
	private Book book;
	
	public Book build() {
		return book;
	}
	
	public BookBuilder() {
		this.book = new Book();
	}
	
	public BookBuilder setId(int id) {
		this.book.setId(id);
		return this;
	}
	
	public BookBuilder setAuthor(String author) {
		this.book.setAuthor(author);
		return this;
	}
	
	public BookBuilder setTitle(String title) {
		this.book.setTitle(title);
		return this;
	}
	
	public BookBuilder setPages(int pages) {
		this.book.setPages(pages);
		return this;
	}
	
	public BookBuilder setReleasedOn(Date releasedOn) {
		this.book.setReleasedOn(releasedOn);
		return this;
	}
	
	public BookBuilder setGenre(String genre) {
		this.book.setGenre(genre);
		return this;
	}
	
	public BookBuilder setPrice(int price) {
		this.book.setPrice(price);
		return this;
	}
	
	public BookBuilder setQuantity(int quantity) {
		this.book.setQuantity(quantity);
		return this;
	}
	
	public BookBuilder setInstanceId(int instanceId) {
		this.book.setInstanceId(instanceId);
		return this;
	}
}
