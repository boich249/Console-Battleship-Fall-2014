package battleship;
import java.util.Random;
import java.util.Scanner;

import battleship.Ship.Orientation;

/**
 * Represents a player in the game.
 * @author Asher
 *
 */
abstract public class Player {
	public Scanner userIn1 = new Scanner(System.in);
	public Random gen1 = new Random();
	
	// Dimensions of the board (squared)
	public static final int SQUARE = 10;
	// Number of ships
	public static final int NB_SHIPS = 5;
	
	public String name;

	
	
	// Array of coordinates, represents the board
	private Coordinate [] board = new Coordinate[SQUARE * SQUARE];

	
	// Players ships
	private Ship [] allShips = new Ship[NB_SHIPS];
	
	private boolean loser = false;
	
	
	// Default constructor
	
	
	public Player(){
		initBoard();
		setup();
	}
	
	public Player(String name){
		this.name = name;
		initBoard();
		setup();
	
	}
	
	public String getName(){
		return name;
	}

	public Coordinate[] getBoard() {
		Coordinate[] getBoard = new Coordinate[board.length];
		for (int i = 0; i < board.length; i++)
			getBoard[i] = new Coordinate(board[i]);
		return getBoard;
	}

	public Ship[] getAllShips() {
		Ship[] allShips = new Ship[this.allShips.length];
		for (int i = 0; i < allShips.length; i++)
			allShips[i] = new Ship(this.allShips[i]);
		return allShips;
	}


	public void setAllShips(Ship[] allShips) {
		for (int i = 0; i < allShips.length; i++)
			this.allShips[i] = new Ship(allShips[i]);
	}
	
	public void setOneShip(Ship ship, int i) {
		this.allShips[i] = new Ship(ship);
	}
	

	public boolean isLoser() {
		if (loser == true)
			
			return true;
		
		else
			
			for (int i = 0; i < allShips.length; i++)
				
				if (!(allShips[i].isSunk()))
					
					return false;
		
		return (loser = true);
	}


	public void setLoser(boolean loser) {
		this.loser = loser;
	}


	// Initializes a board
	public void initBoard(){
		for (int i = 0; i < SQUARE; i++)
			for (int j = 0; j < SQUARE; j++)
				board[(i * SQUARE) + j] = new Coordinate((1 + j), ('A' + i));
	}
	
	// Indicates if a ship is located on this coordinate
	public boolean coHasShip(Coordinate co){
		for (int i = 0; i < allShips.length; i++){
			
			if (!(allShips[i] == null))

			
				for (int j = 0; j < allShips[i].getLocation().length; j++)
				
					if (co.equals(allShips[i].getLocation()[j]))
					
						return true;
		}
		return false;
					
	}
	
	// Indicates if the coordinate exists on the board
	public boolean coExists(Coordinate co){
		return co.exists();
	}
	
	// Indicates if a ship is allowed to be placed on this coordinate
	public boolean coIsOk(Coordinate co){
		return (coExists(co)) && !(coHasShip(co));
	}
	
	// Places a ship on the board
	public boolean placeShip(Ship ship, Coordinate firstC, Orientation direction){
		if (!ship.place(firstC, direction))
			return false;

		return placeShip(ship);

	}
	
	public boolean placeShip(Ship ship){
		//System.out.println(ship.place());
		if(!ship.place())
			return false;
		
		for(int i = 0; i < ship.getLocation().length; i++)
			if (!coIsOk(ship.getLocation()[i]))
			
				return false;
		
		allShips[ship.getType().ordinal()] = new Ship(ship);
		return true;
	}
	
	// Associates ships coordinates with boards coordinates
	public void associate(){
		for (int i = 0; i < allShips.length; i++)
			for (int j = 0; j < allShips[i].getLocation().length; j++)
				for (int k = 0; k < board.length; k++)
					if (board[k].equals(allShips[i].getLocation()[j]))
					{
						board[k] = allShips[i].location[j];
						break;
					}
	}
	
	// String representation of coordinates used in printing defense board
	public String coToStringDef(Coordinate co){
		if (coHasShip(co)) 
			for (int i = 0; i < allShips.length; i++)
			{	
				if (allShips[i] == null)
					continue;
				for (int j = 0; j < allShips[i].getLocation().length; j++)
					if (allShips[i].getLocation()[j].equals(co))
						if (allShips[i].getLocation()[j].isHit())
							return "X";
						else
							return "O";
			}
		else if (co.isHit())
				return "*";
	
		return " ";
			
	}
	
	// String representation of coordinates used in printing offense board
	public String coToStringOff(Coordinate co){
		if (coHasShip(co)) 
			for (int i = 0; i < allShips.length; i++)
			{	
				if (allShips[i] == null)
					continue;
				for (int j = 0; j < allShips[i].getLocation().length; j++)
					if (allShips[i].getLocation()[j].equals(co))
						if (allShips[i].getLocation()[j].isHit())
							return "X";
						
			}			
		else if (isHit(co))
			return "*";
		
		return " ";
	}

	// Indicates if a coordinate on the board is hit
	public boolean isHit(Coordinate co){
		for (int i = 0; i < allShips.length; i++)
		{	
			if (allShips[i] == null)
				continue;
			
			for (int j = 0; j < allShips[i].getLocation().length; j++)

				if (allShips[i].getLocation()[j].equals(co))
				
					return allShips[i].getLocation()[j].isHit();

		}
		return false;
	}
	
	// Attempts a "Hit" on a coordinate, returns true if hits a ship
	public boolean hit(Coordinate co){
		for (int i = 0; i < allShips.length; i++)
		{	
			if (allShips[i] == null)
				continue;
			
			for (int j = 0; j < allShips[i].getLocation().length; j++)

				if (allShips[i].getLocation()[j].equals(co))
			
					allShips[i].hit(co);
		}			
		
		for (int k = 0; k < board.length; k++)
		
			
			if (board[k].equals(co))
			
				board[k].hit();
				
	//	System.out.println(board[0].isHit());
		return coHasShip(co);
	}
	
	// Indicates if the most recent hit sunk a ship
	public boolean sunkShip(Coordinate co){
		for (int i = 0; i < allShips.length; i++)
		{	
			if (allShips[i] == null)
				continue;
			
			for (int j = 0; j < allShips[i].getLocation().length; j++)

				if (allShips[i].getLocation()[j].equals(co))
					return allShips[i].isSunk();
		}
		return false;
	}
	
	// Private, returns actual reference, locates a ship  based on a coordinate
	private Ship findShip(Coordinate co){
		for (int i = 0; i < allShips.length; i++)
		{	
			if (allShips[i] == null)
				continue;
			
			for (int j = 0; j < allShips[i].getLocation().length; j++)

				if (allShips[i].getLocation()[j].equals(co))
					return allShips[i];
		}
		return null;
	}
	
	// Private, returns actual reference, returns the reference of the equal ship in allShips
	private Ship manipShip(Ship ship){
		for (int i = 0; i < allShips.length; i++)
			if (allShips[i].equals(ship))
				return allShips[i];
		return null;
	}
	
	abstract public void setup();
	
	abstract public void turn(Player opponent);
	
	abstract public String toString();
	
	public String toStringAlt() {
		// TODO Auto-generated method stub
		
		char y = 'A';
		String toString = name + "\n ";
		for (int i = 0; i < SQUARE; i++)
			toString += " " + (i + 1);
		for (int i = 0; i < SQUARE; i++){				
			toString += "\n" + y++ + "|";
			for (int j = 0; j < SQUARE; j++)
				toString += coToStringDef(getBoard()[(i * SQUARE) + j]) + "|";
		}
			return toString;
	}

}
