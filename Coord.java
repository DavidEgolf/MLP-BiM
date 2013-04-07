/*
	Author: David Egolf
	File: Coord.java
	Description: This class is responsible for maintaining coordinates for the
		battleship game board. There are multiple constructors, and multiple ways of
		getting various types of data into this class.
	Date: 2/27/13
*/

public class Coord
{
	private int x; // x coordinate
	private int y; // y coordinate
	
	// default constructor, assign them both to 0
	Coord()
	{
		x = 0;
		y = 0;
	}
	
	// constructor with two integer parameters, assign them to X and Y respectively
	Coord(int inX, int inY)
	{
		x = inX;
		y = inY;
		
		validate();//ensure there aren't any points existing outside of the 10x10 universe
	}
	
	/*
		public String print()
			Description: This generates a string for the coordinates in (YX) format where:
							Y is a letter (as on the game)
							X is one above its true value (IE: no B0)
						This function was mostly a testing/debugging function.
			Variables:
				Output:
					None
				Input:
					None
				Returns:
					String of text that represents the coord. 
	*/
	public String print()
	{
		return "(" + intToChar(y) + (1+x) + ")";
	} // end public String print()
		
	/*
		public char intToChar(int)
			Description: This method take an integer (row number) and converts it to its
						corresponding row letter.
			Variables:
				Output:
					None
				Input:
					rowNum - integer - The Y axis row number
				Returns:
					character - the letter representation of the rowNum
	*/
	public static char intToChar(int rowNum)
	{
		char theLetter = 'A'; // starting with 'A'
		theLetter += rowNum;  // increment by the row num
		return theLetter; // return the letter
	}
	
	/*
		Coord(char, char)
			Description: this constructor creates a coordinate point from two characters
			Variables:
				Output:
					none
				Input:
					inX - character - the horizontal coordinate, as a character representation
					inY - character - the vertical coordinate, as a character representation
				Returns:
					nothing
	*/
	Coord(char inX, char inY)
	{
		x = intFromChar(inX);
		y = intFromChar(inY);
		
		validate(); // "validate" input
	}

	/*
		public int toLinear()
			Description: This takes the coordinate from a two dimensional point and linearizes it to a single point on the large grid
			Variables:
				Output:
					none
				Input:
					none
				Returns:
					linear point; [0, 99]
	*/
	public int toLinear()
	{
		return (y * 10) + x; // self explanitory
	}
	
	/*
		public static int intFromChar
			Description: this method take a character and returns the integer value of it
						ie: a -> 0, A -> 0, '1' -> 0, b -> 1, B -> 1, '2' -> 1, etc
			Variables:
				Output:
					none
				Input:
					inC - char - the character to be converted to integer
				Returns:
					
	*/
	public static int intFromChar(char inC)
	{
		if (inC < 'A') // below the alphabet on ASCII? it's a number. remove 49
			return inC - 49; // 49 because that will give us '1' -> 0 because of
								// zero indexed arrays
	
		if (inC >= 'a') // it's lowercase?
			inC -=32; // convert to uppercase
			
		return inC - 65; // knock off the 65 that is the Cap A (which is what the 
						// alphabet should be, numbers already returned away from this)
	}
	
	/*
		private void validate()
			Description: this 'validates' the x and y coordinates
						translations: if values are out of [0, 9], they're placed within
							that range
			Variables:
				Output:
					none
				Input:
					none
				Returns:
					none
	*/
	private void validate()
	{
		if(x > 9) x = 9; // x greater then 9? nope
		if(x < 0) x = 0; // x smaller then 0? nope
		if(y > 9) y = 9; // y greater then 9? nope
		if(y < 0) y = 0; // y smaller then 0? nope
	}
	
	// standard accessor method for X
	public int getX()
	{
		return x;
	}
	
	// standard accessor method for Y
	public int getY()
	{
		return y;
	}
	
	// standard mutator method for X, with "validation"
	public void setX(int inX)
	{
		x = inX;
		validate();
	}
	
	// standard mutator method for Y, with "validataion"
	public void setY(int inY)
	{
		y = inY;
		validate();
	}
	
	/*
		public static void GenerateCoordFromString(String, inStrCo, Coord theCoord)
			Description: this takes input (A5, b7, even 14) as a string and makes it
			coord (A5 -> (0, 4); b7 -> (1, 6); 14 -> (0, 3))
			Variables:
				Output:
					theCoord - Coord - the coordinate that was pulled from inStrCo
				Input:
					inStrCo - String - the string that the coordinate gets pulled from
				Returns:
					nada
	*/
	public static void generateCoordFromString(String inStrCo, Coord theCoord)
	{
		char row = inStrCo.charAt(0);
		char col;
		if(inStrCo.length() == 3) // if the column is 10, there will be 3 characters
			col = ':'; // this is a hack, but it works. not my best work, I wish it didn't
		else
			col = inStrCo.charAt(1);
		
		theCoord.setX(intFromChar(col)); // set the x from the col
		theCoord.setY(intFromChar(row)); // set the y from the row
	}
	
	public static Coord generateCoordFrom1D(int x)
	{
		Coord y = new Coord();
		y.setX(x%10);
		y.setY(x/10);
		return y;
	}
}
