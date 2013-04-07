import javax.swing.*; // all the UI elements
import javax.swing.event.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.applet.*;
import java.net.*;
import javax.swing.ImageIcon;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PlayingField extends JPanel
{
	boolean isTurn;
				
	boolean hidden = false;
	
	private Icon iconB;
	private Icon iconR;
	private Icon iconY;

	Player parent;
	
	int shipPlaced = 0;
	Ship shipList[] = new Ship[]{Ship.AIRCRAFT_CARRIER, Ship.BATTLESHIP, Ship.SUBMARINE, Ship.DESTROYER, Ship.PATROL_BOAT};

	JButton[] buttons;
	
	NoLayoutListener NLL;

	PlayingField(boolean player, NoLayoutListener inNLL, URL url)
	{
		int scale = 30;
		setLayout(null);
		
		try{
		BufferedImage image = ImageIO.read(this.getClass().getResource("assets/PS_B.png"));
		iconB = new ImageIcon(image);
		image = ImageIO.read(this.getClass().getResource("assets/PS_R.png"));
		iconR = new ImageIcon(image);
		image = ImageIO.read(this.getClass().getResource("assets/PS_Y.png"));
		iconY = new ImageIcon(image);
		} catch (Exception e)
		{System.out.println("Error!");}
		
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
				
				buttons[bNum].addActionListener(handler);
				//buttons[bNum].setEnabled(player);
				add(buttons[bNum]);
			}
			
		NLL = inNLL;
	}
	
	private class BHandle implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			Object bObj = event.getSource();
			JButton clickedButton = (JButton) bObj;
			
			if(shipPlaced >= 5) // no ships placed?
			{
				clickedButton.setEnabled(false);
				System.out.println("(" + (clickedButton.getBounds().x / 30) + "," + (clickedButton.getBounds().y / 30) + ")");
				parent.enemyFire();
			}
			else // place ship
			{
				System.out.println(shipPlaced);
				GameBoard board = parent.getGameBoard();
				Coord click = new Coord(clickedButton.getBounds().x/30, clickedButton.getBounds().y/30);
				//setColor(click, Color.RED);\
				if(shipPlaced > 5) parent.say("Place your " + ShipInfo.getShipName(shipList[shipPlaced + 1]));
				if(!board.placeShip(shipList[shipPlaced], click, Orientation.EAST))
				{
					parent.say("You can't go there, silly!");
					shipPlaced--;
				}
				redrawBoard();				
				shipPlaced++;
				if(shipPlaced == 5)
				{
					parent.swapBoards();
				}
			}
		}
	}
	
	public boolean isTurn()
	{
		return NLL.isTurn(this);
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
		GameBoard board = parent.getGameBoard();
		for(int i = 0; i < 100; i++)
		{
			setColor(i, Color.BLUE);
			CellState st = board.getCellState(i);
			if(st == CellState.SHIP)
			{
				if(!hidden)
					setColor(i, Color.RED);
				setShipDisabled(i);
			}
			if(st == CellState.EMPTY)
			{
				setEmptyDisabled(i);
			}
		}
	}
	
	public void hideShips()
	{
		hidden = true;
	}
	
	public void setParent(Player in)
	{
		parent = in;
	}
	
	public void setDisabled()
	{
		for(int i = 0; i < 100; i ++)
			buttons[i].setEnabled(false);
	}
}
