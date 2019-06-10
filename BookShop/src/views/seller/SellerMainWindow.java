package views.seller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controllers.BasketController;
import controllers.BookController;
import controllers.EventLog;
import controllers.MessageBox;
import controllers.OrderController;
import models.records.Book;
import models.records.LoggedUser;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;

public class SellerMainWindow extends JFrame {

	private JFrame frame;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private BookController bookController;
	private NewBookWindow newBookWFrame;
	private InventoryReplenishmentWindow invRepWFrame;
	private LoggedUser loggedUser;
	private AcceptBuyingWindow acceptBuyingWFrame;
	private ModifyPriceWindow modifyPriceWFrame;
	private BasketController basketController;
	private OrderController orderController;
	private DefaultTableModel tableModel;
	private MessageBox mb;
	private EventLog eventLog;
	private JScrollPane scrollPane;
	private JButton btnNewBook;
	private JButton btnStockUp;
	private JButton btnBookModify;
	private JButton btnBookRemove;
	private JButton btnAcceptBuying;
	private JLabel lblAuthor;
	private JLabel lblSellerName;
	private JButton btnRefresh;
	private JLabel lblPreviousOperation;
	private JScrollPane scrollPane_1;
	private JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellerMainWindow frame = new SellerMainWindow();
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
	public SellerMainWindow() {
		basketController = BasketController.getInstance();
		orderController = OrderController.getInstance();
		bookController = BookController.getInstance();
		loggedUser = LoggedUser.getInstance();
		mb = new MessageBox();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 131, 624, 305);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		btnNewBook = new JButton("Új könyv");
		btnNewBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if(newBookWFrame == null) {
					newBookWFrame = new NewBookWindow();
				}
				newBookWFrame.setVisible(true);
			}
		});
		btnNewBook.setBounds(662, 131, 162, 40);
		contentPane.add(btnNewBook);
		
		btnStockUp = new JButton("Készlet feltöltés");
		btnStockUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(invRepWFrame == null) {
					invRepWFrame = new InventoryReplenishmentWindow();
				}
				invRepWFrame.refreshComboBox();
				invRepWFrame.setVisible(true);
			}
		});
		btnStockUp.setBounds(662, 182, 162, 40);
		contentPane.add(btnStockUp);
		
		btnBookModify = new JButton("Árak módosítása");
		btnBookModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(modifyPriceWFrame == null) {
					modifyPriceWFrame = new ModifyPriceWindow();
				}
				modifyPriceWFrame.refreshComboBox();
				modifyPriceWFrame.setVisible(true);
			}
		});
		btnBookModify.setBounds(662, 233, 162, 40);
		contentPane.add(btnBookModify);
		
		btnBookRemove = new JButton("Könyv törlése");
		btnBookRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteBook();
			}
		});
		btnBookRemove.setBounds(662, 284, 162, 40);
		contentPane.add(btnBookRemove);
		
		btnAcceptBuying = new JButton("Vásárlás jóváhagyása");
		btnAcceptBuying.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(acceptBuyingWFrame == null) {
					acceptBuyingWFrame = new AcceptBuyingWindow();
				}
				acceptBuyingWFrame.setVisible(true);				
			}
		});
		btnAcceptBuying.setBounds(662, 367, 162, 40);
		contentPane.add(btnAcceptBuying);
		
		lblAuthor = new JLabel("Szûrés:");
		lblAuthor.setBounds(28, 103, 46, 14);
		contentPane.add(lblAuthor);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filter(textField.getText());
			}
		});
		textField.setBounds(87, 100, 180, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblSellerName = new JLabel("Eladó neve: "+loggedUser.getName());
		lblSellerName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSellerName.setBounds(28, 12, 261, 35);
		contentPane.add(lblSellerName);
		
		btnRefresh = new JButton("Frissít");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableLoad();
			}
		});
		btnRefresh.setBounds(547, 84, 105, 35);
		contentPane.add(btnRefresh);
		
		lblPreviousOperation = new JLabel("Korábbi mûveletek:");
		lblPreviousOperation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPreviousOperation.setBounds(28, 465, 142, 14);
		contentPane.add(lblPreviousOperation);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(28, 503, 624, 100);
		contentPane.add(scrollPane_1);
		
		textPane = new JTextPane();
		scrollPane_1.setViewportView(textPane);
		eventLog = new EventLog(textPane);
		
		bookController.registerObserver(mb);
		bookController.registerObserver(eventLog);
		orderController.registerObserver(mb);
		orderController.registerObserver(eventLog);
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
	
	public void deleteBook() {
		if(table.getSelectedRow() != -1) {
			int bookId = (Integer)table.getValueAt(table.getSelectedRow(), 0);
			if(!basketController.containsThisBook(bookId)) {
				if(bookController.deleteBook(bookId))
					tableLoad();
			}
		}else {
			bookController.notifyForEvent("Nincs könyv kijelölve!", false);
		}
	}
	
	public void filter(String query) {
		TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(tableModel);
		table.setRowSorter(tr);		
		tr.setRowFilter(RowFilter.regexFilter(query));
	}
}
