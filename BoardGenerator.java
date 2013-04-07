/*
	Author: David Egolf
	File: BoardGenerator.java
	Description: This is the class that will generate a random board for the CPU.
	Date: 2/27/13
*/

import java.util.Random; // need to generate random numbers

class BoardGenerator
{
	/*
		public static void generate()
			Description: This will generate a random board, with all 5 ships, in random
						orientations.
						
			Variables:
				Output:
					theBoard - this is the board that will have the ships added to it
				Input:
					None
				Returns:
					Nothing, void function.
	*/
	public static void generate(GameBoard theBoard)
	{
		Random generator = new Random(); // need new Random object
		
		// for all 5 ships
		for (Ship outShip : Ship.values())
		{
			int orientation = generator.nextInt(4); // 4 different orientations
			int iterator = 0; // count the iterations
			
			// iterate through the orientations until it hits the one generated above
			for (Orientation or : Orientation.values())
			{
				if(iterator == orientation) // if the iterator is equal to the orient. generated
				{	
					Coord coordinates; // need a slightly higher scope
					do {
						int x = generator.nextInt(10); // generate an x coord
						int y = generator.nextInt(10); // generate a  y coord
						coordinates = new Coord(x, y);
					} // end do{}while(cantPlaceShip)
					while(!theBoard.placeShip(outShip, coordinates, or)); // place the ship, or
																// re-generate until it can
				} // end if(iterator == orientation)
				iterator++; // the iterator wasn't correct, try next one
			} // end (Orientation or : Orientation.values())
		} // end for (Ship outShip : Ship.values())
	} // end public static void generate()
} // end class boardGenerator
