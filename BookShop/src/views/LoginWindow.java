package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.looks.windows.WindowsLookAndFeel;

import controllers.MessageBox;
import controllers.UserController;
import models.BookModel;
import models.DatabaseManager;
import models.records.LoggedUser;
import views.customer.CustomerMainWindow;
import views.seller.SellerMainWindow;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;

public class LoginWindow {

	private JFrame frame;
	private JTextField emailField;
	private JPasswordField pswdField;
	private DatabaseManager dbManager = DatabaseManager.getInstance();
	private UserController userController = UserController.getInstance();
	private LoggedUser loggedUser = LoggedUser.getInstance();
	private MessageBox mb;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {				
				try {					
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mb = new MessageBox();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(10, 81, 120, 14);
		frame.getContentPane().add(lblEmail);
		
		JLabel lblPswd = new JLabel("Jelszó:");
		lblPswd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPswd.setBounds(10, 116, 120, 14);
		frame.getContentPane().add(lblPswd);
		
		emailField = new JTextField();
		emailField.setBounds(140, 78, 143, 20);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		pswdField = new JPasswordField();
		pswdField.setBounds(140, 113, 143, 20);
		frame.getContentPane().add(pswdField);
		
		JButton btnLogin = new JButton("Bejelentkezés");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();			
			}
		});
		btnLogin.setBounds(140, 158, 143, 23);
		frame.getContentPane().add(btnLogin);
		
		JButton btnRegistration = new JButton("Regisztráció");
		btnRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterWindow registerWFrame = new RegisterWindow();
				registerWFrame.setVisible(true);
			}
		});
		btnRegistration.setBounds(140, 213, 143, 23);
		frame.getContentPane().add(btnRegistration);
		
		JLabel lblBookshop = new JLabel("BookShop");
		lblBookshop.setHorizontalAlignment(SwingConstants.CENTER);
		lblBookshop.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblBookshop.setBounds(96, 22, 215, 33);
		frame.getContentPane().add(lblBookshop);
		
		userController.registerObserver(mb);
	}
	
	public void login() {
		char[] passwd = pswdField.getPassword();
		String pass = String.valueOf(passwd);
		if(userController.login(emailField.getText(), pass)) {
			switch (loggedUser.getPermission()) {
			case 0:
				CustomerMainWindow cMWFrame = new CustomerMainWindow();
				frame.dispose();
				cMWFrame.tableLoad();
				cMWFrame.setVisible(true);
				break;
			case 1:
				SellerMainWindow sMWFrame = new SellerMainWindow();
				frame.dispose();
				sMWFrame.tableLoad();
				sMWFrame.setVisible(true);
				break;
			default:
				break;
			}
		}
	}
}
