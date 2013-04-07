/*
	File: ShipInfo.java
	Author: David Egolf
	Description: This class of two static methods will just retrieve information on 
					the eunms of Ship because of the prohibitive nature of enums.
	Date: 2/27/13
*/
public class ShipInfo
{
	/*
		public static int getShipLength(Ship inShip)
			Description: Because I hate myself, I decided to name a function with the 
						word 'length' in it so that I could stumble over why the compiler
						now hates me too. In all seriousness, this just gets the number
						of squares a ship covers
			Variables:
				Output:
					n/a
				Input:
					Ship
				Returns:
					number of squares for given ship
	*/
	public static int getShipLength(Ship inShip)
	{
		// based on the type of ship, return the length
		switch(inShip)
		{
			case AIRCRAFT_CARRIER: return 5;
			case BATTLESHIP: return 4;
			case SUBMARINE: return 3;
			case DESTROYER: return 3;
			case PATROL_BOAT: return 2;
		}
		return 0; // javac bitches if there's no catch-all return
	} // end public static int getShipLength(Ship)
	
	/*
		public static int getShipName(Ship inShip)
			Description: this method returns the fancified name of the ship
			Variables:
				Output:
					n/a
				Input:
					Ship
				Returns:
					Name of ship
	*/
	public static String getShipName(Ship inShip)
	{
		// based on the type of ship, return the name of the ship
		switch(inShip)
		{
			case AIRCRAFT_CARRIER: return "Swarm";
			case BATTLESHIP: return "Eaters";
			case SUBMARINE: return "Trio";
			case DESTROYER: return "Spawn";
			case PATROL_BOAT: return "Pair";
		}
		return ""; // javac maons if there's no catch-all return
	} // end public static String getShipName(Ship)
} // end public class ShipInfo
