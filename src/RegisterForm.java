import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class RegisterForm implements ActionListener{
	JFrame frame = new JFrame();
	JPanel mainPanel = new JPanel(new GridLayout(0, 1));
	JPanel genderPanel = new JPanel(new GridLayout(1,1));
	JPanel buttonsPanel = new JPanel();
	JLabel registerLabel = new JLabel("Register");
	JLabel usernameLabel = new JLabel("Username");
	JLabel firstNameLabel = new JLabel("First Name");
	JLabel lastNameLabel = new JLabel("Last Name");
	JLabel emailLabel = new JLabel("E-mail");
	JLabel ageLabel = new JLabel("Age");
	JLabel passwordLabel = new JLabel("Password");
	JLabel confirmPasswordLabel = new JLabel("Confirm Password");
	JLabel errorLabel = new JLabel();
	JTextField usernameTextField = new JTextField();
	JTextField firstNameTextField = new JTextField();
	JTextField lastNameTextField = new JTextField();
	JTextField emailTextField = new JTextField();
	JPasswordField passwordField = new JPasswordField();
	JPasswordField confirmPasswordField = new JPasswordField();
	JRadioButton maleRadioButton = new JRadioButton("Male");
	JRadioButton femaleRadioButton = new JRadioButton("Female");
	ButtonGroup gender = new ButtonGroup();
	SpinnerModel ageSpinnerModel = new SpinnerNumberModel(11, 11, 100, 1);
	JSpinner ageSpinner = new JSpinner(ageSpinnerModel);
	JButton registerButton = new JButton("Register");
	JButton backToLoginButton = new JButton("Back to Login");
	Color lightBlue = new Color(51, 204, 255);
	Font headerFont = new Font("Comic Sans MS", Font.PLAIN, 28);
	Font contentFont = new Font("Comic Sans MS", Font.BOLD, 15);
	Font errorFont = new Font("Comic Sans MS", Font.BOLD, 15);

	public RegisterForm() {
		mainPanel.setBackground(lightBlue);
		mainPanel.setBorder(new EmptyBorder(0, 250, 0, 250));
		registerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registerLabel.setFont(headerFont);
		mainPanel.add(registerLabel);
		usernameLabel.setFont(contentFont);
		mainPanel.add(usernameLabel);
		mainPanel.add(usernameTextField);
		firstNameLabel.setFont(contentFont);
		mainPanel.add(firstNameLabel);
		mainPanel.add(firstNameTextField);
		lastNameLabel.setFont(contentFont);
		mainPanel.add(lastNameLabel);
		mainPanel.add(lastNameTextField);
		emailLabel.setFont(contentFont);
		mainPanel.add(emailLabel);
		mainPanel.add(emailTextField);
		maleRadioButton.setBackground(lightBlue);
		femaleRadioButton.setBackground(lightBlue);
		maleRadioButton.setFont(contentFont);
		femaleRadioButton.setFont(contentFont);
		gender.add(maleRadioButton);
		gender.add(femaleRadioButton);
		genderPanel.add(maleRadioButton);
		genderPanel.add(femaleRadioButton);
		mainPanel.add(genderPanel);
		ageLabel.setFont(contentFont);
		mainPanel.add(ageLabel);
		ageSpinner.setEditor(new JSpinner.DefaultEditor(ageSpinner));
		mainPanel.add(ageSpinner);
		passwordLabel.setFont(contentFont);
		mainPanel.add(passwordLabel);
		mainPanel.add(passwordField);
		confirmPasswordLabel.setFont(contentFont);
		mainPanel.add(confirmPasswordLabel);
		mainPanel.add(confirmPasswordField);
		buttonsPanel.setBackground(lightBlue);
		registerButton.addActionListener(this);
		buttonsPanel.add(registerButton);
		backToLoginButton.addActionListener(this);
		buttonsPanel.add(backToLoginButton);
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
		if(e.getSource() == backToLoginButton) {
			frame.dispose();
			new LoginForm();
		}
		if(e.getSource() == registerButton) {
			if(usernameTextField.getText().isEmpty() || firstNameTextField.getText().isEmpty() || lastNameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() || (maleRadioButton.isSelected() == false && femaleRadioButton.isSelected() == false) || passwordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
				errorLabel.setText("All field must be filled");
			} else {
				String username = usernameTextField.getText(), name = firstNameTextField.getText() +" " +lastNameTextField.getText(), email = emailTextField.getText(), gender, password = passwordField.getText(), confirmPassword = confirmPasswordField.getText();
				int age = (int) ageSpinner.getValue();
				if(maleRadioButton.isSelected()) {
					gender = "male";
				} else {
					gender = "female";
				}
				if(username.length() < 8 || username.length() > 15) {
					errorLabel.setText("Username length must be more than 8 and no more than 15");
				} else {
					ResultSet resultSet = Connect.getInstance().executeQuery("SELECT * FROM user;");
					try {
						while(resultSet.next()) {
							if(username.equals(resultSet.getString(2))) {
								errorLabel.setText("Username has already taken");
								return;
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					if(!emailValidation(email)) {
						errorLabel.setText("E-mail must be an E-mail format (xxxx..@xxxx....com)");
					} else {
						if(password.length() > 20) {
							errorLabel.setText("Password maximal characters are 20 characters");
						} else {
							boolean containAlpha = false, containNum = false, isAlphanum = true;
							for(int i = 0; i < password.length(); i++) {
								if(!isAlphaNumeric(password.charAt(i))) {
									isAlphanum = false;
								}
								if(isAlphabetic(password.charAt(i))) {
									containAlpha = true;
								}
								if(isNumeric(password.charAt(i))) {
									containNum = true;
								}
							}
							if(!(containAlpha && containNum && isAlphanum)) {
								errorLabel.setText("Password must consist of letters and numbers");
							} else {
								if(!password.equals(confirmPassword)) {
									errorLabel.setText("Password must be equal");
								} else {
									Connect.getInstance().executeUpdate("INSERT INTO user VALUES (null, '" +username +"', '" +name +"', " +age +", '" +email +"', '" +gender +"', '" +password +"');");
									System.out.println("Register success");
									resultSet = null;
									resultSet = Connect.getInstance().executeQuery("SELECT UserId FROM user WHERE Username = '" +username +"';");
									int userId = -1;
									try {
										resultSet.next();
										userId = resultSet.getInt(1);
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
									User.logIn(userId, username, name, age, email, gender, password);
									frame.dispose();
									new MainForm();
									return;
								}
							}
						}
					}
				}
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		
	}
	
	public boolean isAlphabetic(char character) {
        return (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z');
    }
	
	public boolean isNumeric(char character) {
        return (character >= '0' && character <= '9');
    }
	
	public boolean isAlphaNumeric(char character) {
        return (character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') || (character >= '0' && character <= '9');
    }
	
	public boolean emailValidation(String email) {
		boolean onlyOnce = false, emailEnding = true;
		int count = 0;
		for(int i = 0; i < email.length(); i++) {
			if(email.charAt(i) == '@') {
				count++;
			}
		}
		if(count == 1) {
			onlyOnce = true;
		} else {
			onlyOnce = false;
		}
		if(email.endsWith("@.com")) {
			emailEnding = false;
		}
		if(email.startsWith("@") || email.startsWith(".") || email.endsWith("@") || email.endsWith(".") || !email.endsWith(".com") || onlyOnce == false || emailEnding == false) {
			return false;
		} else {
			return true;
		}
	}

}