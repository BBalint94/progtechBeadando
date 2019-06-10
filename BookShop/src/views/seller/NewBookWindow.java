package views.seller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import controllers.BookController;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

public class NewBookWindow extends JFrame {

	private JPanel contentPane;
	private JTextField authorField;
	private JTextField titleField;
	private JDateChooser dateChooser;
	private JComboBox comboBox;
	private JSpinner pagesSpinner;
	private JSpinner priceSpinner;
	private JSpinner quantitySpinner;
	private BookController bookController = BookController.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewBookWindow frame = new NewBookWindow();
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
	public NewBookWindow() {
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAuthor = new JLabel("Szerzõ:");
		lblAuthor.setBounds(63, 31, 46, 14);
		contentPane.add(lblAuthor);
		
		JLabel lblTitle = new JLabel("Cím:");
		lblTitle.setBounds(63, 56, 46, 14);
		contentPane.add(lblTitle);
		
		JLabel lblPages = new JLabel("Oldal:");
		lblPages.setBounds(63, 81, 46, 14);
		contentPane.add(lblPages);
		
		JLabel lblReleased = new JLabel("Megjelenés:");
		lblReleased.setBounds(63, 106, 72, 14);
		contentPane.add(lblReleased);
		
		JLabel lblGenre = new JLabel("Mûfaj:");
		lblGenre.setBounds(63, 139, 46, 14);
		contentPane.add(lblGenre);
		
		JLabel lblPrice = new JLabel("Ár:");
		lblPrice.setBounds(63, 164, 46, 14);
		contentPane.add(lblPrice);
		
		JLabel lblQuantity = new JLabel("Mennyiség:");
		lblQuantity.setBounds(63, 191, 84, 14);
		contentPane.add(lblQuantity);
		
		authorField = new JTextField();
		authorField.setBounds(162, 28, 186, 20);
		contentPane.add(authorField);
		authorField.setColumns(10);
		
		titleField = new JTextField();
		titleField.setBounds(162, 53, 186, 20);
		contentPane.add(titleField);
		titleField.setColumns(10);
		
		Date date = new Date();
		dateChooser = new JDateChooser();
		dateChooser.setBounds(162, 106, 132, 20);
		dateChooser.setDate(date);
		contentPane.add(dateChooser);		
		
		List<String> genres = bookController.getGenres();
		comboBox = new JComboBox(genres.toArray());
		comboBox.setBounds(162, 136, 186, 20);
		contentPane.add(comboBox);		
		
		pagesSpinner = new JSpinner();
		pagesSpinner.setModel(new SpinnerNumberModel(10, 10, 10000, 1));
		pagesSpinner.setBounds(162, 78, 63, 20);
		contentPane.add(pagesSpinner);
		
		priceSpinner = new JSpinner();
		priceSpinner.setModel(new SpinnerNumberModel(1, 1, 1000000, 1));
		priceSpinner.setBounds(162, 161, 63, 20);
		contentPane.add(priceSpinner);
		
		quantitySpinner = new JSpinner();
		quantitySpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		quantitySpinner.setBounds(162, 188, 63, 20);
		contentPane.add(quantitySpinner);
		
		JButton btnHozzad = new JButton("Hozz\u00E1ad");
		btnHozzad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendNewBook();			
			}
		});
		btnHozzad.setBounds(319, 210, 89, 23);
		contentPane.add(btnHozzad);
		
	}
	
	public void sendNewBook() {		
		Date dateFromDateChooser = dateChooser.getDate();
		java.sql.Date sqlDate = new java.sql.Date(dateFromDateChooser.getTime());	
		bookController.addBook(authorField.getText(), titleField.getText(), (Integer)pagesSpinner.getValue(), 
				sqlDate, comboBox.getSelectedIndex()+1, (Integer)priceSpinner.getValue(), (Integer)quantitySpinner.getValue());		
	}
}
