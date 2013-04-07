/*
	Author: David Egolf
	File: CLI.java
	Description: This is the CLI client for battleship. Loads of print commands 
		and what-have-you. 
	Date: 2/27/13
*/

import java.util.Random; // need to generate random numbers
import java.util.Scanner; // scanner!
//import Input.InputReader; // InputReader class!

public class CLIGame
{
	public void run()
	{
		CPUAI playerCPU = new CPUAI(); // the comp-u-noid player
		Human playerOne = new Human(); // the humanoid player
		Scanner scan = new Scanner(System.in); // need (want) a scanner
		
		getHunanName(playerOne, scan, playerCPU); // get human name
		
		getHumanShips(playerOne, scan); // ask the human of ship names
		
		do
		{
			outputBoards(playerOne, playerCPU); // print boards
			launchAssault(playerOne, playerCPU, scan); // attack each other
		}
		while(scanForWinner(playerOne, playerCPU) == false); // until a winner
		
		if(playerOne.scanForWinner()) // show the winner! Kinda backwards...
			System.out.println("Computer won!");
		else
			System.out.println("Player one won!");
	}//end run method
	
	// scanForWinner just checks p1 and pCPU for a winner
	private boolean scanForWinner(Human p1, CPUAI pCPU)
	{
		return p1.scanForWinner() || pCPU.scanForWinner();
	} // end scanForWinner
	
	// function for getting the human's name
	private void getHunanName(Human inHumane, Scanner sc, CPUAI inAI)
	{
		System.out.print("Please enter your name : ");
		String username = sc.nextLine();
		inHumane.setName(username); // set name
		if(username.equals("cheat"))
			inAI.toggleCheat(); // toggle cheat
		if(username.equals("quit") || username.equals("exit"))
			System.exit(0); // kill game
	} // end getHumanName
	
	// method to output the human/cpu boards, nothing fancy
	private void outputBoards(Human inHuman, CPUAI inCPU)
	{
		System.out.println("Enemy board:");
		inCPU.printBoard();
		
		System.out.println();
		
		System.out.println(inHuman.getName() + "'s board:"); // 
		inHuman.printBoard();
	} // end outputBoards
	
	// launchAssault gets the human and cpu to attack each other in a gentlemanly way
	private void launchAssault(Human inHuman, CPUAI inCPU, Scanner sc)
	{
		boolean loopHuman = true; // loop the human until either a hit or miss
		while(loopHuman)
		{
			Coord attackCo = new Coord();
			generateHumanAssault(sc, attackCo);
			
			switch(inCPU.attack(attackCo))
			{
				case HIT:
					System.out.println("You got a hit on " + attackCo.print() + "!");
					loopHuman = false;
					break;
				case MISS:
					System.out.println("You missed on " + attackCo.print() + ".");
					loopHuman = false;
					break;
				case REGUESS:
					System.out.println("Already tried that space, try again.");
			} // end switch inCPU.attack
		}// end while(loopHuman)
		
		boolean loopAI = true; // loop AI until a hit or a miss
		while(loopAI)
		{
			Coord attackCo = new Coord();
			generateAIAssault(attackCo);
			
			switch(inHuman.attack(attackCo))
			{
				case HIT:
					System.out.println("AI got a hit on " + attackCo.print() + "!");
					loopAI = false;
					break;
				case MISS:
					System.out.println("AI missed on " + attackCo.print() + ".");
					loopAI = false;
					break;
			} // end switch inHuman.attack
		} // end while (loopAI)
	} // end launchAssault()
	
	// generateAIAssault just generates new coordinates until one that's not been tried yt
	private void generateAIAssault(Coord attackCo)
	{
		Random generator = new Random(); // need new Random object

		attackCo.setX(generator.nextInt(10));
		attackCo.setY(generator.nextInt(10));
	} // end generateAIAssault
	
	// getCoordsAsStr gets the coordinates as a String(Builder) Issues with string
	// and not passing properly led to the use of StringBuilder
	private void getCoordsAsStr(Scanner sc, StringBuilder inString, String query)
	{
		System.out.print(query);
		inString.append(sc.nextLine());
		doesQuit(inString);
	} // end getCoordsAsStr
	
	// loopFailCoords will loop forever, while the user fails at entering coords
	private void loopFailCoords(Scanner sc, StringBuilder inString, String query)
	{
		getCoordsAsStr(sc, inString, query);
		
		while(inString.toString().length() < 2 || inString.toString().length() > 3)
		{
			inString.setLength(0);
			getCoordsAsStr(sc, inString, "Error:Invalid | " + query);
		} // end while string.length() > 3 || string.len() < 2
	} // end loopFailCoords
	
	// generateHumanAssault will create an assault on behalf of a human and then
	// execute it until it works awesomely
	private void generateHumanAssault(Scanner sc, Coord attackCoord)
	{
		StringBuilder attackStr = new StringBuilder();
		
		loopFailCoords(sc, attackStr, "Enter your attack : ");
		Coord.generateCoordFromString(attackStr.toString(), attackCoord);
	} // end generateHumanAssault()
	
	// doesQuit is a simple function that quits when the user wants to
	private void doesQuit(StringBuilder aS)
	{
		if(aS.toString().equals("quit")) // did they type 'quit'?
			System.exit(0); // then quit
	} // end doesQuit(StringBuilder)
	
	// getHumanShips is kind of a backwards name. should be 'set' or 'place'
	private void getHumanShips(Human inHuman, Scanner sc)
	{	
		// need to do this for all 5 ship types
		for (Ship selectShip : Ship.values())
		{
			Coord insertCo = new Coord();
			boolean continueOn = false;
			while(continueOn == false)
			{
				int intOrient = -1;
				inHuman.printBoard();
			
				while(intOrient < 0 || intOrient > 3)
				{
					System.out.printf("Which orientation would you like your %s?\n", ShipInfo.getShipName(selectShip));
					printOrientationList();
				
					try {
						intOrient = InputReader.readInt();
					}catch(java.io.IOException e){
						System.out.println(e.getMessage());
					}
					StringBuilder sbToCoords = new StringBuilder();
					loopFailCoords(sc, sbToCoords, "Where would you like your ship (Eg:C4) : ");
					Coord.generateCoordFromString(sbToCoords.toString(), insertCo);
				
				} // end the checking 0 < input < 3 loop
				continueOn = inHuman.placeShip(selectShip, insertCo, intToOrient(intOrient));
			} // end the contination
		} // after all ships exhausted
	} // end void getHumanShips(Human, Scanner)
	
	// intToOrient just returns the Orientation based on the integer entered above
	private Orientation intToOrient(int inInt)
	{
		switch(inInt)
		{
			case 0: return Orientation.NORTH;
			case 1: return Orientation.EAST;
			case 2: return Orientation.SOUTH;
			case 3: return Orientation.WEST;
		}
		return Orientation.NORTH; // never hits, but compiler will complain without
	} // end private Orientation intToOrient(int)
	
	// printOrientationList just prints the available orientations
	private void printOrientationList()
	{
		int iterator = 0;
		for (Orientation or : Orientation.values())
			System.out.printf("%d: %s\n", iterator++, or);
		System.out.print("> ");
	} // end printOrientationList
}//end class
