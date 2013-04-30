import javax.swing.*; // all the UI elements
import java.awt.geom.*;
import java.awt.*;
import java.net.URL;

public class NoLayoutListener extends JPanel // this class draws up the ui
{
	private static final long serialVersionUID = 1L; // YAY ECLIPSE STOP COMPLAIN

	PlayingField playingFieldLeft, playingFieldRight;
	
	Portrait portLeft, portRight;
	
	ShipList sLTop, sLBot;
	
	Player lPlayer, rPlayer; 
	
	boolean tickTock = false;
	
	NoLayoutListener(URL url)
	{		
			setLayout(null);
			
			playingFieldLeft = new PlayingField(true, url);
			playingFieldLeft.setBounds(30,30,300,300);
			
			playingFieldRight = new PlayingField(false, url);
			playingFieldRight.setBounds(470,150,300,300);
			
			String lImagePath = "assets/PPie.png";
			String rImagePath = "assets/Discord.png";
			
			if(true)
			{
				lImagePath = "assets/CE.png";
				rImagePath = "assets/CE.png";
			}
			
			System.out.println(lImagePath);
			
			portLeft = new Portrait(30, 360, 420, 90, lImagePath.toString(), Orientation.EAST);
			portRight = new Portrait(350, 30, 420, 90, rImagePath.toString(), Orientation.WEST);
			
			sLTop = new ShipList(355, 160);
			sLBot = new ShipList(355, 250);
			
			lPlayer = new Player();
			rPlayer = new Player();
			
			BoardGenerator.generate(rPlayer.getGameBoard());
			
			playingFieldLeft.setGameBoard(lPlayer.getGameBoard());
			playingFieldRight.setGameBoard(rPlayer.getGameBoard());
			
			lPlayer.setPortrait(portLeft);
			rPlayer.setPortrait(portRight);
			lPlayer.setPlayingField(playingFieldLeft);
			rPlayer.setPlayingField(playingFieldRight);
			lPlayer.setShipList(sLBot);
			rPlayer.setShipList(sLTop);
			
			lPlayer.setEnemy(rPlayer);
			rPlayer.setEnemy(lPlayer);
			
			//enemy.showShips();
			rPlayer.setDisabled();
			
			this.add(lPlayer.getPlayingField());
			this.add(rPlayer.getPlayingField());
			this.add(lPlayer.getPortrait());
			this.add(rPlayer.getPortrait());
			this.add(lPlayer.getShipList());
			this.add(rPlayer.getShipList());
			
			lPlayer.getPortrait().say("Place your swarm on the left side board, silly! Left click for across, right click for down!");
			rPlayer.getPortrait().say("");
			
			rPlayer.printGameBoard();
	}
	
	/**
	 * override the paint object so that it paints dividers between boards
	 * @param g 
	 */
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
}
