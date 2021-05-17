package connect5;

import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import java.lang.String;

	/* @Author: Arukhan
	 * 
	 * Connect 5 game 
	 * This is the main class where you should run the program
	 * Before you start the program be sure that all methods and classes are in the same 
	 * package, also the folder images are outside of the package connect5
	 * 
	 * Comments will help you with navigation in my code
	 * */


@SuppressWarnings("serial")
public class mainclass extends JFrame implements ActionListener{
	
	//Declaration of Buttons and label
	JPanel mainMenuPanel;
	JButton button_start;
	static JButton button_pause;
	static JButton button_restart;
	JButton button_save;
	JButton button_recall;
	static JLabel theLabel;
	
	//Main class of Menu
	public mainclass() {
		
		mainMenuPanel = new JPanel();
		mainMenuPanel.setLayout(null);
		mainMenuPanel.setSize(800,900);
		
		button_start = new JButton("Start the game");
		button_start.setBounds(10,10,215,50);
		
		button_pause = new JButton("Pause the game");
		button_pause.setBounds(10,70,215,50);
		
		button_restart = new JButton("Restart the game");
		button_restart.setBounds(10,130,215,50);
		
		button_save = new JButton("Save the game");
		button_save.setBounds(10,190,215,50);
		
		button_recall = new JButton("Recall the game");
		button_recall.setBounds(10,250,215,50);
		
		theLabel = new JLabel("The players turn counter");
		theLabel.setBounds(10, 310, 160, 30 );
		
		add(mainMenuPanel);
		mainMenuPanel.add(button_start);
		mainMenuPanel.add(button_pause);
		mainMenuPanel.add(button_restart);
		mainMenuPanel.add(button_save);
		mainMenuPanel.add(button_recall);
		mainMenuPanel.add(theLabel);
		
		button_start.addActionListener(this);
		button_save.addActionListener(this);
		button_recall.addActionListener(this);
		
		setTitle("Menu");
		setLocationRelativeTo(null);
		setSize(400,500);
		setLocation(600,200);
		setVisible(true);
		setBounds(100, 100, 247, 337);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { //actionPerformed adds to the buttons the action
		if(e.getSource() == button_start){ //This button calls GUI of the main game
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new GUI(false); //start the game
				}
			});
		}else if(e.getSource() == button_save) {//Not finished button which saves the game into the txt.file
			if(theLabel.getText().equals("The players turn counter") || theLabel.getText().equals("You should start the game!"))
				theLabel.setText("You should start the game!");
			else
				try {
					Board.saveBoard();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				theLabel.setText("The game saved!");
		}else if(e.getSource() == button_recall) {//Not finished button which starts the new game from and gets information of saved game from txt.file
			new GUI(true);
		}
		
	}
	
	public static void main(String[] args) {new mainclass();}
}
