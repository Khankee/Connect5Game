package connect5;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;

	/* @Author: Khanspii
	 * 
	 * This is the main GUI class which starts the game and 
	 * calls other classes during the process
	 * 
	 * */

@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	//Declaration of GUI and their components
	private Container cp;
	private Connect5game game;
	int rows = 7; 
	int columns = 8;
	private int count = 0;
	private int playerOne = 0;
	private int playerTwo = 0;
	
	//image path directory
	private String imgEmptyFileName = "images/empty.png";
	private String imgYellowFilename = "images/yellow.png";
	private String imgRedFilename = "images/red.png";
	
	//Here is to prepare imageIcones to be used JComponents
	private ImageIcon icon_Empty = null;
	private ImageIcon icon_Yellow = null;
	private ImageIcon icon_Red = null;
		
	//Constructor for GUI to set up and play
	public GUI(boolean saving) {
		//The parameter saving should check that user wants to start the new game 
		//or wants to start the previous (saved) game
		if(saving) {
			game = new Connect5game("R", "Y");
			try {
				Board.recallBoard();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else{
		
		game = new Connect5game("R", "Y");
		
		//preparing imageURL for icons
		//Empty Icons
		URL imageURL = getClass().getClassLoader().getResource(imgEmptyFileName);
		if(imageURL != null) icon_Empty = new ImageIcon(imageURL);
		else System.err.println("Couldn't find file: " + imgEmptyFileName);
		
		//Yellow icons
		imageURL = getClass().getClassLoader().getResource(imgYellowFilename);
		if(imageURL != null) icon_Yellow = new ImageIcon(imageURL);
		else System.err.println("Couldn't find file: " + imgYellowFilename);
		
		//Red icons
		imageURL = getClass().getClassLoader().getResource(imgRedFilename);
		if(imageURL != null) icon_Red = new ImageIcon(imageURL);
		else System.err.println("Couldn't find file: " + imgRedFilename);
		
		cp = getContentPane();
		cp.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		//fills the panel with empty icons by rows and columns
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < columns; col++) {
				JButton button = new JButton();//declared the button which will give the action (icons)
				button.setIcon(icon_Empty);
				button.setPreferredSize(new Dimension(100,  100));
				button.setName(Integer.toString((row * 10 + col)));//for all icons set special name which is the first digit is row and second digit is column
				
				button.addActionListener(new ActionListener() {//for all icons gives action to call method "updater"
					@Override
					public void actionPerformed(ActionEvent actionEvent) {
						updater(((JButton)(actionEvent.getSource())));
						count++;
						System.out.println(count);
						if(count >= 56) JOptionPane.showMessageDialog(null, "Draw, restart the game"); //if the user clicked 56 times set "Draw"
					}
				});
				cp.add(button);
			}
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Connect 5");
		setLocation(400,100);
		setSize(840,750);
		setVisible(true);
		
		mainclass.button_pause.addActionListener(new ActionListener() {//pause the game
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Game paused");
			}
		});
		
		mainclass.button_restart.addActionListener(new ActionListener() {//restarts the game, the previous game hides
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				mainclass.theLabel.setText("The players turn counter");
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new GUI(false); //start the new game
					}
				});
			}
		});
		}		
	}
	
	private void updater(JButton button) {//this is the one of the main methods which changes the empty icons to yellow or red.
		
		int rowTimesTenPlusCol = Integer.parseInt(button.getName());//takes the coordinate of clicked button (Row *10) + column
		int col = rowTimesTenPlusCol % 10; //gets the column's position
		
		int addedRow = game.round(col);//checks for valid position if it is not, sets addedRow to -1, else returns the row position where the game should fill with Yellow or Red
		if(addedRow != -1) {
			
			JButton buttonToUpdate = ((JButton)(cp.getComponent(columns * addedRow + col)));//new button with new row of the column which already checked 
			
			if(game.isIs1playing()) {//True -> Yellow
				buttonToUpdate.setIcon(icon_Yellow);//Set with the new icon 
				playerOne++;
				mainclass.theLabel.setText("PlayerOne: " + Integer.toString(playerOne) + " PlayerTwo " + Integer.toString(playerTwo));
			} else {//False -> Red
				buttonToUpdate.setIcon(icon_Red);//Set with the new icon 
				playerTwo++;
				mainclass.theLabel.setText("PlayerOne: " + Integer.toString(playerOne) + " PlayerTwo: " + Integer.toString(playerTwo));
			}
			
			//check for the winner
			if(game.checkForWinnerInGUI(col)) {// every time checks the horizontal, vertical and diagonal positions which connected 5 icons.
				JOptionPane.showMessageDialog(null, "You have won");//Unfinished part of code, I should stop the players to continue the game
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please select the valid position"); 
			count--;
		}
	}
}


	