/*
	File: Human.java
	Author: David Egolf
	Description: This defines the human class, basically an extension of Player with a name.
	Date: 2/27/13
*/

public class Human extends Player
{
	String name = new String(""); // need a name for humans
	
	// default constructor doesn't need to do anything
	Human()
	{
		board = new GameBoard();
	}
	
	// mutator for the name property
	public void setName(String toName)
	{
		name = toName;
	}// end setName()
	
	// accessor for the name property
	public String getName()
	{
		return name;
	} // end getName()
	
	// for you, ALWAYS show ships
	public void printBoard()
	{
		playingField.print(true);
	} // end printBoard
	
	// this is a passthrough function so you can place ships from the CLIGame.java
	// and find out the result of that
	public boolean placeShip(Ship inShip, Coord inCo, Orientation direction)
	{
		return playingField.placeShip(inShip, inCo, direction);
	} // end placeShip(Ship, Coord, Orientation)
} // end public class human
