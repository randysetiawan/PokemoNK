import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ManagePokemonForm implements ActionListener{
	JFrame frame = new JFrame();
	JPanel mainPanel = new JPanel(new GridLayout(2, 1));
	JPanel contentPanel = new JPanel(new GridLayout(0, 3));
	JPanel insertPanel = new JPanel(new BorderLayout());
	JPanel insertContentPanel = new JPanel(new GridLayout(3, 3));
	JPanel deletePanel = new JPanel(new BorderLayout());
	JPanel deleteContentPanel = new JPanel(new GridLayout(2, 2));
	JPanel updatePanel = new JPanel(new BorderLayout());
	JPanel updateContentPanel = new JPanel(new GridLayout(4, 2));
	JPanel footerPanel = new JPanel(new BorderLayout());
	JPanel emptyPanel = new JPanel();
	JTable pokemonTable = new JTable(new DefaultTableModel(null, new Object[]{"Pokemon Id", "Pokemon Name", "Pokemon Level", "Pokemon Type"}));
	DefaultTableModel pokemonDefaultTableModel = (DefaultTableModel) pokemonTable.getModel();
	JScrollPane pokemonScrollPane = new JScrollPane(pokemonTable);
	JLabel pokemonNameLabel = new JLabel("Pokemon Name :");
	JLabel pokemonLevelLabel = new JLabel("Pokemon Level :");
	JLabel pokemonTypeLabel = new JLabel("Pokemon Type :");
	JLabel pokemonIdLabel = new JLabel("Pokemon ID :");
	JLabel pokemonNameLabel2 = new JLabel("Pokemon Name :");
	JLabel pokemonLevelLabel2 = new JLabel("Pokemon Level :");
	JLabel pokemonTypeLabel2 = new JLabel("Pokemon Type :");
	JLabel pokemonIdLabel2 = new JLabel("Pokemon ID :");
	JLabel errorLabel = new JLabel(" ");
	JTextField pokemonNameInsertTextField = new JTextField();
	JTextField pokemonLevelInsertTextField = new JTextField();
	JTextField pokemonTypeInsertTextField = new JTextField();
	JTextField pokemonIdDeleteTextField = new JTextField();
	JTextField pokemonIdUpdateTextField = new JTextField();
	JTextField pokemonNameUpdateTextField = new JTextField();
	JTextField pokemonLevelUpdateTextField = new JTextField();
	JTextField pokemonTypeUpdateTextField = new JTextField();
	JButton insertButton = new JButton("Insert");
	JButton deleteButton = new JButton("Delete");
	JButton updateButton = new JButton("Update");
	JButton backToMainButton = new JButton("Back to Main");
	Font contentFont = new Font("Comic Sans MS", Font.BOLD, 15);
	Font errorFont = new Font("Comic Sans MS", Font.BOLD, 15);
	Color lightBlue = new Color(51, 204, 255);

	public ManagePokemonForm() {
		initializeTable();
		contentPanel.setBorder(new EmptyBorder(25, 25, 25, 75));
		insertContentPanel.setBorder(new EmptyBorder(0, 0, 25, 0));
		deleteContentPanel.setBorder(new EmptyBorder(0, 0, 125, 0));
		updateContentPanel.setBorder(new EmptyBorder(0, 0, 25, 0));
		backToMainButton.addActionListener(this);
		insertButton.addActionListener(this);
		deleteButton.addActionListener(this);
		updateButton.addActionListener(this);
		pokemonNameLabel.setFont(contentFont);
		pokemonLevelLabel.setFont(contentFont);
		pokemonTypeLabel.setFont(contentFont);
		pokemonIdLabel.setFont(contentFont);
		pokemonIdLabel2.setFont(contentFont);
		pokemonNameLabel2.setFont(contentFont);
		pokemonLevelLabel2.setFont(contentFont);
		pokemonTypeLabel2.setFont(contentFont);
		errorLabel.setFont(errorFont);
		errorLabel.setForeground(Color.RED);
		mainPanel.setBackground(lightBlue);
		footerPanel.setBackground(lightBlue);
		pokemonScrollPane.setBackground(lightBlue);
		pokemonTable.setBackground(lightBlue);
		contentPanel.setBackground(lightBlue);
		insertPanel.setBackground(lightBlue);
		insertContentPanel.setBackground(lightBlue);
		deletePanel.setBackground(lightBlue);
		deleteContentPanel.setBackground(lightBlue);
		updatePanel.setBackground(lightBlue);
		updateContentPanel.setBackground(lightBlue);
		emptyPanel.setBackground(lightBlue);
		pokemonNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pokemonLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pokemonTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pokemonIdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pokemonIdLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		pokemonNameLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		pokemonLevelLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		pokemonTypeLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		insertContentPanel.add(pokemonNameLabel);
		insertContentPanel.add(pokemonNameInsertTextField);
		insertContentPanel.add(pokemonLevelLabel);
		insertContentPanel.add(pokemonLevelInsertTextField);
		insertContentPanel.add(pokemonTypeLabel);
		insertContentPanel.add(pokemonTypeInsertTextField);
		insertPanel.add(insertContentPanel, BorderLayout.CENTER);
		insertPanel.add(insertButton, BorderLayout.SOUTH);
		deleteContentPanel.add(pokemonIdLabel);
		deleteContentPanel.add(pokemonIdDeleteTextField);
		deleteContentPanel.add(emptyPanel);
		deleteContentPanel.add(deleteButton);
		deletePanel.add(deleteContentPanel, BorderLayout.CENTER);
		deletePanel.add(backToMainButton, BorderLayout.SOUTH);
		updateContentPanel.add(pokemonIdLabel2);
		updateContentPanel.add(pokemonIdUpdateTextField);
		updateContentPanel.add(pokemonNameLabel2);
		updateContentPanel.add(pokemonNameUpdateTextField);
		updateContentPanel.add(pokemonLevelLabel2);
		updateContentPanel.add(pokemonLevelUpdateTextField);
		updateContentPanel.add(pokemonTypeLabel2);
		updateContentPanel.add(pokemonTypeUpdateTextField);
		updatePanel.add(updateContentPanel, BorderLayout.CENTER);
		updatePanel.add(updateButton, BorderLayout.SOUTH);
		contentPanel.add(insertPanel);
		contentPanel.add(deletePanel);
		contentPanel.add(updatePanel);
		footerPanel.add(contentPanel, BorderLayout.CENTER);
		footerPanel.add(errorLabel, BorderLayout.SOUTH);
		mainPanel.add(pokemonScrollPane);
		mainPanel.add(footerPanel);
		frame.add(mainPanel);
		initializeFrame();
	}
	
	public void initializeFrame() {
		frame.setTitle("Welcome Admin");
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void initializeTable() {
		pokemonDefaultTableModel.setRowCount(0);
		ResultSet resultSet = Connect.getInstance().executeQuery("SELECT * FROM pokemon;");
		try {
			while(resultSet.next()) {
				Object[] cartData = new Object[] {resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4)};
				pokemonDefaultTableModel.addRow(cartData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backToMainButton) {
			new MainForm();
			frame.dispose();
			
		}
		if(e.getSource() == insertButton) {
			if(pokemonNameInsertTextField.getText().isEmpty() || pokemonLevelInsertTextField.getText().isEmpty() || pokemonTypeInsertTextField.getText().isEmpty()) {
				errorLabel.setText("All field must be filled");
			} else {
				String pokemonName = pokemonNameInsertTextField.getText(), pokemonType = pokemonTypeInsertTextField.getText();
				int pokemonLevel;
				try {
					pokemonLevel = Integer.parseInt(pokemonLevelInsertTextField.getText());
				} catch (Exception e2) {
					pokemonLevel = -1;
					errorLabel.setText("Pokemon level must be numeric");
					SwingUtilities.updateComponentTreeUI(frame);
					return;
				}
				if(pokemonLevel < 1) {
					errorLabel.setText("Pokemon level must be more than 0");
				} else {
					Connect.getInstance().executeUpdate("INSERT INTO pokemon VALUES (NULL, '" +pokemonName +"', " +pokemonLevel +", '" +pokemonType +"');");
					errorLabel.setText("Inserted Succesfully");
					initializeTable();
				}
			}
			
		}
		if(e.getSource() == deleteButton) {
			if(pokemonIdDeleteTextField.getText().isEmpty()) {
				errorLabel.setText("Pokemon ID must be filled");
			} else {
				int pokemonId;
				try {
					pokemonId = Integer.parseInt(pokemonIdDeleteTextField.getText());
				} catch (Exception e2) {
					pokemonId = -1;
					errorLabel.setText("Pokemon level must be numeric");
					SwingUtilities.updateComponentTreeUI(frame);
					return;
				}
				for(int i = 0; i < pokemonTable.getRowCount(); i++) {
					if(pokemonId == (int) pokemonTable.getValueAt(i, 0)) {
						Connect.getInstance().executeUpdate("DELETE FROM pokemon WHERE PokemonId = " +pokemonId +";");
						errorLabel.setText("Deleted Succesfully");
						initializeTable();
						SwingUtilities.updateComponentTreeUI(frame);
						return;
					}
				}
				errorLabel.setText("Pokemon doesn't exists");
			}
			
		}
		if(e.getSource() == updateButton) {
			if(pokemonIdUpdateTextField.getText().isEmpty() || pokemonNameUpdateTextField.getText().isEmpty() || pokemonLevelUpdateTextField.getText().isEmpty() || pokemonTypeUpdateTextField.getText().isEmpty()) {
				errorLabel.setText("All field must be filled");
			} else {
				String pokemonName = pokemonNameUpdateTextField.getText(), pokemonType = pokemonTypeUpdateTextField.getText();
				int pokemonId, pokemonLevel;
				try {
					pokemonId = Integer.parseInt(pokemonIdUpdateTextField.getText());
				} catch (Exception e2) {
					pokemonId = -1;
					errorLabel.setText("Pokemon ID must be numeric");
					SwingUtilities.updateComponentTreeUI(frame);
					return;
				}
				try {
					pokemonLevel = Integer.parseInt(pokemonLevelUpdateTextField.getText());
				} catch (Exception e2) {
					pokemonLevel = -1;
					errorLabel.setText("Pokemon level must be numeric");
					SwingUtilities.updateComponentTreeUI(frame);
					return;
				}
				if(pokemonLevel < 1) {
					errorLabel.setText("Pokemon level must be more than 0");
				} else {
					for(int i = 0; i < pokemonTable.getRowCount(); i++) {
						if(pokemonId == (int) pokemonTable.getValueAt(i, 0)) {
							Connect.getInstance().executeUpdate("UPDATE pokemon SET PokemonName = '" +pokemonName +"', PokemonLevel = " +pokemonLevel +", PokemonType = '" +pokemonType +"' WHERE PokemonId = " +pokemonId +";");
							errorLabel.setText("Updated Succesfully");
							initializeTable();
							SwingUtilities.updateComponentTreeUI(frame);
							return;
						}
					}
				}
				errorLabel.setText("Pokemon doesn't exists");
			}
			
		}
		SwingUtilities.updateComponentTreeUI(frame);
		
	}
	
}
