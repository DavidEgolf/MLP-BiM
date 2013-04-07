/*
	Author: David Egolf
	File: BattleshipGame.java
	Description: This is the main file being loaded/run into the Java VM.
		It just loads up a CLIGame object and then runs it. This will make
		changing it to a GUI easier, hopefully.
	Date: 2/27/13
*/

import java.awt.Graphics;
import javax.swing.JApplet;

public class BattleshipGame extends JApplet
{
	NoLayoutListener testUI;

		// JApplet init() function
	public void init()
	{
	
		testUI = new NoLayoutListener(getCodeBase());
		testUI.setSize(800,480); // size it
		this.add(testUI);
		
	}//end main method
	
	public void paint(Graphics g)
	{
		super.paint( g );
	}
}//end class BattleshipGame
