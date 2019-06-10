package controllers;

import java.util.ArrayList;
import java.util.List;

import interfaces.IObserver;
import interfaces.ISubject;
import models.OrderModel;
import models.records.Order;
import models.records.TransportStrategy;

public class OrderController implements ISubject{

	private static OrderController instance;
	private OrderModel orderModel = OrderModel.getInstance();
	private List<IObserver> clients = new ArrayList<>();
	private String event;
	private boolean state;
	private OrderController() {}
	
	public static OrderController getInstance() {
		if(instance == null) {
			instance = new OrderController();
		}
		return instance;
	}
	
	public List<Order> getOrders(){
		List<Order> orders = orderModel.fetchMultiple();		
		return orders;
	}
	
	public boolean newOrder(Order order) {		
		if(orderModel.persist(order)) {			
			return true;
		}
		return false;
	}
	
	public int getLastOrderId() {
		int id = orderModel.getLastOrderId();		
		return id;
	}
	
	public boolean removeOrder(int orderId) {
		if(orderModel.delete(orderId)) {
			notifyForEvent("Rendelés feladva!", true);
			return true;
		}
		notifyForEvent("Rendelés feladása sikertelen!", false);
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
