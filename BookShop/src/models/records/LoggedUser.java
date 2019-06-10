package models.records;

import java.util.List;

public class LoggedUser {

	private static LoggedUser instance;
	private int userId;
	private String name;
	private String email;
	private byte permission;	
	private TransportStrategy transportStrategy;
	private Basket basket;
	
	private LoggedUser() {}
	
	public static LoggedUser getInstance() {
		if(instance == null) {
			instance = new LoggedUser();
		}
		return instance;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getPermission() {
		return permission;
	}

	public void setPermission(byte permission) {
		this.permission = permission;
	}
	
	public Basket getBasket() {
		return basket;
	}

	public void setBasket(Basket basket) {
		this.basket = basket;
	}	
	
	public void setTransportStrategy(TransportStrategy transportStrategy) {
		this.transportStrategy = transportStrategy;
	}

	public Order transport(Order order) {
		Order o = this.transportStrategy.transport(order);
		return o;
	}
	
	
	
	
	
}
