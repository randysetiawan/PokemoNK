import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class BagForm implements ActionListener{
	JFrame frame = new JFrame();
	JTable cartTable = new JTable(new DefaultTableModel(null, new Object[]{"Pokemon ID", "Pokemon Name", "Pokemon Level", "Pokemon Type", "Quantity"}));
	DefaultTableModel defaultTableModel = (DefaultTableModel) cartTable.getModel();
	JScrollPane scrollPane = new JScrollPane(cartTable);
	JPanel mainPanel = new JPanel(new GridLayout(2, 1));
	JPanel footerPanel = new JPanel(new BorderLayout(0, 0));
	JPanel contentPanel = new JPanel(new GridLayout(3, 2));
	JPanel emptyPanel = new JPanel();
	JLabel manageCartLabel = new JLabel("Manage Cart");
	JLabel errorLabel = new JLabel(" ");
	JLabel pokemonIdLabel = new JLabel("Pokemon Id :");
	JTextField pokemonIdTextField = new JTextField();
	JButton deleteButton = new JButton("Delete");
	JButton checkOutButton = new JButton("Checkout");
	JButton backToMainButton = new JButton("Back to Main");
	Color lightBlue = new Color(51, 204, 255);
	Font headerFont = new Font("Comic Sans MS", Font.PLAIN, 28);
	Font contentFont = new Font("Comic Sans MS", Font.BOLD, 15);
	Font errorFont = new Font("Comic Sans MS", Font.BOLD, 15);

	public BagForm() {
		initializeTable();
		mainPanel.setBackground(lightBlue);
		cartTable.setBackground(lightBlue);
		scrollPane.setBackground(lightBlue);
		footerPanel.setBackground(lightBlue);
		contentPanel.setBackground(lightBlue);
		emptyPanel.setBackground(lightBlue);
		manageCartLabel.setFont(headerFont);
		pokemonIdLabel.setFont(contentFont);
		pokemonIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setFont(errorFont);
		errorLabel.setForeground(Color.RED);
		deleteButton.addActionListener(this);
		checkOutButton.addActionListener(this);
		backToMainButton.addActionListener(this);
		mainPanel.add(scrollPane);
		manageCartLabel.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		footerPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
		footerPanel.add(manageCartLabel, BorderLayout.NORTH);
		footerPanel.add(errorLabel, BorderLayout.SOUTH);
		contentPanel.setBorder(new EmptyBorder(50, 250, 50, 250));
		contentPanel.add(pokemonIdLabel);
		contentPanel.add(pokemonIdTextField);
		contentPanel.add(emptyPanel);
		contentPanel.add(deleteButton);
		contentPanel.add(checkOutButton);
		contentPanel.add(backToMainButton);
		footerPanel.add(contentPanel, BorderLayout.CENTER);
		mainPanel.add(footerPanel);
		frame.setContentPane(mainPanel);
		initializeFrame();
	}
	
	public void initializeFrame() {
		frame.setTitle("Manage Bag");
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void initializeTable() {
		defaultTableModel.setRowCount(0);
		ResultSet resultSet = Connect.getInstance().executeQuery("SELECT pokemon.PokemonId, PokemonName, PokemonLevel, PokemonType, Quantity FROM cart JOIN pokemon ON cart.PokemonId = pokemon.PokemonId WHERE UserId = " +User.getUser().getUserId() +";");
		try {
			while(resultSet.next()) {
				Object[] cartData = new Object[] {resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getInt(5)};
				defaultTableModel.addRow(cartData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backToMainButton) {
			frame.dispose();
			new MainForm();
		}
		if(e.getSource() == deleteButton) {
			if(cartTable.getRowCount() == 0) {
				errorLabel.setText("Pokemon Id must be filled");
			} else if(pokemonIdTextField.getText().isEmpty()){
				errorLabel.setText("Pokemon Id must be filled");
			} else {
				int pokemonId;
				try {
					pokemonId = Integer.parseInt(pokemonIdTextField.getText());
				} catch (Exception e1) {
					pokemonId = -1;
				}
				for(int i = 0; i < cartTable.getRowCount(); i++) {
					if(pokemonId == Integer.parseInt(cartTable.getValueAt(i, 0).toString())) {
						Connect.getInstance().executeUpdate("DELETE FROM cart WHERE UserId = " +User.getUser().getUserId() +" AND PokemonId = " +pokemonId +";");
						errorLabel.setText("Deleted Succesfully");
						initializeTable();
						SwingUtilities.updateComponentTreeUI(frame);
						return;
					}
				}
				errorLabel.setText("Pokemon Doesn't Exists");
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		if(e.getSource() == checkOutButton) {
			if(cartTable.getRowCount() == 0) {
				errorLabel.setText("Cart is empty");
			} else {
				Connect.getInstance().executeUpdate("INSERT INTO headertransaction VALUES (NULL, " +User.getUser().getUserId() +", CURRENT_TIMESTAMP());");
				int headerTransactionId = 0;
				ResultSet resultSet = Connect.getInstance().executeQuery("SELECT * FROM headertransaction ORDER BY Time DESC LIMIT 1;");
				try {
					resultSet.next();
					headerTransactionId = resultSet.getInt(1);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				for(int i = 0; i < cartTable.getRowCount(); i++) {
					Connect.getInstance().executeUpdate("INSERT INTO detailtransaction VALUES (" +headerTransactionId +", " +Integer.parseInt(cartTable.getValueAt(i, 0).toString()) +", " +Integer.parseInt(cartTable.getValueAt(i, 4).toString()) +");");
				}
				Connect.getInstance().executeUpdate("DELETE FROM cart WHERE UserId = " +User.getUser().getUserId() +";");
				initializeTable();
				errorLabel.setText("Checked Out Succesfully");
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		
	}

}
