package controllers;

import java.awt.Color;
import java.time.LocalDateTime;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import interfaces.IObserver;

public class EventLog implements IObserver{

	private JTextPane textPane;
	
	public EventLog(JTextPane textPane) {
		this.textPane = textPane;
	}
	
	@Override
	public void onSuccess(String message) {		
		appendToPane(textPane, message, Color.black);
	}

	@Override
	public void onFailed(String message) {				
		appendToPane(textPane, message, Color.RED);
	}
	
	private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        String time = LocalDateTime.now().getHour()+":"+LocalDateTime.now().getMinute()+":"+LocalDateTime.now().getSecond();
        tp.replaceSelection("["+time+"] "+msg+" \n");
    }

}
