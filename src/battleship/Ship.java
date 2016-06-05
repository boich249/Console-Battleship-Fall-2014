package battleship;
/**
 * Represents a battle ship in a specific location.
 * @author Asher
 *
 */
public class Ship {
	/*-------------------------------------
	 				ENUMS
	-------------------------------------*/
	
	// Enum for ship types name and length
	public enum ShipType {
		DESTROYER 	(2, "Destroyer"),
		CRUISER 	(3, "Cruiser"),
		SUBMARINE	(3, "Submarine"),
		BATTLESHIP 	(4, "Battleship"),
		AIRCRAFT 	(5, "Aircraft Carrier");
		
		final int LENGTH;
		final String NAME;
		
		
		ShipType(int length, String name){
			this.LENGTH = length;
			this.NAME = name;
		}
		
		public String toString(){
			return NAME + " (" + LENGTH + ")";
		}
		
		// returns a ShipType based on the value
		/*public static ShipType byValue(int value){
			
		}*/
		
	}
	
	// Used to represent ships orientation
	public enum Orientation {HORIZONTAL, VERTICAL}
	
	
	/*-------------------------------------
				INSTANCE VARIABLES
	-------------------------------------*/
	
	private ShipType type;
	
	// Represents ships direction
	private Orientation direction;
	
	// Stores ship's coordinates
	/*private*/ Coordinate[] location;
	
	// Represents ship's sunk status
	boolean sunk = false;
	
	/*----------------------------------------------------
	 				CONSTRUCTORS
	----------------------------------------------------*/
	
	// Default constructor
	public Ship(){
	}
	
	// Constructor sets ship's type
	public Ship(ShipType type){
	
		this.type = type;
		location = new Coordinate[type.LENGTH];
	}

	// Constructor sets ships length and sets first coordinate
	public Ship(ShipType type, Coordinate firstC){
		
		this(type);
		location[0] = new Coordinate (firstC);
	}
	
	// Constructor sets length, first coordinate, and direction
	public Ship(ShipType type, Coordinate firstC, Orientation direction){
		
		this(type, firstC);
		this.direction = direction;
	}
	
	// Copy constructor
	public Ship(Ship copy){
		this.location = new Coordinate[copy.location.length];
		for (int i = 0; i < location.length; i++)
			this.location[i] = new Coordinate(copy.location[i]);
		this.type = copy.type;
		this.direction = copy.direction;
		this.sunk = copy.sunk;
	}
	
	
	/*---------------------------------------------
	 			GETTERS AND SETTERS
	---------------------------------------------*/
	
	public ShipType getType() {
		return type;
	}

	public void setType(ShipType type) {
		this.type = type;
	}

	public Coordinate[] getLocation() {
		Coordinate[] location = new Coordinate[this.location.length];
		for (int i = 0; i < location.length; i++)
			location[i] = new Coordinate (this.location[i]);
		return location;
	}

	/*public void setLocation(Coordinate[] location) {
		this.location = location;
	}*/

	public Orientation getDirection() {
		return direction;
	}

	public void setDirection(Orientation direction) {
		this.direction = direction;
	}

	public boolean isSunk() {
		if (sunk)
			
			return true;
		
		else
			
			for (int i = 0; i < location.length; i++)
				
				if (!(location[i].isHit()))
					
					return false;
		
		return (sunk = true);
	}

	public void setSunk(boolean sunk) {
		this.sunk = sunk;
	}

	
	
	/*---------------------------------------------
	 				OTHER METHODS
	---------------------------------------------*/
	
	
	// Places ship on board, based on parameters first coordinate and direction
	public boolean place(Coordinate firstC, Orientation direction){
		location[0] = new Coordinate (firstC);
		this.direction = direction;
		
		return place();
	}
	
	// Places ship on board when required info is already there
	public boolean place(){
		if (direction == Orientation.HORIZONTAL)
			for (int i = 1; i < location.length; i++)
				location[i] = location[(i - 1)].onRight();
		else if (direction == Orientation.VERTICAL)
			for (int i = 1; i < location.length; i++)
				location[i] = location[(i - 1)].below();
		
		// Makes sure the coordinates exist
		for (int i = 0; i < location.length; i++){
			//System.out.println(location[i].exists());
			if (!(location[i].exists()))
				return false;
		}
		return true;
	}
	
	public void hit(Coordinate co){
		for(int i = 0; i < location.length; i++)
			if (co.equals(location[i]))
				location[i].hit();

	}
	
	public boolean equals(Ship other){
		return this.type == other.type;
	}
	
	

}
