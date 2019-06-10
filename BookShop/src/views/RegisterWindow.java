package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import controllers.MessageBox;
import controllers.UserController;
import models.records.LoggedUser;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class RegisterWindow extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField emailField;
	private JTextField addressField;
	private JPasswordField pswdField;
	private JPasswordField pswdAgainField;
	private JDateChooser dateChooser;
	private UserController userController;
	private JLabel lblName;
	private JLabel lblEmail;
	private JLabel lblPswd;
	private JLabel lblPswdAgain;
	private JLabel lblBirthDate;
	private JLabel lblAddress;
	private JButton btnRegistration;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterWindow frame = new RegisterWindow();
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
	public RegisterWindow() {
		userController = UserController.getInstance();			
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nameField = new JTextField();
		nameField.setBounds(177, 11, 109, 20);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		emailField = new JTextField();
		emailField.setBounds(177, 42, 109, 20);
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		lblName = new JLabel("Név:");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblName.setBounds(10, 14, 157, 14);
		contentPane.add(lblName);
		
		lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(10, 45, 157, 14);
		contentPane.add(lblEmail);
		
		lblPswd = new JLabel("Jelszó:");
		lblPswd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPswd.setBounds(10, 76, 157, 14);
		contentPane.add(lblPswd);
		
		lblPswdAgain = new JLabel("Jelszó mégegyszer:");
		lblPswdAgain.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPswdAgain.setBounds(10, 107, 157, 14);
		contentPane.add(lblPswdAgain);
		
		Date date = new Date();
		dateChooser = new JDateChooser();
		dateChooser.setBounds(177, 135, 109, 20);
		dateChooser.setDateFormatString("YYYY-MM-dd");
		dateChooser.setDate(date);
		contentPane.add(dateChooser);
		
		lblBirthDate = new JLabel("Születési dátum:");
		lblBirthDate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBirthDate.setBounds(10, 141, 157, 14);
		contentPane.add(lblBirthDate);
		
		addressField = new JTextField();
		addressField.setBounds(177, 166, 109, 20);
		contentPane.add(addressField);
		addressField.setColumns(10);
		
		lblAddress = new JLabel("Lakcím:");
		lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAddress.setBounds(10, 169, 157, 14);
		contentPane.add(lblAddress);
		
		btnRegistration = new JButton("Regisztráció");
		btnRegistration.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 registration();
			}
		});
		btnRegistration.setBounds(177, 216, 109, 23);
		contentPane.add(btnRegistration);
		
		pswdField = new JPasswordField();
		pswdField.setBounds(177, 73, 109, 20);
		contentPane.add(pswdField);
		
		pswdAgainField = new JPasswordField();
		pswdAgainField.setBounds(177, 104, 109, 20);
		contentPane.add(pswdAgainField);			
	}
	
	public void registration() {
		Date dateFromDateChooser = dateChooser.getDate();
		java.sql.Date sqlDate = new java.sql.Date(dateFromDateChooser.getTime());				
		char[] passwd = pswdField.getPassword();
		String pass = String.valueOf(passwd);		
		userController.registration(nameField.getText(), emailField.getText(),pass, sqlDate, addressField.getText());		
	}
}
