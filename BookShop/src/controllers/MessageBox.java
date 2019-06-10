package controllers;

import javax.swing.JOptionPane;

import interfaces.IObserver;

public class MessageBox implements IObserver{	
	
	@Override
	public void onSuccess(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);		
	}

	@Override
	public void onFailed(String message) {
		JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.OK_OPTION);		
	}

}
