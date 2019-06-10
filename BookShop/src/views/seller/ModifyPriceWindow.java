package views.seller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.BookController;
import models.records.Book;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class ModifyPriceWindow extends JFrame {

	private JPanel contentPane;
	private BookController bookController;
	private JComboBox comboBox;
	private JSpinner spinner;
	private DefaultComboBoxModel dm;
	private JLabel lblFt;
	private JLabel lbNewPrice;
	private JButton btnModify;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifyPriceWindow frame = new ModifyPriceWindow();
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
	public ModifyPriceWindow() {
		bookController = BookController.getInstance();
		dm = new DefaultComboBoxModel();
		
		setBounds(100, 100, 350, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fillComboBox();
		comboBox = new JComboBox();
		comboBox.setModel(dm);
		comboBox.setBounds(10, 11, 314, 20);
		contentPane.add(comboBox);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 1000000, 1));
		spinner.setBounds(122, 72, 113, 20);
		contentPane.add(spinner);
		
		lblFt = new JLabel("Ft");
		lblFt.setBounds(245, 75, 46, 14);
		contentPane.add(lblFt);
		
		lbNewPrice = new JLabel("Új ár:");
		lbNewPrice.setBounds(66, 75, 46, 14);
		contentPane.add(lbNewPrice);
		
		btnModify = new JButton("Módosít");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendPrice();
			}
		});
		btnModify.setBounds(122, 128, 89, 23);
		contentPane.add(btnModify);
	}
	
	public void fillComboBox() {
		List<Book> books = bookController.getBooks();		
		for (Book b : books) {			
			dm.addElement(b.getId()+" | "+b.getAuthor()+" : "+b.getTitle()+"");
		}		
	}
	
	public void sendPrice() {
		if(comboBox.getSelectedIndex() != -1) {
			String[] comboText = comboBox.getSelectedItem().toString().split(" | ");
			int bookId = Integer.parseInt(comboText[0]);
			bookController.modifyPrice(bookId, (Integer)spinner.getValue());
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
