/*
	File: GameBoard.java
	Author: David Egolf
	Description: This is the class that handles keeping all the cells together.
		Most methods take singular/multiple of (Ship|Orientation|Coord).
	Date: 2/27/13
	
	// this document is largely undocumented. It's now 8:30AM and I've been up since 6 AM
	// yesterday (where I put in 8 hours of work) and I can barely type much less think
*/

public class GameBoard
{
	private Cell cell[]; // array of cells
	
	private boolean debugVar = false;
	
	private int width = 10; // thought experiment of resizable battleship
	private int height = 10; // might've worked too, but '10' hard coded too many places
	
	private int totalCells = width * height; // again, resizable thing
	
	GameBoard()
	{
		cell = new Cell[totalCells]; // even dynamic as to how many cells it creates!
		for(int i = 0; i < totalCells; i++) // for all the cells,
			cell[i] = new Cell();	// instantiate them
	} // end GameBoard()
	
	public void print()
	{
		print(false);
	}
	
	public void print(boolean showShips)
	{
		System.out.print("  "); // two spaces for the numbering scheme
		
		for(int i = 1; i <= width; i++) // print out the column numbers
			System.out.printf("%d ", i);
			
		System.out.println(); // blank line to start printing the board
		
		for(int i = 0; i < totalCells; i++) // for every cell in the board
		{
			if((i+1) % width == 1) // first column of the row?
				System.out.print(getLetter(i/height) + " "); // print the row 'letter'
			
			System.out.printf("%c", getStateChar(i, showShips)); // get the state of the space, print
			
			if((i+1) % width > 0) // if not end of line
				System.out.print(" "); // print pipe
			else // end of line
				System.out.println(); // newline
		}
	}
	
	public AttackOutcome fire(Coord inCo)
	{
		if(!isValidXY(inCo)) return AttackOutcome.ERROR;
		
		int coOrd = inCo.toLinear();
		//System.out.printf("(%d, %d)\n", inCo.getX(), inCo.getY());
		switch(cell[coOrd].getCellState())
		{
			case SHIP:
				cell[coOrd].setCellState(CellState.HIT);
				return AttackOutcome.HIT;
			case EMPTY:
				cell[coOrd].setCellState(CellState.MISS);
				return AttackOutcome.MISS;
			default:
				break;
		}
		
		return AttackOutcome.REGUESS;
	}
	
	public boolean placeShip(Ship inShip, Coord inCo, Orientation direction)
	{
		//check bounds
		if(!checkShipPlacement(inShip, inCo, direction))
			return false;
			
		// check for other ship collisions
		if(!checkShipCollisions(inShip, inCo, direction))
			return false;
		
		// we know it's safe at this point
		int coOrd = inCo.toLinear(); // get the coord from [x][y] -> [coord]
		int modifier = getModifier(direction); // this is the 
			
		// for the length of the ship
		for(int i = 0; i < ShipInfo.getShipLength(inShip); i++)
		{
			cell[coOrd].setCellState(CellState.SHIP); // tell the board there's a ship
			cell[coOrd].setShipSpace(inShip); // tell the board which ship is there
			coOrd += modifier; // move on to next cell
		} // end ship placement loop
		
		return true; // the ship was placed successfully
	}
	
	private boolean checkShipPlacement(Ship inShip, Coord inCo, Orientation direction)
	{
		int y = inCo.getY();
		int x = inCo.getX();
		
		switch(direction)
		{
			case NORTH:
				if((y - ShipInfo.getShipLength(inShip)) < 0)
					return false;
				break;
			case EAST:
				if((x + ShipInfo.getShipLength(inShip)) > 10) // bug was here, > 9
					return false;
				break;
			case SOUTH:
				if((y + ShipInfo.getShipLength(inShip)) > 10) // bug was also here
					return false;
				break;
			case WEST:
				if((x - ShipInfo.getShipLength(inShip)) < 0)
					return false;
				break;
		}
		
		if(debugVar)
			System.out.println("(" + (x+1) + ":" + getLetter(y) + ")" + direction + ":" + inShip);
		return true;
	}
	
	private boolean checkShipCollisions(Ship inShip, Coord inCo, Orientation direction)
	{
		int coOrd = inCo.toLinear();
		int modifier = getModifier(direction);
	
		for(int i = 0; i < ShipInfo.getShipLength(inShip); i++)
		{
			if(cell[coOrd].getCellState() == CellState.SHIP)
				return false;
			coOrd += modifier;
		}
		
		return true;
	}
	
	private boolean isValidXY(Coord inCo)
	{
		int x = inCo.getX();
		int y = inCo.getY();
		return ((x >= 0) && (x <= 9) && (y >= 0) && (y <= 9));
	}
	
	/*
	private void setCell(Cell theCell, CellState theCellState)
	{
		theCell.setCellState(theCellState);
	}
	*/
	
	private char getStateChar(int cellNumber, boolean showShips)
	{
		if(
			(cell[cellNumber].getCellState() == CellState.EMPTY) ||
			((cell[cellNumber].getCellState() == CellState.SHIP) && !showShips)
		   )
			return '#';
		if(cell[cellNumber].getCellState() == CellState.HIT)
			return 'X';
		if(cell[cellNumber].getCellState() == CellState.MISS)
			return 'o';
		if(cell[cellNumber].getCellState() == CellState.SHIP)
			return '+';
		return '.';
	}
	
	private int getModifier(Orientation direction)
	{
		switch(direction)
		{
			case NORTH: return -10;
			case EAST: return 1;
			case SOUTH: return 10;
			case WEST: return -1;
		}
		return 0; // never hits, java compiler complains without
	}
	
	/*private int getCoord(int x, int y)
	{
		return (y * 10) + x;
	}*/
	
	public char getLetter(int rowNum)
	{
		char theLetter = 'A';
		theLetter += rowNum;
		return theLetter;
	}
	
	public boolean didWin()
	{
		for(int i = 0; i < 100; i++)
			if(cell[i].getCellState() == CellState.SHIP)
				return false;
		return true;
	}
	
	public CellState getCellState(int cellNum)
	{
		if(cellNum >= 0 && cellNum < 100)
		{
			return cell[cellNum].getCellState();
		}
		return CellState.MISS;
	}
}//end class
