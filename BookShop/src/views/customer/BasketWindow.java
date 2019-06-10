package views.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controllers.BasketController;
import controllers.EventLog;
import controllers.MessageBox;
import controllers.OrderController;
import models.records.Basket;
import models.records.Book;
import models.records.LoggedUser;
import models.records.NoNeed;
import models.records.Order;
import models.records.PackageAutomat;
import models.records.WithCourier;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class BasketWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Basket basket;
	private BasketController basketController;
	private OrderController orderController;
	private LoggedUser loggedUser;
	private DefaultTableModel tableModel;
	private JLabel lblOwing;
	private JRadioButton rdbtnCourier;
	private JRadioButton rdbtnPackageAutomat;
	private JRadioButton rdbtnPersonalReceipt;
	private ButtonGroup group;
	private JButton btnDelete;
	private JButton btnBuy;
	private JLabel lblTransportType;
	private JScrollPane scrollPane;
	private int sumPrice;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasketWindow frame = new BasketWindow();					
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
	public BasketWindow() {			
		basketController = BasketController.getInstance();
		orderController = OrderController.getInstance();
		loggedUser = LoggedUser.getInstance();
		
		setBounds(100, 100, 650, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		basket = basketController.getBasket(loggedUser.getUserId());
		fillTable(basket);
		
		rdbtnCourier = new JRadioButton("Futár (+1000Ft)");
		rdbtnCourier.setBounds(436, 127, 174, 23);
		contentPane.add(rdbtnCourier);
		
		rdbtnPackageAutomat = new JRadioButton("Csomagautomata(+700Ft)");
		rdbtnPackageAutomat.setBounds(436, 165, 174, 23);
		contentPane.add(rdbtnPackageAutomat);
		
		rdbtnPersonalReceipt = new JRadioButton("Személyes Átvétel");
		rdbtnPersonalReceipt.setBounds(436, 203, 174, 23);
		contentPane.add(rdbtnPersonalReceipt);
		
		group = new ButtonGroup();
		group.add(rdbtnCourier);
		group.add(rdbtnPackageAutomat);
		group.add(rdbtnPersonalReceipt);
		
		rdbtnCourier.setSelected(true);
		
		btnDelete = new JButton("Törlés");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove();				
			}
		});
		btnDelete.setBounds(436, 29, 140, 40);
		contentPane.add(btnDelete);
	
		btnBuy = new JButton("Vásárlás");
		btnBuy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendOrder();
				refreshTable();
			}
		});
		btnBuy.setBounds(436, 292, 140, 40);
		contentPane.add(btnBuy);
		
		lblOwing = new JLabel("Fizetendõ:");
		lblOwing.setHorizontalAlignment(SwingConstants.LEFT);
		lblOwing.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblOwing.setBounds(27, 270, 393, 29);		
		lblOwing.setText("Fizetendõ: "+sumPrice + " Ft");
		contentPane.add(lblOwing);	
		
		lblTransportType = new JLabel("Rendelés átvételének módja:");
		lblTransportType.setBounds(440, 93, 170, 14);
		contentPane.add(lblTransportType);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 29, 393, 230);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(tableModel);
		table.setBackground(Color.white);
		table.setForeground(Color.black);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(4).setPreferredWidth(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
	}
	
	public void refreshTable() {
		tableModel.setRowCount(0);
		sumPrice = 0;
		basket = basketController.getBasket(loggedUser.getUserId());
		Object[] columns = {"Id","Author","Title","Price","Instance"};
		tableModel.setColumnIdentifiers(columns);
		for (Book b : basket.getBooks()) {
			sumPrice += b.getPrice();
			Object[] rows = {b.getId(),b.getAuthor(),b.getTitle(), b.getPrice(), b.getInstanceId()};
			tableModel.addRow(rows);			
		}
		table.repaint();
		table.setBackground(Color.white);
		table.setForeground(Color.black);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(0);
		table.getColumnModel().getColumn(4).setMaxWidth(0);
		table.getColumnModel().getColumn(4).setMinWidth(0);
		table.getColumnModel().getColumn(4).setPreferredWidth(0);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lblOwing.setText("Fizetendõ: "+sumPrice+" Ft");
	}
	
	public void fillTable(Basket basket) {
		Object[] columns = {"Id","Szerzõ","Cím","Ár","Instance"};				
		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(columns);
		for (Book b : basket.getBooks()) {
			sumPrice += b.getPrice();
			Object[] rows = {b.getId(),b.getAuthor(),b.getTitle(), b.getPrice(), b.getInstanceId()};			
			tableModel.addRow(rows);			
		}
	}
	
	public void sendOrder() {
		if(rdbtnCourier.isSelected()) {
			loggedUser.setTransportStrategy(new WithCourier());
		}else if(rdbtnPackageAutomat.isSelected()) {
			loggedUser.setTransportStrategy(new PackageAutomat());
		}else if(rdbtnPersonalReceipt.isSelected()) {
			loggedUser.setTransportStrategy(new NoNeed());
		}
		Order order = new Order();
		order.setSumPrice(sumPrice);				
		orderController.newOrder(loggedUser.transport(order));
		int orderId = orderController.getLastOrderId();
		if(basketController.setOrder(loggedUser.getUserId(), orderId)) {			
			basketController.notifyForEvent("Rendelés feladva!", true);
		}else {			
			basketController.notifyForEvent("Rendelés felvétele sikertelen!", false);
		}
	}
	
	public void remove() {
		if(table.getSelectedRow() != -1) {
			String book = (String)table.getValueAt(table.getSelectedRow(), 1)+" : "+table.getValueAt(table.getSelectedRow(), 2);
			if(basketController.removeFromBasket((Integer)table.getValueAt(table.getSelectedRow(), 4), loggedUser.getUserId())) {
				refreshTable();				
				basketController.notifyForEvent(book+" - eltávolítva a kosárból", true);
			}
		}else {
			basketController.notifyForEvent("Nincs kijelölve könyv!", false);
		}
	}
}
