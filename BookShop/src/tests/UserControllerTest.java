package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.UserController;
import models.records.LoggedUser;

public class UserControllerTest {

	@Test
	public void testLogin() {
		LoggedUser loggedUser = LoggedUser.getInstance();
		UserController userController = UserController.getInstance();
		String userName = "Test";
		String userEmail = "test@testmail.com";
		String userPassword = "test123";
		
		userController.login(userEmail, userPassword);
		Assert.assertEquals(loggedUser.getName(), userName);		
	}

}
