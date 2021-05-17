package connect5;

import java.io.*;

	/* @Author: Arukhan
	 * 
	 * This is the Board class with methods to check the winner and work with board
	 * */

public class Board {
	
	private static final int rows = 7; // rows
	private static final int columns = 8; // Columns
	
	static Piece [][]ourBoard = new Piece[rows][columns];//2D array board
	
	public static int getColumns() { return columns;} 
	
	public boolean checkForWinner(int col, String winningColor) {//Checks the connect 5 by 4 directions 
		for (int row = 0; row < rows; row++) {                   //(Horizontal, Vertical, Left Diagonal and Right Diagonal)
			if(ourBoard[row][col] != null) {
				// if this reaches 0, we have winner
				int winningStreak = 4;
				
				//vertical
				for(int winRow = row + 1; winRow < rows; winRow++) {
					if(ourBoard[winRow][col].getColor().equals(winningColor)) {
						winningStreak--;
						if(winningStreak == 0) { //Finds the winner
							return true;
						}
					}else{
						winningStreak = 4; //winning streak reseted
					}
				}
				
				winningStreak = 5;//Reset the winning streak
		
				//horizontal 
				for(int winCol = col - 4; winCol <= col + 4; winCol++) {
					if(winCol < 0) continue;
					if(winCol >= columns) break;
					
					if(ourBoard[row][winCol] != null && ourBoard[row][winCol].getColor().equals(winningColor)) {
						winningStreak--;
						if(winningStreak == 0) { //Finds the winner
							return true;
						}
					}else winningStreak = 5; //winning streak reseted
				}
				
				winningStreak = 5; //Reset the winning streak
				
				//diagonal check left and right
				if(checkDiagonal(row, col, winningColor, false))return true;//left Diagonal checker 
				if(checkDiagonal(row, col, winningColor, true))return true;//right Diagonal checker
				
				return false;
			}
		}
		return false;
	}
	
	private boolean checkDiagonal(int row, int col, String winningColor, boolean rightDiagonal) { // Boolean is needed to check right or left diagonal
		int winningStreak = 5;
		int reverser = 1;
		
		if (rightDiagonal) {//The logic is simple, if it is true it checks right diagonal, if it is true, it checks left diagonal
			reverser = -1;
		}
		for(int winRow = row - 4, winCol = col - (4 * reverser); winRow <= row + 4; winRow++, winCol += reverser) {
			//The condition is complicated, but to be simple, 
			//the way of finding diagonal position is to add 1 to row and column or minus 1
			//and find the diagonal position which is not empty and equals to the winning color
			//if it is count the winning streak wile it will be not reached 0 which returns "True"
			if(!rightDiagonal) {
				if(winRow < 0 || winCol < 0)continue;
				if(winRow >= rows || winCol >= columns) break;//if it is outside of board, break it
			} else {
				if(winRow < 0 || winCol >= columns)continue;
				if(winRow >= rows || winCol < 0)break;//if it is outside of board, break it	
			}
			
			if(ourBoard[winRow][winCol] != null && ourBoard[winRow][winCol].getColor().equals(winningColor)) {
				winningStreak--;
				if(winningStreak == 0) {return true;} //Finds the winner
			}else winningStreak = 5;
		}
		
		return false;
	}

	public int addPiece(int colToAdd, String color) { // fill the piece by choosing the columns
	
			if (ourBoard[0][colToAdd] == null) {//Checks that column is empty to continue the game
				int addedRow = -1;
				for (int row = rows - 1; row >= 0; row--) {//from the down side of the board checks that is not empty
					if (ourBoard[row][colToAdd] == null) {//if it is not empty,set with new piece and returns the row coordination
						ourBoard[row][colToAdd] = new Piece();
						ourBoard[row][colToAdd].setColor(color);
						addedRow = row;
						break;
					} 
				}
				return addedRow;
			} else {return -1;}
	}
		
	public static void saveBoard() throws IOException{ //Method which saves all position of not empty icons [Row][Column][Color]
		FileWriter writehandle = new FileWriter("C:\\Users\\Public\\Documents\\connect5.txt");
		BufferedWriter bw = new BufferedWriter(writehandle);
		
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				if (ourBoard[row][col] == null) bw.write(Integer.toString(row) + Integer.toString(col) + ourBoard[row][col].getColor() + " ");
			}
		} 
		
		bw.close();
		writehandle.close();
	}
	
	public static void recallBoard() throws IOException{//Method which should read the txt.file and declare the array with pieces.
		FileReader readhandle = new FileReader("C:\\Users\\Public\\Documents\\connect5.txt");
		BufferedReader br = new BufferedReader(readhandle);
		
		String line = null;
		while((line = br.readLine()) != null){
			System.out.println(line);
			String[] words = line.split(" ");
			System.out.println(words.length + " Pieces");
		}
		br.close();
		readhandle.close();
	}

	public Board() {
		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < columns; col++) {
				ourBoard[row][col] = null;
			}
		}
	}
}
