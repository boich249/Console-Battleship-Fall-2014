package battleship;



import battleship.Ship.Orientation;
import battleship.Ship.ShipType;

import java.util.Random;


public class Comp extends Player {
	
	private Coordinate[] target;
	private Ship.Orientation targetIs;
	//private Random gen = new Random();
	
	public Comp() {
		super("Comp");
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		Coordinate firstC;
		Orientation direction;
		ShipType type;
		Ship ship;
		boolean allIsWell = false;
		Random gen = new Random();
		
		//System.out.println("Place your ships:");
		for (int i = 0; i < NB_SHIPS; i++){
			type = Ship.ShipType.values()[i];
			do{
				//System.out.print("Enter the top left coordinate for your " + type + ":\nExample: A1");
				firstC = new  Coordinate(getBoard()[gen.nextInt((getBoard().length))]);
				//do{
					//System.out.print("Enter the direction for your " + type
						//+ ":\n Enter 0 for HORIZONTAL or 1 for VERTICAL");
					//dirVal = gen.nextInt(Orientation.values().length);
				//}while((dirVal != 0) && (dirVal != 1));
				direction = Orientation.values()[gen.nextInt(Orientation.values().length)];
				ship = new Ship(type, firstC, direction);
				allIsWell = placeShip(ship);
				//if(!allIsWell)
					//System.out.println("Your " + type + " cannot go there, choose another place:");
			}while (!allIsWell);
			//System.out.println(this);
			
		}

		associate();
	}

	@Override
	public void turn(Player opponent) {
		// TODO Auto-generated method stub
		Coordinate toHit;
		boolean validHit;
		Random gen = new Random();
		do{
			System.out.println(this + "\n" + opponent);
			if(target == null)
				toHit = new Coordinate(getBoard()[gen.nextInt(getBoard().length)]);
			else
				toHit = hunt(opponent);
			
			validHit = (toHit.exists()) && (!opponent.isHit(toHit));
		
		}
		while(!validHit);
		
		if (opponent.hit(toHit)){
			System.out.println("You've been hit!!!");
			recordHit(toHit);
			if (opponent.sunkShip(toHit)){
				System.out.println("I SUNK YOUR SHIP!!!");
				target = null;
				targetIs = null;
			}
			System.out.println("I go again...");
				turn(opponent);
		}
		else
			System.out.println("It's a miss... Your turn.");
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		char y = 'A';
		String toString = "Comp:\n ";
		for (int i = 0; i < SQUARE; i++)
			toString += " " + (i + 1);
		for (int i = 0; i < SQUARE; i++){				
			toString += "\n" + y++ + "|";
			for (int j = 0; j < SQUARE; j++)
				toString += coToStringOff(getBoard()[(i * SQUARE) + j]) + "|";
		}
			return toString;
	}
	
	private void recordHit(Coordinate newHit){
		// First hit on ship
		if (target == null){
			target = new Coordinate[1];
			target[0] = new Coordinate(newHit);
		}
		// Checks if ship is sunk
		else if (sunkShip(newHit)){
			target = null;
			targetIs = null;
		}
		// Second hit on ship
		else if (target.length == 2){
			if (target[0].above().equals(target[1]) || target[0].below().equals(target[1]))
				targetIs = Orientation.VERTICAL;
			else if (target[0].onRight().equals(target[1]) || target[0].onLeft().equals(target[1]))
				targetIs = Orientation.HORIZONTAL;
		}
		// Subsequent hits
		else{
			Coordinate[] temp = new Coordinate[target.length + 1];
			for (int i = 0; i < target.length; i++)
				temp[i] = target[i];
			temp[temp.length - 1] = new Coordinate(newHit);
		}
	}
		
		private Coordinate hunt(Player opponent){
			Coordinate toHit;
			boolean inChase = false;
			Random gen = new Random();
			do{
				toHit = new Coordinate(getBoard()[gen.nextInt(getBoard().length)]);
				if (targetIs == Orientation.VERTICAL){
					for (int i = 0; i < target.length; i++)
						if ((target[i].above().equals(toHit) || target[i].below().equals(toHit)) && !opponent.isHit(toHit)){
							inChase = true;
							return toHit;					
						}
				}
				else if (targetIs == Orientation.HORIZONTAL){
					for (int i = 0; i < target.length; i++)
						if ((target[i].onRight().equals(toHit) || target[i].onLeft().equals(toHit)) && !opponent.isHit(toHit)){
							inChase = true;
							return toHit;					
						}
				}
				else if (targetIs == null){
					for (int i = 0; i < target.length; i++)
						if ((target[i].onRight().equals(toHit) || target[i].onLeft().equals(toHit) || target[i].above().equals(toHit) || target[i].below().equals(toHit)) && !opponent.isHit(toHit)){
							inChase = true;
							return toHit;
						}
				}
			}while(!inChase);
			targetIs = null;
			return null;
	}

}


