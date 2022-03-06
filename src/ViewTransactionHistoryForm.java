import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ViewTransactionHistoryForm implements ActionListener{
	JFrame frame = new JFrame();
	JTable headerTransactionTable = new JTable(new DefaultTableModel(null, new Object[]{"Transaction Id", "Date"}));
	JTable detailTransactionTable = new JTable(new DefaultTableModel(null, new Object[]{"Transaction Id", "Pokemon Id", "Pokemon Name", "Pokemon Level", "Pokemon Type", "Quantity"}));
	DefaultTableModel headerTransactionDefaultTableModel = (DefaultTableModel) headerTransactionTable.getModel();
	DefaultTableModel detailTransactionDefaultTableModel = (DefaultTableModel) detailTransactionTable.getModel();
	JScrollPane headerTransactionScrollPane = new JScrollPane(headerTransactionTable);
	JScrollPane detailTransactionScrollPane = new JScrollPane(detailTransactionTable);
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel contentPanel = new JPanel(new BorderLayout());
	JPanel tablePanel = new JPanel(new GridLayout(2, 1));
	JPanel buttonPanel = new JPanel();
	JLabel transactionHistoryLabel = new JLabel("Transaction History");
	JLabel selectTheRowForDetailsLabel = new JLabel("Select the rows for details");
	JButton backToMainMenuButton = new JButton("Back to Main Menu");
	Color lightBlue = new Color(51, 204, 255);
	Font headerFont = new Font("Comic Sans MS", Font.PLAIN, 28);
	Font contentFont = new Font("Comic Sans MS", Font.PLAIN, 18);

	public ViewTransactionHistoryForm() {
		initializeHeaderTransactionTable();
		mainPanel.setBackground(lightBlue);
		contentPanel.setBackground(lightBlue);
		tablePanel.setBackground(lightBlue);
		headerTransactionScrollPane.setBackground(lightBlue);
		headerTransactionTable.setBackground(lightBlue);
		detailTransactionScrollPane.setBackground(lightBlue);
		detailTransactionTable.setBackground(lightBlue);
		buttonPanel.setBackground(lightBlue);
		transactionHistoryLabel.setFont(headerFont);
		selectTheRowForDetailsLabel.setFont(contentFont);
		transactionHistoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectTheRowForDetailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(selectTheRowForDetailsLabel, BorderLayout.NORTH);
		tablePanel.setBorder(new EmptyBorder(10, 0, 25, 0));
		tablePanel.add(headerTransactionScrollPane);
		detailTransactionScrollPane.setVisible(false);
		tablePanel.add(detailTransactionScrollPane);
		backToMainMenuButton.addActionListener(this);
		buttonPanel.add(backToMainMenuButton);
		contentPanel.setBorder(new EmptyBorder(10, 0, 85, 0));
		contentPanel.add(tablePanel);
		contentPanel.add(buttonPanel, BorderLayout.SOUTH);
		mainPanel.add(contentPanel);
		mainPanel.add(transactionHistoryLabel, BorderLayout.NORTH);
		frame.add(mainPanel);
		initializeFrame();
	}
	
	public void initializeFrame() {
		frame.setTitle("View Transaction History");
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public void initializeHeaderTransactionTable() {
		headerTransactionDefaultTableModel.setRowCount(0);
		ResultSet resultSet = Connect.getInstance().executeQuery("SELECT TransactionId, Time FROM headertransaction WHERE UserId = " +User.getUser().getUserId() +";");
		try {
			while(resultSet.next()) {
				Object[] transactionData = new Object[] {resultSet.getInt(1), resultSet.getTimestamp(2)};
				headerTransactionDefaultTableModel.addRow(transactionData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		headerTransactionTable.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				
			}
			
			public void mousePressed(MouseEvent e) {
				
			}
			
			public void mouseExited(MouseEvent e) {
				
			}
			
			public void mouseEntered(MouseEvent e) {
				
			}
			
			public void mouseClicked(MouseEvent e) {
				int transactionId = (int) headerTransactionTable.getValueAt(headerTransactionTable.rowAtPoint(e.getPoint()), 0);
				initializeDetailTransactionTable(transactionId);
			}
		});
		
	}
	
	public void initializeDetailTransactionTable(int transactionId) {
		detailTransactionDefaultTableModel.setRowCount(0);
		ResultSet resultSet = Connect.getInstance().executeQuery("SELECT TransactionId, detailtransaction.PokemonId, PokemonName, PokemonLevel, PokemonType, Quantity FROM detailtransaction JOIN pokemon ON detailtransaction.PokemonId = pokemon.PokemonId WHERE TransactionId = " +transactionId +";");
		try {
			while(resultSet.next()) {
				Object[] transactionData = new Object[] {resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getInt(6)};
				detailTransactionDefaultTableModel.addRow(transactionData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		detailTransactionScrollPane.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backToMainMenuButton) {
			frame.dispose();
			new MainForm();
		}
		
	}

}
