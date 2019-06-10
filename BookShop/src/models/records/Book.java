package models.records;

import java.util.Date;

public class Book {
	private int id;
	private String author;
	private String title;
	private int pages;
	private Date releasedOn;
	private String genre;
	private int price;
	private int quantity;
	private int instanceId;
	
	public Book() {
		this.id = 0;
		this.author = "";
		this.title = "";
		this.pages = 0;
		this.releasedOn = null;
		this.genre = "";
		this.price = 0;
		this.quantity = 0;
		this.instanceId = 0;
	}
	
	public Book(int id, String author,String title, int pages, 
			Date releasedOn, String genre, int price, int quantity, int instanceId) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.pages = pages;
		this.releasedOn = releasedOn;
		this.genre = genre;
		this.price = price;
		this.quantity = quantity;
		this.instanceId = instanceId;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public Date getReleasedOn() {
		return releasedOn;
	}
	public void setReleasedOn(Date releasedOn) {
		this.releasedOn = releasedOn;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(int instanceId) {
		this.instanceId = instanceId;
	}
	
	
	
	
}
