package views.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import controllers.BasketController;
import controllers.BookController;
import controllers.EventLog;
import controllers.MessageBox;
import models.records.Book;
import models.records.LoggedUser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.SwingConstants;

public class CustomerMainWindow extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTable table;
	private JTextField filterField;
	private JTextArea textArea;
	private DefaultTableModel tableModel;
	private BookController bookController;
	private BasketController basketController;
	private MessageBox mb;
	private EventLog eventLog;
	private LoggedUser loggedUser;
	private BasketWindow basketWFrame;
	private JButton basketBtn;
	private JButton addToBasketBtn;
	private JLabel filterLabel;
	private JLabel nameLbl;
	private JLabel previousEventLbl;
	private JScrollPane scrollPane_1;
	private JTextPane eventTextPane;
	private JButton btnRefresh;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {										
					CustomerMainWindow frame = new CustomerMainWindow();					
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
	public CustomerMainWindow() {
		bookController = BookController.getInstance();
		basketController = BasketController.getInstance();
		mb = new MessageBox();
		loggedUser = LoggedUser.getInstance();
		
		
		setBounds(100, 100, 750, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 140, 675, 311);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		basketBtn = new JButton("Kosár");
		basketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(basketWFrame == null) {
					basketWFrame = new BasketWindow();
				}
				basketWFrame.refreshTable();
				basketWFrame.setVisible(true);
			}
		});
		basketBtn.setBounds(526, 71, 175, 39);
		contentPane.add(basketBtn);
		
		addToBasketBtn = new JButton("Kosárba helyez");
		addToBasketBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				addToBasket();
			}
		});
		addToBasketBtn.setBounds(516, 469, 185, 39);
		contentPane.add(addToBasketBtn);
		
		filterLabel = new JLabel("Szûrés:");
		filterLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		filterLabel.setBounds(211, 104, 74, 14);
		contentPane.add(filterLabel);
		
		filterField = new JTextField();
		filterField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String query = filterField.getText();
				filter(query);
			}
		});
		filterField.setColumns(10);
		filterField.setBounds(298, 101, 175, 20);
		contentPane.add(filterField);
		
		nameLbl = new JLabel("Név: "+loggedUser.getName());
		nameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameLbl.setBounds(419, 27, 282, 23);
		contentPane.add(nameLbl);
		
		previousEventLbl = new JLabel("Korábbi mûveletek:");
		previousEventLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		previousEventLbl.setBounds(26, 471, 179, 30);
		contentPane.add(previousEventLbl);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(26, 535, 675, 114);
		contentPane.add(scrollPane_1);
		
		eventTextPane = new JTextPane();
		scrollPane_1.setViewportView(eventTextPane);
		eventLog = new EventLog(eventTextPane);		
		
		btnRefresh = new JButton("Frissít");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableLoad();
			}
		});
		btnRefresh.setBounds(26, 90, 175, 39);
		contentPane.add(btnRefresh);
		
		basketController.registerObserver(mb);
		basketController.registerObserver(eventLog);
	}
	
	public void tableLoad() {		
		List<Book> blist = bookController.getBooks();
		Object[] columns = {"Id","Szerzõ","Cím","Oldalszám","Megjelenés","Mûfaj","Ár","Mennyiség"};				
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(columns);
		for (Book b : blist) {
			Object[] rows = {b.getId(),b.getAuthor(),b.getTitle(),b.getPages(),b.getReleasedOn(),b.getGenre(), b.getPrice(), b.getQuantity()};
			tableModel.addRow(rows);
		}
		table.setModel(tableModel);
		table.setBackground(Color.white);
		table.setForeground(Color.black);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void addToBasket() {
		if(table.getSelectedRow() != -1) {
			int storedQuantity = bookController.getCurrentQuantity((Integer)table.getValueAt(table.getSelectedRow(), 0));
			if(storedQuantity > 0) {
				basketController.addToBasket((Integer)table.getValueAt(table.getSelectedRow(), 0), 
						(String)table.getValueAt(table.getSelectedRow(), 1), (String)table.getValueAt(table.getSelectedRow(), 2), loggedUser.getUserId());		
			}else {
				basketController.notifyForEvent("Nincs több példány ebbõl a könyvbõl!", false);
			}
		}else {			
			basketController.notifyForEvent("Nem jelölt ki könyvet!", false);
		}
	}
	
	public void filter(String query) {
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(tableModel);
		table.setRowSorter(tr);		
		tr.setRowFilter(RowFilter.regexFilter(query));
	}
}
