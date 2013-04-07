/*
	File: Player.java
	Author: David Egolf
	Description: This class is the superclass to CPUAI and Human. A few handled here,
				some handled there.
	Date: 2/27/13
*/

import java.awt.Color;
import java.util.Random;

public class Player
{	
	boolean cheat = false; // default to off
	
	Portrait charPort;
	PlayingField field;
	public GameBoard board = new GameBoard();
	private ShipList list;
	NoLayoutListener parent;
	
	// default constructor has little use
	Player(NoLayoutListener in)
	{
		parent = in;
	}
	
	public void enemyFire()
	{
		parent.enemyFire();
	}
	
	public void setGameBoard(GameBoard a)
	{
		board = a;
	}
	
	public void setShipList(ShipList a)
	{
		list = a;
	}
	
	public ShipList getShipList()
	{
		return list;
	}
	
	public void setField(PlayingField a)
	{
		field = a;
		a.setParent(this);
	}
	
	public PlayingField getField()
	{
		return field;
	}
	
	public void setPortrait(Portrait a)
	{
		charPort = a;
	}
	
	public Portrait getPortrait()
	{
		return charPort;
	}
	
	public GameBoard getGameBoard()
	{
		return board;
	}
	
	// toggleCheat does exactly like it sounds. toggles cheating mode on/off
	public void toggleCheat()
	{
		cheat = !cheat; // toggle it
	} // end toggleCheat()
	
	// printBoard prints the board using the cheat boolean to determine
	// whether to show enemy ships
	public void printBoard()
	{		
		board.print(true);
	} // end printBoard()
	
	// attack() performs an attack on a player and checks to see what happens during
	// (what kind of outcome was the attack)
	public AttackOutcome attack(Coord attackCoords)
	{				
		//return field.fire(attackCoords); 
		return AttackOutcome.HIT;
	} // end Attackoutcome attack(Coord attackCoords)
	
	// public boolean scanForWin() returns true if the player has won
	public boolean scanForWinner()
	{
		//return field.didWin();
		return false;
	} // end scanForWin()
	
	public void generate()
	{
		BoardGenerator.generate(board);
	}
	
	public void showShips()
	{
		for(int i = 0; i < 100; i++)
		{
			switch(board.getCellState(i))
			{
				case MISS:
					field.setColor(i, Color.YELLOW);
				case EMPTY:
					field.setColor(i, Color.BLUE);
					break;
				case HIT:
				case SHIP:
					field.setColor(i, Color.RED);
			}
		}
	}
	
	public void setDisabledShips()
	{
		
		for(int i = 0; i < 100; i++)
		{
			switch(board.getCellState(i))
			{
				case SHIP:
					field.setShipDisabled(i);
				default:
			}
		}
	}
	
	public void say(String a)
	{
		charPort.say(a);
	}
	
	public void swapBoards()
	{
		parent.swapBoards();
		field.hideShips();
		field.redrawBoard();
	}
	
	public void fire()
	{
		Coord attackCo = new Coord();
		Random generator = new Random(); // need new Random object

		attackCo.setX(generator.nextInt(10));
		attackCo.setY(generator.nextInt(10));
		
	}
} // end public class Player
