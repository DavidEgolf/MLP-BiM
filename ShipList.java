import java.awt.geom.*;
import java.awt.*;
import javax.swing.*; // all the UI elements

public class ShipList extends JPanel
{
	JLabel [] shipText;
	boolean [] isSunk = new boolean[5];
	
	ShipList(int x, int y)
	{
		this.setLayout(null);
		setXY(x, y);
		spawnText();
	}
	
	private void setXY(int x, int y)
	{
		setBounds(x, y, 100, 200);
	}
	
	
	private void spawnText()
	{
		shipText = new JLabel[5]; // 5 ships, 5 labels
		int iterator = 0; // iterator
		// for all 5 ships
		for (Ship outShip : Ship.values())
		{ // increase iterator while spanwing new label with the "ship" name
			String text = new String(" - (" + ShipInfo.getShipLength(outShip) + ")");
			shipText[iterator] = new JLabel(ShipInfo.getShipName(outShip) + text);
			isSunk[iterator++] = false; // abuse this loop to initialize the boolean array
		} // end for (Ship outShip : Ship.values())
		
		// add the buttons to the frame
		for(int i = 0; i < 5; i++)
		{
			shipText[i].setBounds(0, 14*i, 120, 14);
			this.add(shipText[i]);
		}
	} // end spawnText()
	
	public void sink(int i)
	{
		isSunk[i] = true;
        this.repaint();
	}
		
	public void paint(Graphics g) {
		paintSunk(g);
    }
    
    private void paintSunk(Graphics g)
    {
		super.paint(g);  // fixes the immediate problem.
        Graphics2D g2 = (Graphics2D) g;
        for(int i = 0; i < 5; i++)
        {
			int y = i * 14 + 7;
			Line2D lin = new Line2D.Float(0, y, 75, y);
			if(isSunk[i])
				g2.draw(lin);
		}
    }
}