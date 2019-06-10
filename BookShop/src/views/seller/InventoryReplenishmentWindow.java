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
	private BookController bookController = BookController.getInstance();
	private JComboBox comboBox;
	private JSpinner spinner;
	private DefaultComboBoxModel dm;

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
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dm = new DefaultComboBoxModel();
		setBounds(100, 100, 350, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 10000, 1));
		spinner.setBounds(110, 78, 110, 20);
		contentPane.add(spinner);
		
		JButton btnFeltlts = new JButton("Feltöltés");
		btnFeltlts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendQuantity();
			}
		});
		btnFeltlts.setBounds(110, 128, 110, 23);
		contentPane.add(btnFeltlts);
		
		JLabel lblMennyisg = new JLabel("Mennyiség:");
		lblMennyisg.setBounds(27, 81, 73, 14);
		contentPane.add(lblMennyisg);		
		
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
