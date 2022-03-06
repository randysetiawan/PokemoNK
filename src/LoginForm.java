import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginForm implements ActionListener{
	JFrame frame = new JFrame();
	JPanel mainPanel = new JPanel(new GridLayout(0, 1));
	JPanel emptyPanel = new JPanel();
	JPanel buttonsPanel = new JPanel(new GridLayout(2, 1));
	JLabel loginLabel = new JLabel("Login");
	JLabel usernameLabel = new JLabel("Username");
	JLabel passwordLabel = new JLabel("Password");
	JLabel errorLabel = new JLabel();
	JTextField usernameTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	Color lightBlue = new Color(51, 204, 255);
	Font headerFont = new Font("Comic Sans MS", Font.PLAIN, 28);
	Font contentFont = new Font("Comic Sans MS", Font.BOLD, 15);
	Font errorFont = new Font("Comic Sans MS", Font.BOLD, 15);

	public LoginForm() {
		mainPanel.setBackground(lightBlue);
		mainPanel.setBorder(new EmptyBorder(0, 250, 0, 250));
		loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loginLabel.setFont(headerFont);
		mainPanel.add(loginLabel);
		emptyPanel.setBackground(lightBlue);
		mainPanel.add(emptyPanel);
		usernameLabel.setFont(contentFont);
		passwordLabel.setFont(contentFont);
		mainPanel.add(usernameLabel);
		mainPanel.add(usernameTextField);
		mainPanel.add(passwordLabel);
		mainPanel.add(passwordField);
		mainPanel.add(emptyPanel);
		buttonsPanel.setBackground(lightBlue);
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		buttonsPanel.add(loginButton);
		buttonsPanel.add(registerButton);
		mainPanel.add(buttonsPanel);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setFont(errorFont);
		errorLabel.setForeground(Color.RED);
		mainPanel.add(errorLabel);
		frame.setContentPane(mainPanel);
		initializeFrame();
	}
	
	public void initializeFrame() {
		frame.setTitle("PokemoNK");
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == registerButton) {
			frame.dispose();
			new RegisterForm();
		}
		if(e.getSource() == loginButton) {
			if(usernameTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
				errorLabel.setText("All field must be filled");
			} else {
				String username = usernameTextField.getText(), password = passwordField.getText();
				ResultSet resultSet = Connect.getInstance().executeQuery("SELECT * FROM user;");
				try {
					while(resultSet.next()) {
						if(username.equals(resultSet.getString(2))) {
							if(password.equals(resultSet.getString(7))) {
								User.logIn(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6), resultSet.getString(7));
								frame.dispose();
								new MainForm();
								return;
							} else {
								errorLabel.setText("Invalid username or password");
								return;
							}
						}
					}
					errorLabel.setText("Invalid username or password");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		
	}
	
	public static void main(String[] args) {
		Connect.getInstance();
		new LoginForm();
	}

}