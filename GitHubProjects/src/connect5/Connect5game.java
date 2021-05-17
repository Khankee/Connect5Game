package connect5;
import java.util.Random;

	/* @Author: Khanspii
	 * 
	 * This is the actual game process which connects Board class
	 * */

public class Connect5game {
	private Board board;
	private String color1;
	private String color2;
	
	// true if player one's turn;
	// false if player two's turn;
	private boolean is1playing;
	
	public Connect5game(String color1, String color2) {
		this.board = new Board();
		this.color1 = color1;
		this.color2 = color2;
		
		is1playing = (new Random()).nextBoolean();//randomly given color
	}
	
	/*
	 * which column user try to add piece to it returns -1
	 * if unsuccessful 
	 * an int of which row it was added to if success
	 * */
	public int round(int col) {//changes the order and adds piece in the multiple array, for example: Yellow started the game it will be true then changes to false (Red's turn)
		int row = -1;
		
		String color = is1playing ? color1 : color2;
		row = board.addPiece(col, color);//calls the method addPiece with information of column order and color(Y or R)
		
		if(row != -1) is1playing = !is1playing;//reverses the turn
		return row; 
	}
	
	//checks the connect 5
	public boolean checkForWinnerInGUI(int column) {
		String winningColor;
		
		//inversion 
		if(!is1playing) {
			winningColor = color1;
		} else {
			winningColor = color2;
		}
		
		return board.checkForWinner(column, winningColor);
	}
	
	public boolean isIs1playing() {
		return is1playing;
	}

}
