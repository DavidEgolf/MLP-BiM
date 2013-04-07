import java.awt.event.ActionListener; // action listener
import java.awt.event.ActionEvent; // action event
import java.awt.event.ItemListener; // item listener
import java.awt.event.ItemEvent; // item event
import javax.swing.*; // all the UI elements
import javax.swing.event.*;
import java.awt.geom.*;
import java.awt.*;
import java.net.URL;

public class NoLayoutListener extends JPanel // this class draws up the ui
{
	
	PlayingField playingFieldLeft, playingFieldRight;
	
	Portrait portLeft, portRight;
	
	ShipList sLTop, sLBot;
	
	Player you, enemy;
	
	boolean tickTock = false;
	
	NoLayoutListener(URL url)
	{		
			setLayout(null);
			
			playingFieldLeft = new PlayingField(true, this, url);
			playingFieldLeft.setBounds(30,30,300,300);
			
			playingFieldRight = new PlayingField(false, this, url);
			playingFieldRight.setBounds(470,150,300,300);
			
			portLeft = new Portrait(30, 360, 420, 90, "assets/PPie.png", Orientation.EAST);
			portRight = new Portrait(350, 30, 420, 90, "assets/Discord.png", Orientation.WEST);
			
			sLTop = new ShipList(355, 160);
			sLBot = new ShipList(355, 250);
			
			you = new Player(this);
			enemy = new Player(this);
			
			enemy.generate();
			
			you.setPortrait(portLeft);
			enemy.setPortrait(portRight);
			you.setField(playingFieldLeft);
			enemy.setField(playingFieldRight);
			you.setShipList(sLBot);
			enemy.setShipList(sLTop);
			
			//enemy.showShips();
			enemy.setDisabledShips();
			
			this.add(you.getField());
			this.add(enemy.getField());
			this.add(you.getPortrait());
			this.add(enemy.getPortrait());
			this.add(you.getShipList());
			this.add(enemy.getShipList());
			
			you.getPortrait().say("You've got a real problem alright, and a banjo is the only answer!<br>Place your Swarm!");
			enemy.getPortrait().say("Time for some chaos...shall we start with parasprites?");
			
			enemy.printBoard();
	}
	
	public void paint(Graphics g) {
		paintDividers(g);
    }
    
    private void paintDividers(Graphics g)
    {
		super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Line2D lin = new Line2D.Float(330, 240, 469, 240);
        Line2D lin2 = new Line2D.Float(330, 239, 469, 239);
        Line2D lin3 = new Line2D.Float(330, 0, 330, 240);
        Line2D lin4 = new Line2D.Float(469, 240, 469, 480);
        g2.draw(lin);
        g2.draw(lin2);
        g2.draw(lin3);
        g2.draw(lin4);
    }
    
 	public boolean isTurn(PlayingField caller)
 	{
 		boolean temp = tickTock;
 		tickTock = !tickTock;
 		if(temp)
 		{
 			Coord a = new Coord(4, 5);
 			you.attack(a);
 		}
 		return temp;
 	}
 	
 	public void ready()
 	{
 		//swapBoards();
 	}
 	
 	public void swapBoards()
 	{
 		GameBoard temp = you.getGameBoard();
 		you.setGameBoard(enemy.getGameBoard());
 		enemy.setGameBoard(temp);
 		enemy.printBoard();
 	}
 	
 	public void enemyFire()
 	{
 		enemy.fire();
 	}
}
