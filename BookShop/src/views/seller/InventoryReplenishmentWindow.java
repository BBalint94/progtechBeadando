package views.seller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.BookController;
import models.records.Book;

import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InventoryReplenishmentWindow extends JFrame {

	private JPanel contentPane;
	private BookController bookController;
	private JComboBox comboBox;
	private JSpinner spinner;
	private DefaultComboBoxModel dm;
	private JButton btnUpload;
	private JLabel lblQuantity;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryReplenishmentWindow frame = new InventoryReplenishmentWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InventoryReplenishmentWindow() {		
		dm = new DefaultComboBoxModel();
		bookController = BookController.getInstance();
		
		setBounds(100, 100, 350, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 10000, 1));
		spinner.setBounds(110, 78, 110, 20);
		contentPane.add(spinner);
		
		btnUpload = new JButton("Feltöltés");
		btnUpload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendQuantity();
			}
		});
		btnUpload.setBounds(110, 128, 110, 23);
		contentPane.add(btnUpload);
		
		lblQuantity = new JLabel("Mennyiség:");
		lblQuantity.setBounds(27, 81, 73, 14);
		contentPane.add(lblQuantity);		
		
		fillComboBox();
		comboBox = new JComboBox();
		comboBox.setModel(dm);
		comboBox.setBounds(10, 11, 314, 20);
		contentPane.add(comboBox);			
		
	}
	
	public void fillComboBox() {
		List<Book> books = bookController.getBooks();		
		for (Book b : books) {			
			dm.addElement(b.getId()+" | "+b.getAuthor()+" : "+b.getTitle()+"");
		}		
	}
	
	public void sendQuantity() {
		if(comboBox.getSelectedIndex() != -1) {
			String[] comboText = comboBox.getSelectedItem().toString().split(" | ");
			int bookId = Integer.parseInt(comboText[0]);		
			bookController.updateQuantity(bookId, (Integer)spinner.getValue());
		}else {
			bookController.notifyForEvent("Nincs könyv kijelölve!", false);
		}
	}
	
	public void refreshComboBox() {
		dm.removeAllElements();
		fillComboBox();
		comboBox.setModel(dm);
	}
}
