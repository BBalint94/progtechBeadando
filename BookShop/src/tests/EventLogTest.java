package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import javax.swing.JTextPane;

import org.junit.Assert;
import org.junit.Test;

import controllers.EventLog;

public class EventLogTest {

	private JTextPane eventTextPane;
	private EventLog eventLog;
	
	@Test
	public void testOnSuccess() {
		eventTextPane = new JTextPane();
		eventLog = new EventLog(eventTextPane);
		
		String message = "Test message";
		eventLog.onSuccess(message);				
		
		Assert.assertTrue(eventTextPane.getText().contains(message));
		
	}

	@Test
	public void testOnFailed() {		
		eventTextPane = new JTextPane();
		eventLog = new EventLog(eventTextPane);
		
		String message = "Test message";
		eventLog.onFailed(message);
		
		Assert.assertTrue(eventTextPane.getText().contains(message));
	}

}
