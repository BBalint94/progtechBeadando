package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IObserver;
import interfaces.ISubject;
import models.BookModel;
import models.records.Book;
import models.records.BookBuilder;

public class BookController implements ISubject{

	private static BookController instance;
	private BookModel bookModel = BookModel.getInstance();
	private List<IObserver> clients = new ArrayList<>();
	private String event;
	private boolean state;
	private BookController() {}
	
	public static BookController getInstance() {
		if(instance == null) {
			instance = new BookController();
		}
		return instance;
	}
	
	public List<Book> getBooks(){		
		List<Book> blist = bookModel.fetchMultiple();
		return blist;
	}
	
	public List<String> getGenres(){
		List<String> genres = bookModel.fetchGenres();		
		return genres;
	}
	
	public boolean addBook(String author, String title, int pages, Date releasedOn, int genreId, int price, int quantity) {
		if(author.isEmpty() || author == null || title.isEmpty() || title == null) {
			notifyForEvent("Valamelyik mez� �res!", false);
			return false;
		}
		BookBuilder builder = new BookBuilder();
		Book book = builder.setAuthor(author).setTitle(title).setPages(pages).setReleasedOn(releasedOn)
				.setGenre(String.valueOf(genreId)).setPrice(price).setQuantity(quantity).build();
		if(bookModel.persist(book)) {				
			notifyForEvent(author+" : "+title+" - hozz�adva az adatb�zishoz", true);
			return true;
		}
		notifyForEvent(author+" : "+title+" - hozz�ad�sa az adatb�zishoz sikertelen", false);
		return false;
	}
	
	public List<Book> getWithOrder(int orderId){
		List<Book> books = bookModel.fetchWithOrder(orderId);
		return books;
	}
	
	public void decreaseQuantity(Map<Integer, Integer> bookCount) {
		for (Map.Entry<Integer, Integer> e : bookCount.entrySet()) {			
			if(bookModel.fetchQuantity(e.getKey()) > 0){				
				bookModel.decreaseQuantity(e.getKey(), e.getValue());
			}			
		}
	}
	
	public int getCurrentQuantity(int bookId) {
		int quantity = bookModel.fetchQuantity(bookId);
		return quantity;
	}
	
	public boolean updateQuantity(int bookId, int added) {
		if(added > 0) {
			if(bookModel.updateQuantity(bookId, added)) {
				notifyForEvent("A k�nyv mennyis�ge n�velve! (+ "+added+" db)", true);
				return true;
			}
			notifyForEvent("Nem siker�lt n�velni a k�nyv mennyis�g�t!", false);
			return false;
		}
		notifyForEvent("A mennyis�g csak pozit�v �rt�k lehet!", false);
		return false;
	}
	
	public boolean modifyPrice(int bookId, int newPrice) {
		if(newPrice > 0) {
			if(bookModel.modifyPrice(bookId, newPrice)) {
				notifyForEvent("K�nyv �ra m�dos�tva, �j �r: "+newPrice, true);
				return true;
			}
			notifyForEvent("K�nyv �r�nak m�dos�t�sa sikertelen!", false);
			return false;
		}
		notifyForEvent("A k�nyv �r�nak t�bbnek kell lennie 0 Ft-n�l!", false);
		return false;
	}
	
	public Book fetchBook(int bookId) {
		Book book = bookModel.fetch(bookId);
		return book;
	}
	
	public boolean deleteBook(int bookId) {
		if(bookModel.delete(bookId)) {
			notifyForEvent("A kijel�lt k�nyv t�r�lve!", true);
			return true;
		}
		notifyForEvent("Nem siker�lt t�r�lni a k�nyvet!", false);
		return false;
	}
	
	public void setEventState(String message, boolean state) {
		event = message;
		this.state = state;
	}

	@Override
	public void registerObserver(IObserver o) {
		clients.add(o);
		
	}

	@Override
	public void removeObserver(IObserver o) {
		clients.remove(o);
		
	}

	@Override
	public void notifyObservers() {
		for(IObserver c : clients) {
			if(state) {
				c.onSuccess(event);				
			}else {
				c.onFailed(event);
			}
		}				
	}
	
	public void notifyForEvent(String message, boolean state) {
		setEventState(message, state);
		notifyObservers();
	}
	
	
	
	
}
