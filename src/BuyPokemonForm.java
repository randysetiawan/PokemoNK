import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class BuyPokemonForm implements ActionListener {
	JFrame frame = new JFrame();
	JTable pokemonTable = new JTable(new DefaultTableModel(null, new Object[]{"Pokemon ID", "Pokemon Name", "Pokemon Level", "Pokemon Type"}));
	DefaultTableModel defaultTableModel = (DefaultTableModel) pokemonTable.getModel();
	JScrollPane scrollPane = new JScrollPane(pokemonTable);
	JPanel mainPanel = new JPanel(new GridLayout(2,1));
	JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
	JPanel contentPanel = new JPanel(new GridLayout(2, 1));
	JPanel buttonsPanel = new JPanel();
	JPanel footerPanel = new JPanel(new GridLayout(2, 1));
	JPanel errorPanel = new JPanel();
	JLabel insertLabel = new JLabel ("Insert Pokemon Id");
	JLabel quantityLabel = new JLabel ("Quantity");
	JLabel errorLabel = new JLabel();
	JTextField pokemonIdTextField = new JTextField();
	JTextField quantityTextField = new JTextField();
	JButton insertButton = new JButton("Insert");
	JButton backToMainButton = new JButton("Back to main");
	Color lightBlue = new Color(51, 204, 255);
	Font contentFont = new Font("Comic Sans MS", Font.BOLD, 15);
	Font errorFont = new Font("Comic Sans MS", Font.BOLD, 15);
	
	public BuyPokemonForm() {
		initializeTable();
		mainPanel.setBackground(lightBlue);
		buttonsPanel.setBackground(lightBlue);
		contentPanel.setBackground(lightBlue);
		mainPanel.add(scrollPane);
		pokemonTable.setBackground(lightBlue);
		scrollPane.setBackground(lightBlue);
		insertLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
		quantityLabel.setFont(contentFont);
		insertLabel.setFont(contentFont);
		contentPanel.add(insertLabel);
		contentPanel.add(pokemonIdTextField);
		contentPanel.add(quantityLabel);
		contentPanel.add(quantityTextField);
		insertButton.addActionListener(this);
		backToMainButton.addActionListener(this);
		errorLabel.setFont(errorFont);
		errorLabel.setForeground(Color.RED);
		errorPanel.setBackground(lightBlue);
		errorPanel.add(errorLabel);
		buttonsPanel.add(insertButton);
		buttonsPanel.add(backToMainButton);
		footerPanel.add(buttonsPanel);
		footerPanel.add(errorPanel);
		contentPanel.setBorder(new EmptyBorder(25, 100, 25, 100));
		bottomPanel.add(contentPanel);
		bottomPanel.add(footerPanel);
		mainPanel.add(bottomPanel);
		frame.setContentPane(mainPanel);
		initializeFrame();
	}
	
	public void initializeFrame() {
		frame.setTitle("Pokemon Market");
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void initializeTable() {
		defaultTableModel.setRowCount(0);
		ResultSet resultSet = Connect.getInstance().executeQuery("SELECT * FROM pokemon;");
		try {
			while(resultSet.next()) {
				Object[] pokemonData = new Object[] {resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4)};
				defaultTableModel.addRow(pokemonData);
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
		if(e.getSource() == insertButton) {
			if(pokemonIdTextField.getText().isEmpty() || quantityTextField.getText().isEmpty()) {
				errorLabel.setText("All field must be filled");
			} else {
				int pokemonId;
				try {
					pokemonId = Integer.parseInt(pokemonIdTextField.getText());
				} catch (Exception e1) {
					pokemonId = -1;
				}
				for(int i = 0; i < pokemonTable.getRowCount(); i++) {
					if(pokemonId == Integer.parseInt(pokemonTable.getValueAt(i, 0).toString())) {
						int qty;
						try {
							qty = Integer.parseInt(quantityTextField.getText());
						} catch (Exception e3) {
							qty = -1;
							errorLabel.setText("Quantity must be numeric");
							SwingUtilities.updateComponentTreeUI(frame);
							return;
						}
						if(qty < 0) {
							errorLabel.setText("Quantity cannot be less than 0");
							SwingUtilities.updateComponentTreeUI(frame);
							return;
						} else {
							ResultSet resultSet = Connect.getInstance().executeQuery("SELECT * FROM cart WHERE UserId = " +User.getUser().getUserId() +";");
							try {
								while(resultSet.next()) {
									if(pokemonId == resultSet.getInt(1)) {
										Connect.getInstance().executeUpdate("UPDATE cart SET Quantity = " +(resultSet.getInt(3) + qty) +" WHERE UserId = " +User.getUser().getUserId() +" AND PokemonId = " +pokemonId +";");
										errorLabel.setText(null);
										SwingUtilities.updateComponentTreeUI(frame);
										JOptionPane.showMessageDialog(frame, "Update " +qty +" of " +pokemonTable.getValueAt(i, 1) +" Success");
										return;
									}
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							Connect.getInstance().executeUpdate("INSERT INTO cart VALUES ('" +pokemonId +"', '" +User.getUser().getUserId() +"', '" +qty +"');");
							errorLabel.setText(null);
							SwingUtilities.updateComponentTreeUI(frame);
							JOptionPane.showMessageDialog(frame, "Insert " +qty +" of " +pokemonTable.getValueAt(i, 1) +" Success");
							return;
						}
					}
				}
				errorLabel.setText("Pokemon must exists");
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		
	}
	
}
