import javax.swing.*; // all the UI elements
import javax.swing.event.*;
import java.awt.geom.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Portrait extends JPanel
{	
	String fileName = new String();
	Orientation whichWay;
	JLabel speech = new JLabel();
	
	int portX, portY;
	
	Portrait(int x, int y, int w, int h, String fName, Orientation dir)
	{
		this.setLayout(null);
		this.setBounds(x, y, w, h);
		fileName = fName;
		whichWay = dir;
		calcXY();
		openImage();
		speech.setVerticalAlignment(JLabel.TOP);
		speech.setVerticalTextPosition(JLabel.TOP);
		say("");
		this.add(speech);
	}
	
	private void calcXY()
	{
		switch(whichWay)
		{
			case WEST:
				portX = (int)getBounds().getWidth() - 90;
				portY = 0;
				break;
			case EAST:
				portX = 0;
				portY = 0;
				break;
		}
	}
	
	private void drawSpeechBubble(Graphics g)
	{
        Graphics2D g2 = (Graphics2D) g;
		
		int inFrom = 0, scalar = 1;
		
		int outter = (int)getBounds().getWidth();
		
		if(whichWay == Orientation.WEST)
		{
			inFrom = outter;
			scalar *= -1;
		}
		
		int [] x = new int[] {100, 125, outter - 25, outter - 25, 125, 100};
		int [] y = new int[] {45, 20, 20, 70, 70, 45};
		
		speech.setBounds(80 + (scalar * 50), 21, outter - 165, 50);
		
		for(int i = 0, j = 1; i < 5; i++, j++)
		{
			int x1 = inFrom + scalar * x[i];
			int y1 = y[i];
			int x2 = inFrom + scalar * x[j];
			int y2 = y[j];
			
			Line2D lin = new Line2D.Float(x1, y1, x2, y2);
			g2.draw(lin);
		}
	}
	
	Portrait()
	{
	}
	
	private void openImage()
	{
       try 
       {                
          image = ImageIO.read(this.getClass().getResource(fileName));
       } 
       catch (IOException ex)
       {
            System.out.println("Error with loading image! : " + ex);
       }
	}
	
	private BufferedImage image;
	
	public void paint(Graphics g)  
	{  
		super.paint(g); 
		drawSpeechBubble(g); 
		g.drawImage(image, portX,portY, null); //draw circle outline  
		g.drawOval(portX,portY,89,89);  
		
	}  
	
	public void say(String said)
	{
		speech.setText("<html>" + said + "</html>");
	}
}