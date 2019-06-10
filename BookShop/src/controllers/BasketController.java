package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import interfaces.IObserver;
import interfaces.ISubject;
import models.BasketModel;
import models.records.Basket;
import models.records.Book;
import models.records.BookBuilder;

public class BasketController implements ISubject {
	
	private static BasketController instance;
	private BasketModel basketModel = BasketModel.getInstance();
	private List<IObserver> clients = new ArrayList<>();
	private String event;
	private boolean state;
	private BasketController() {}
	
	public static BasketController getInstance() {
		if(instance == null) {
			instance = new BasketController();
		}
		return instance;
	}
	
	public Basket getBasket(int userId){
		Basket basket = basketModel.fetch(userId);
		return basket;
	}	
	
	public boolean addToBasket(int bookId, String author, String title, int customerId) {
		BookBuilder builder = new BookBuilder();
		Book book = builder.setId(bookId).setAuthor(author).setTitle(title).build();
		List<Book> blist = new ArrayList<>();
		blist.add(book);
		Basket basket = new Basket(blist);
		basket.setOwnerId(customerId);
		if(basketModel.persist(basket)) {			
			setEventState(book.getAuthor()+" : "+book.getTitle()+" - kosárba helyezve", true);
			notifyObservers();
			return true;
		}
		setEventState(book.getAuthor()+" : "+book.getTitle()+" - sikertelen kosárba helyezés", false);
		notifyObservers();
		return false;		
	}
	
	public boolean containsThisBook(int bookId) {
		return basketModel.containsThisBook(bookId);
	}
	
	public Map<Integer, Integer> getBooksCount(int orderId){
		Map<Integer, Integer> bookCount = new HashMap<Integer, Integer>();
		basketModel.bookCount(bookCount, orderId);
		return bookCount;
	}
	
	public boolean removeFromBasket(int instanceId, int customerId) {
		if(basketModel.remove(instanceId, customerId)) {
			return true;
		}else {
			return false;
		}
	}	
	
	public boolean setOrder(int customerId, int orderId) {
		if(basketModel.setOrder(customerId, orderId)) {			
			return true;
		}else {
			return false;
		}
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
