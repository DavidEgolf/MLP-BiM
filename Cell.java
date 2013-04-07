/*
	Author: David Egolf
	File: Cell.java
	Description: This is the class that contains the individual cells.
			This keeps track of what ship is on the cell, and what BoardSpaceState the 
			cell is currently in.
	Date: 2/27/13
*/

public class Cell
{
	CellState state; // need to track the state
	Ship shipSpace; // track the Ship that is on the state
	
	// default constructor, cell should start out empty
	Cell()
	{
		state = CellState.EMPTY;
	} // end default constructor
	
	/*
		public void setCellState(CellState)
			Description: This method changes the state of the cell.
			Variables:
				Output:
					None
				Input:
					The state that the cell will be changing TO
				Returns:
					Nothing, void function.
	*/
	public void setCellState(CellState inState)
	{
		state = inState;
	} // end public void setCellState(CellState)
		
	/*
		public CellState getCellState()
			Description: This method is an accessor for the state of the cell
			Variables:
				Output:
					None
				Input:
					None
				Returns:
					The state of the cell, as enumerated type: CellState
	*/
	public CellState getCellState()
	{
		return state;
	} // end public CellState getCellState()
		
	/*
		public void setShipSpace(Ship)
			Description: This method changes the ship over the cell
			Variables:
				Output:
					None
				Input:
					The ship that the cell will be changing TO
				Returns:
					Nothing, void function.
	*/
	public void setShipSpace(Ship inShip)
	{
		shipSpace = inShip;
	} // end public void setShipSpace(Ship)
			
	/*
		public Ship getShipSpace()
			Description: This method is an accessor for the ship over the cell
			Variables:
				Output:
					None
				Input:
					None
				Returns:
					The ship of the cell, as enumerated type: Ship
	*/
	public Ship getShipSpace()
	{
		return shipSpace;
	} // end public Ship getShipSpace()
	
	/*
		public boolean isHit()
			Description: This method will determine whether the cell was hit, 
				and change status accordingly.
			Variables:
				Output:
					None
				Input:
					None
				Returns:
					True if the cell was hit with that attack
					False if the cell was not hit
	*/
	public boolean isHit()
	{
		if(state == CellState.SHIP)
		{
			state = CellState.HIT;
			return true;
		} // end if (state == SHIP)
		
		state = CellState.MISS;
		return false;
	}// end public boolean isHit()
	
}//end class
