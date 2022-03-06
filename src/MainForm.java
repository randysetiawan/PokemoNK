import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainForm implements ActionListener{
	JFrame frame = new JFrame();
	JPanel mainPanel = new JPanel(new GridLayout(1, 1));
	JLabel pokemonkLabel = new JLabel("PokemoNK");
	JMenuBar menuBar = new JMenuBar();
	JMenu userMenu = new JMenu("User");
	JMenu adventureMenu = new JMenu("Adventure");
	JMenu manageMenu = new JMenu("Manage");
	JMenuItem signOutMenuItem = new JMenuItem("Sign Out");
	JMenuItem pokemonMarketMenuItem = new JMenuItem("Pokemon Market");
	JMenuItem bagMenuItem = new JMenuItem("Bag");
	JMenu transactionMenu = new JMenu("Transaction");
	JMenuItem viewTransactionHistoryMenuItem = new JMenuItem("View Transaction History");
	JMenuItem managePokemonMenuItem = new JMenuItem("Manage Pokemon");
	Color lightBlue = new Color(51, 204, 255);

	public MainForm() {
		mainPanel.setBackground(lightBlue);
		pokemonkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pokemonkLabel.setVerticalAlignment(SwingConstants.CENTER);
		pokemonkLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 72));
		mainPanel.add(pokemonkLabel);
		frame.setJMenuBar(menuBar);
		menuBar.add(userMenu);
		signOutMenuItem.addActionListener(this);
		userMenu.add(signOutMenuItem);
		if(!User.getUser().getUsername().equals("admin")) {
			menuBar.add(adventureMenu);
			pokemonMarketMenuItem.addActionListener(this);
			bagMenuItem.addActionListener(this);
			viewTransactionHistoryMenuItem.addActionListener(this);
			adventureMenu.add(pokemonMarketMenuItem);
			adventureMenu.add(bagMenuItem);
			menuBar.add(transactionMenu);
			transactionMenu.add(viewTransactionHistoryMenuItem);
		} else {
			menuBar.add(manageMenu);
			managePokemonMenuItem.addActionListener(this);
			manageMenu.add(managePokemonMenuItem);
		}
		
		frame.setContentPane(mainPanel);
		initializeFrame();
	}
	
	public void initializeFrame() {
		if(!User.getUser().getUsername().equals("admin")) {
			frame.setTitle("Welcome User");
		} else {
			frame.setTitle("Welcome Admin");
		}
		frame.setSize(1024, 768);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == signOutMenuItem) {
			User.logOut();
			frame.dispose();
			new LoginForm();
		}
		if(e.getSource() == pokemonMarketMenuItem) {
			frame.dispose();
			new BuyPokemonForm();
		}
		if(e.getSource() == bagMenuItem) {
			frame.dispose();
			new BagForm();
		}
		if(e.getSource() == viewTransactionHistoryMenuItem) {
			frame.dispose();
			new ViewTransactionHistoryForm();
		}
		if(e.getSource() == managePokemonMenuItem) {
			frame.dispose();
			new ManagePokemonForm();
		}
		
	}

}


