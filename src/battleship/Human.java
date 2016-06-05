package battleship;

import java.util.Scanner;

import battleship.Ship.Orientation;
import battleship.Ship.ShipType;

public class Human extends Player {
	private static Scanner userIn = new Scanner(System.in);

	
	public Human(){
	}
	public Human(String name){
		super(name);
	}
	
	@Override
	public void setup() {
		Coordinate firstC;
		int dirVal;
		Orientation direction;
		ShipType type;
		Ship ship;
		boolean allIsWell = false;
		int x = 1;
		char y = 'A';
	
		
		System.out.println("Place your ships:");
		for (int i = 0; i < NB_SHIPS; i++){
			type = Ship.ShipType.values()[i];
			do{
				System.out.print("Enter the top left coordinate for your " + type + ":\nExample: A1 ");
				firstC = new Coordinate(userIn.next());
				do{
					System.out.print("Enter the direction for your " + type
						+ ":\nEnter 0 for HORIZONTAL or 1 for VERTICAL:");
					dirVal = userIn.nextInt();
				}while((dirVal != 0) && (dirVal != 1));
				direction = Orientation.values()[dirVal];
				ship = new Ship(type, firstC, direction);
				allIsWell = placeShip(ship);
				//System.out.println(allIsWell);
				if(!allIsWell)
					System.out.println("Your " + type + " cannot go there, choose another place:");
			}while (!allIsWell);
			System.out.println(this);
			
		}
		associate();
	}

	@Override
	public void turn(Player opponent) {
		// TODO Auto-generated method stub
		Coordinate toHit;
		boolean validHit;
		
		do{
			System.out.println(opponent + "\n" + this);
			System.out.print("Choose a coordinate:");
			toHit = new Coordinate(userIn.next());
			validHit = (toHit.exists()) && (!opponent.isHit(toHit));
			if (!validHit)
				System.out.println("That is not a valid choice;");
		}
		while(!validHit);
		
		if (opponent.hit(toHit)){
			System.out.println("It's a hit!!!");
			
			if (opponent.sunkShip(toHit))
				System.out.println("YOU SUNK MY SHIP!!!");
			
			System.out.println("Go again...");
				turn(opponent);
		}
		else
			System.out.println("It's a miss... My turn.");
		
		
	}

	@Override
	public String toString() {
		char y = 'A';
		String toString = name + ":\n ";
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
