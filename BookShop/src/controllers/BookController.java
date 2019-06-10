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
			notifyForEvent("Valamelyik mezõ üres!", false);
			return false;
		}
		BookBuilder builder = new BookBuilder();
		Book book = builder.setAuthor(author).setTitle(title).setPages(pages).setReleasedOn(releasedOn)
				.setGenre(String.valueOf(genreId)).setPrice(price).setQuantity(quantity).build();
		if(bookModel.persist(book)) {				
			notifyForEvent(author+" : "+title+" - hozzáadva az adatbázishoz", true);
			return true;
		}
		notifyForEvent(author+" : "+title+" - hozzáadása az adatbázishoz sikertelen", false);
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
				notifyForEvent("A könyv mennyisége növelve! (+ "+added+" db)", true);
				return true;
			}
			notifyForEvent("Nem sikerült növelni a könyv mennyiségét!", false);
			return false;
		}
		notifyForEvent("A mennyiség csak pozitív érték lehet!", false);
		return false;
	}
	
	public boolean modifyPrice(int bookId, int newPrice) {
		if(newPrice > 0) {
			if(bookModel.modifyPrice(bookId, newPrice)) {
				notifyForEvent("Könyv ára módosítva, új ár: "+newPrice, true);
				return true;
			}
			notifyForEvent("Könyv árának módosítása sikertelen!", false);
			return false;
		}
		notifyForEvent("A könyv árának többnek kell lennie 0 Ft-nál!", false);
		return false;
	}
	
	public Book fetchBook(int bookId) {
		Book book = bookModel.fetch(bookId);
		return book;
	}
	
	public boolean deleteBook(int bookId) {
		if(bookModel.delete(bookId)) {
			notifyForEvent("A kijelölt könyv törölve!", true);
			return true;
		}
		notifyForEvent("Nem sikerült törölni a könyvet!", false);
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
