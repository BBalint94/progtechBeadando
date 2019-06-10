package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import interfaces.IObserver;
import interfaces.ISubject;
import models.HashFunctions;
import models.UserModel;
import models.records.LoggedUser;
import models.records.User;
import models.records.UserBuilder;

public class UserController implements ISubject{
	private static UserController instance;
	private UserModel userModel = UserModel.getInstance();
	private List<IObserver> clients = new ArrayList<>();
	private String event;
	private boolean state;
	private UserController() {}
	
	public synchronized static UserController getInstance() {
		if(instance == null) {
			instance = new UserController();
		}
		return instance;
	}	
	
	public boolean registration(String name, String email, String password, Date birthDate, String address) {
		if(name == null || name.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty() || address == null || address.isEmpty()) {
			notifyForEvent("Valamelyik mezõ üres!", false);
			return false;
		}
		if(validateEmail(email)) {
			String hashedPassword = HashFunctions.getHash(password.getBytes(), "SHA-256");
			UserBuilder builder = new UserBuilder();
			User user = builder.setName(name).setEmail(email).setPassword(hashedPassword).setBirthDate(birthDate).setAddress(address).build();
			if(userModel.persist(user)) {				
				notifyForEvent("Sikeres regisztráció!", true);
				return true;
			}
			notifyForEvent("Sikertelen regisztráció!", false);
			return false;
		}
		notifyForEvent("Létezik már felhasználó ezzel az email címmel!", false);
		return false;
	}
	
	public boolean validateEmail(String email) {
		if(userModel.findUser(email, "") == null) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean login(String email, String password) {
		String hashedPassword = HashFunctions.getHash(password.getBytes(), "SHA-256");
		User user = userModel.findUser(email, hashedPassword);
		if(user != null) {
			LoggedUser loggedUser = LoggedUser.getInstance();
			loggedUser.setUserId(user.getId());
			loggedUser.setEmail(user.getEmail());
			loggedUser.setName(user.getName());
			loggedUser.setPermission(user.getPermission());			
			return true;			
		}
		notifyForEvent("Sikertelen bejelentkezés!", false);
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
