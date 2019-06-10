package tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import models.HashFunctions;

public class HashFunctionsTest {	

	@Test
	public void testGetHash() {		
		String hashedWord = "a858b7a0641ba6c3189a883024b446d195b8a5f982874f4d3adc388588ccd9e7";
		Assert.assertEquals(hashedWord,HashFunctions.getHash("testword".getBytes(), "SHA-256"));
	}

}
