/*
	Author: David Egolf
	File: CPUAI.java
	Description: This class represents an AI under control of the computer.
					It's an extension of player, and more to come later.
	Date: 2/27/13
*/

public class CPUAI extends Player
{
	// default constructor
	CPUAI()
	{
		BoardGenerator.generate(playingField); // generate the gameboard awhile
	}
	
	public void hide()
	{
		
	}
}
