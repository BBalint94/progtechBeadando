package views.seller;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controllers.BasketController;
import controllers.BookController;
import controllers.OrderController;
import models.records.Book;
import models.records.Order;

import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AcceptBuyingWindow extends JFrame {

	private JPanel contentPane;
	private OrderController orderController = OrderController.getInstance();
	private BookController bookController = BookController.getInstance();
	private BasketController basketController = BasketController.getInstance();
	private JComboBox comboBox;
	private JTextArea textArea;
	private DefaultComboBoxModel dm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AcceptBuyingWindow frame = new AcceptBuyingWindow();
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
	public AcceptBuyingWindow() {
		dm = new DefaultComboBoxModel();
		
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		
		fillComboBox();
		comboBox = new JComboBox();
		comboBox.setBounds(66, 33, 390, 20);
		comboBox.setModel(dm);
		contentPane.add(comboBox);
		
		textArea = new JTextArea();
		textArea.setBounds(66, 101, 390, 184);
		contentPane.add(textArea);
		
		JButton btnListz = new JButton("List�z");
		btnListz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderContent();			
			}
		});
		btnListz.setBounds(367, 67, 89, 23);
		contentPane.add(btnListz);
		
		JButton btnElfogads = new JButton("Elfogad�s");
		btnElfogads.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptOrder();		
			}
		});
		btnElfogads.setBounds(367, 317, 89, 23);
		contentPane.add(btnElfogads);	
		
		JButton btnFrisst = new JButton("Friss�t");
		btnFrisst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshComboBox();
			}
		});
		btnFrisst.setBounds(66, 67, 89, 23);
		contentPane.add(btnFrisst);
		
	}
	
	public void refreshData() {
		comboBox.removeItemAt(comboBox.getSelectedIndex());
		textArea.setText("");
	}
	
	public void orderContent() {
		if(comboBox.getSelectedIndex() != -1) {
			textArea.setText("");
			String[] comboText = comboBox.getSelectedItem().toString().split("-");				
			int orderId = Integer.parseInt(comboText[0]);		
			List<Book> book = bookController.getWithOrder(orderId);
			for(Book b : book) {
				textArea.append(b.getAuthor()+" --- "+b.getTitle()+" --- "+b.getPrice()+" Ft \n");
			}
			textArea.setEditable(false);
		}else {
			orderController.notifyForEvent("Nincs kiv�lasztva rendel�s!", false);
		}
	}
	
	public void fillComboBox(){
		List<Order> orders = orderController.getOrders();		
		for(Order o : orders) {
			dm.addElement(""+o.getId()+"--- "+o.getCustomer()+" --- "+o.getSumPrice()+" Ft --- "+o.getWay()+" ");			
		}
		
	}
	
	public void acceptOrder() {
		if(comboBox.getSelectedIndex() != -1) {		
			String[] comboText = comboBox.getSelectedItem().toString().split("---");				
			int orderId = Integer.parseInt(comboText[0]);				
			Map<Integer, Integer> bookCount = basketController.getBooksCount(orderId);
			bookController.decreaseQuantity(bookCount);
			if(orderController.removeOrder(orderId)) {					
				refreshData();
			}
		}else {
			orderController.notifyForEvent("Nincs kiv�lasztva rendel�s!", false);
		}
	}
	
	public void refreshComboBox() {
		dm.removeAllElements();
		fillComboBox();
		comboBox.setModel(dm);
	}
}
