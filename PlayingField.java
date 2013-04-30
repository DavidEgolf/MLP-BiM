import javax.swing.*; // all the UI elements
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class PlayingField extends JPanel
{
	//private boolean isTurn; // may be used for networked client
	private boolean hidden = false;
	private int shipPlaced = 0;
	
	private Icon iconB;
	private Icon iconR;
	private Icon iconY;
	
	private GameBoard board; // this is the players game board
	private GameBoard enemyBoard; // this is the players enemy board (allows for switching)
	private ShipList list; // this player's ship list

	private Ship shipList[] = new Ship[]{Ship.AIRCRAFT_CARRIER, Ship.BATTLESHIP, Ship.SUBMARINE, Ship.DESTROYER, Ship.PATROL_BOAT};
	private JButton[] buttons;
	
	PlayingField(boolean player, URL url)
	{
		int scale = 30;
		setLayout(null);
		
		try
		{
			BufferedImage image = ImageIO.read(this.getClass().getResource("assets/PS_B.png"));
			iconB = new ImageIcon(image);
			image = ImageIO.read(this.getClass().getResource("assets/PS_R.png"));
			iconR = new ImageIcon(image);
			image = ImageIO.read(this.getClass().getResource("assets/PS_Y.png"));
			iconY = new ImageIcon(image);
		} 
		catch (Exception e)
		{
			System.out.println("Error importing parasprites!");
		}
		
		buttons = new JButton[100];
		
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++)
			{
				int bNum = i*10+j;
				
				buttons[bNum] = new JButton(iconB);
				buttons[bNum].setDisabledIcon(iconY);
				
				buttons[bNum].setOpaque(true);
				buttons[bNum].setBounds((j * scale), (i * scale), scale, scale);
				
				BHandle handler = new BHandle();
				
				buttons[bNum].addMouseListener(handler);
				//buttons[bNum].setEnabled(player);
				add(buttons[bNum]);
			}
			
		//NLL = inNLL;
	}
	
	/*
	private class BHandle implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Object bObj = event.getSource();
			JButton clickedButton = (JButton) bObj;
			
			System.out.println(event.getButton());
			
			if(shipPlaced >= 5) // if ships have been placed
			{
				clickedButton.setEnabled(false);
				System.out.println("(" + (clickedButton.getBounds().x / 30) + "," + (clickedButton.getBounds().y / 30) + ")");
				//parent.enemyFire();
			}
			else // ships not all placed, place ship
			{
				/*
				System.out.println(shipPlaced);
				GameBoard board = parent.getGameBoard();
				Coord click = new Coord(clickedButton.getBounds().x/30, clickedButton.getBounds().y/30);
				//setColor(click, Color.RED);\
				if(shipPlaced > 5) 
				{
					//parent.say("Place your " + ShipInfo.getShipName(shipList[shipPlaced + 1]));
				}
				if(!board.placeShip(shipList[shipPlaced], click, Orientation.EAST))
				{
					//parent.say("You can't go there, silly!");
					shipPlaced--;
				}
				redrawBoard();				
				shipPlaced++;
				if(shipPlaced == 5)
				{
					//parent.swapBoards();
				}
				// should have * /
			}
		}
	}
	*/
	
	private class BHandle implements MouseListener
	{
		//public void actionPerformed(ActionEvent event)
		{
			//Object bObj = event.getSource();
			//JButton clickedButton = (JButton) bObj;
			
			/*if(shipPlaced >= 5) // if ships have been placed
			{
				clickedButton.setEnabled(false);
				System.out.println("(" + (clickedButton.getBounds().x / 30) + "," + (clickedButton.getBounds().y / 30) + ")");
				//parent.enemyFire();
			}
			else // ships not all placed, place ship*/
			{
				/*
				System.out.println(shipPlaced);
				GameBoard board = parent.getGameBoard();
				Coord click = new Coord(clickedButton.getBounds().x/30, clickedButton.getBounds().y/30);
				//setColor(click, Color.RED);\
				if(shipPlaced > 5) 
				{
					//parent.say("Place your " + ShipInfo.getShipName(shipList[shipPlaced + 1]));
				}
				if(!board.placeShip(shipList[shipPlaced], click, Orientation.EAST))
				{
					//parent.say("You can't go there, silly!");
					shipPlaced--;
				}
				redrawBoard();				
				shipPlaced++;
				if(shipPlaced == 5)
				{
					//parent.swapBoards();
				}
				*/
			}
		}
		
		/* need to override these mouse events */
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		/* finished overriding the methods */
		
		/**
		 * problem with using a mouse listener:
		 * it'll respond whether the button is enabled or disabled
		 * need to check that when "clicking" the enemy's board!
		 */
		public void mouseClicked(MouseEvent event) 
		{
			Object bObj = event.getSource();
			JButton clickedButton;
			clickedButton = (JButton) bObj;
			MouseButton mButton = MouseButton.LEFT;
			
			if(event.getButton() == 3)
				mButton = MouseButton.RIGHT;
			/*
			 * shipPlaced == 6 must be checked first, otherwise it'll cascade from 4 -> 5 -> 6
			 */
			if(shipPlaced == 6)
			{
				attackSpace(clickedButton);
				//enemyAttack()
			}
			
			// if not all ships placed, try to set them, redraw board, show the ship is down, then increment tracking var
			if(shipPlaced < 5)
			{
				if(setShip(clickedButton, mButton))
				{
					redrawBoard();
					list.sink(shipPlaced);
					shipPlaced++;
				}
			}
			
			// after the ships are placed, it should swap the boards, and empty the tracking list
			// here we do need it to cascade from 4 -> 5, though
			if(shipPlaced == 5)
			{
				swapBoards();
			}
		}
	}
	
	// TODO: implement attackSpace(JButton)
	private void attackSpace(JButton inButton)
	{
		
	}
	
	private boolean setShip(JButton bClicked, MouseButton mClicked)
	{
			Orientation whichWay;
			
			switch(mClicked)
			{
			case RIGHT:
				whichWay = Orientation.SOUTH;
				break;
			default:
				whichWay = Orientation.EAST;
			}
			
			int x = bClicked.getBounds().x/30;
			int y = bClicked.getBounds().y/30;
			Coord click = new Coord(x, y);
			
			boolean isSet = board.placeShip(shipList[shipPlaced], click, whichWay);
			
			return isSet;
	}
	
	/**
	 * swaps the players and the enemy's boards, so that you only ever have to worry
	 * about clicking on the right board. seemed to make sense previously, but now
	 * I question my sanity.
	 */
	private void swapBoards()
	{
		// 3 variable swap of boards here:
		GameBoard tempBoard = enemyBoard;
		enemyBoard = board;
		board = tempBoard;

		hidden = true; // don't want to see enemy ships
		redrawBoard(); // redraw the board
		list.clearSunk(); // clear the "sunk" list
		shipPlaced++; // now to put it in "attacking" mode
	}
	
	public void setColor(Coord cell, Color color)
	{
		setColor(cell.toLinear(), color);
	}
	
	public void setColor(int cell, Color color)
	{
		if(color == Color.RED)
			buttons[cell].setIcon(iconR);
		
		else if(color == Color.BLUE)
			buttons[cell].setIcon(iconB);
		
		else
			buttons[cell].setIcon(iconY);
	}
	
	public void setShipDisabled(int cell)
	{
		buttons[cell].setDisabledIcon(iconR);
	}
	
	public void setEmptyDisabled(int cell)
	{
		buttons[cell].setDisabledIcon(iconY);
	}
	
	public void redrawBoard()
	{
		for(int i = 0; i < 100; i++)
		{
			CellState st = board.getCellState(i);
			
			// default color = blue (ship and empty)
			setColor(i, Color.BLUE);
			
			// if there's a ship, and ships aren't hidden, paint it red
			if(st == CellState.SHIP)
			{
				if(!hidden)
					setColor(i, Color.RED);
				//setShipDisabled(i);
			}
			
			/*if(st == CellState.EMPTY)
			{
				setEmptyDisabled(i);
			}*/
			
			
		}
	}
	
	public void hideShips()
	{
		hidden = true;
	}
	
	public void setDisabled()
	{
		
		for(int i = 0; i < 100; i ++)
			buttons[i].setEnabled(false);
	}
	
	public void setShipList(ShipList in)
	{
		list = in;
	}
	
	public void setGameBoard(GameBoard in)
	{
		board = in;
	}

	public void setEnemyGameboard(GameBoard in) 
	{
		enemyBoard = in;
	}
}
