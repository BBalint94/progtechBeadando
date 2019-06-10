package tests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import controllers.BookController;
import models.records.Book;


public class BookControllerTest {

	private BookController bookController;
	
	@Test
	public void testUpdateQuantity() {
		bookController = BookController.getInstance();
		Random rnd = new Random();
		
		// 22 : TestAuthor , TestBook
		int bookId = 22;
		Book book = bookController.fetchBook(bookId);
		int oldQuantity = book.getQuantity();
		int addQuantity = 12;
		
		bookController.updateQuantity(bookId, addQuantity);
		Book modifiedBook = bookController.fetchBook(bookId);
		
		Assert.assertTrue(modifiedBook.getQuantity()-book.getQuantity() == 12);
	}
	
	@Test
	public void testUpdateQuantityIsNegative() {
		bookController = BookController.getInstance();
		Random rnd = new Random();
		
		// 22 : TestAuthor , TestBook
		int bookId = 22;
		Book book = bookController.fetchBook(bookId);
		int oldQuantity = book.getQuantity();
		int addQuantity = -12;
		
		bookController.updateQuantity(bookId, addQuantity);
		Book modifiedBook = bookController.fetchBook(bookId);
		
		Assert.assertTrue(modifiedBook.getQuantity() == book.getQuantity());
	}

	@Test
	public void testModifyPrice() {		
		bookController = BookController.getInstance();
		Random rnd = new Random();
		
		// 22 : TestAuthor , TestBook
		int bookId = 22;
		Book book = bookController.fetchBook(bookId);		
		int oldPrice = book.getPrice();
		int newPrice = rnd.nextInt(10)*1000;
		if(oldPrice == newPrice)
			newPrice+=500;
		bookController.modifyPrice(bookId, newPrice);
		Book modifiedBook = bookController.fetchBook(bookId);
		
		Assert.assertTrue(book.getPrice() != modifiedBook.getPrice() && modifiedBook.getPrice() == newPrice);	
	}
	
	@Test
	public void testModifyPriceIsNegative() {
		bookController = BookController.getInstance();
		Random rnd = new Random();
		
		// 22 : TestAuthor , TestBook
		int bookId = 22;
		Book book = bookController.fetchBook(bookId);		
		int oldPrice = book.getPrice();
		int newPrice = rnd.nextInt(10)*-1000;
		
		bookController.modifyPrice(bookId, newPrice);
		Book modifiedBook = bookController.fetchBook(bookId);	
		
		Assert.assertTrue(book.getPrice() == modifiedBook.getPrice());		
	}

}
