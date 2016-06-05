package battleship;
/**
 * Represents a coordinate on a board, with an x an y value.
 * @author Asher
 *
 */
public class Coordinate {
	// x and y coordinate values
	public static final int MAX_X = 10;
	public static final int MIN_X = 1;
	public static final char MAX_Y = 'J';
	public static final char MIN_Y = 'A';
	
	private int x;
	private char y;
	
	// Was coordinate hit?
	boolean hit = false;
	
	////////////////////////////////////////////////
	boolean hitt = false;
	
	public void setHitt(){
		if (hit == true)
			hitt = true;
	}
	public boolean isHitt(){
		return hitt;
	}
	
	
	/////////////////////////////////////////////////
	
	// Default constructor
	public Coordinate(){}
	
	// Constructor sets x and y values. Takes an int for x, char for y
	public Coordinate(int x, char y){
		this.x = x;
		this.y = y;
	}
	
	// Constructor sets x and y values. Takes two int values, casts y to char
	public Coordinate(int x, int y){
		this.x = x;
		this.y = (char)y;
	}
	
	// Constructor takes a String representation of a coordinate
	public Coordinate(String fromString){
		x = Integer.parseInt(fromString.substring(1));
		y = (char)fromString.substring(0, 1).toUpperCase().charAt(0);
		//System.out.println(this);
	}
	
	// Copy constructor
	public Coordinate(Coordinate toCopy){
		this.x = toCopy.x;
		this.y = toCopy.y;
	}
	
	// Get and set x, y, and hit
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public char getY() {
		return y;
	}

	public void setY(char y) {
		this.y = y;
	}

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	// "Hits" the coordinate
	public void hit() {
		this.hit = true;
	}

	// Returns the coordinate to the immediate right of this
	public Coordinate onRight(){
		return new Coordinate((this.x + 1), this.y);
	}
	
	// Returns the coordinate to the immediate left of this
	public Coordinate onLeft(){
		return new Coordinate((this.x - 1), this.y);
	}
	
	// Returns the coordinate immediately above this
	public Coordinate above(){
		return new Coordinate(this.x, (this.y - 1));
	}
	
	// Returns the coordinate immediately below this
	public Coordinate below(){
		//System.out.println(new Coordinate(this.x, (this.y + 1)));
		return new Coordinate(this.x, (this.y + 1));
	}
	
	public boolean exists(){
		return (!(x < MIN_X) && !(x > MAX_X)) && (!(y < MIN_Y) && !(y > MAX_Y));
	}
	
	
	// Indicates if two coordinates are identical
	public boolean equals(Coordinate another){
		return ((this.x == another.x) && (this.y == another.y));
	}
	
	// Returns a String representation of the coordinate
	public String toString(){
		return "" + y + x;
	}
	
	// Indicates if the string is a toString representation of the coordinate
	public boolean equalsToString(String toString){
		return this.toString().equals(toString);
	}
}
