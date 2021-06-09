import java.awt.*;
import java.awt.event.*;							// NEW #1 !!!!!!!!!!
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/*********************************************************************************
 *
 * SpaceshipGame.java
 * 				
 ***********************************************************************************/
public class SpaceshipGame
extends JFrame 
implements ActionListener, KeyListener

{
	// DATA:
	private static Spaceship ship;
	private static Spaceship enemy;
	private static BufferedImage image;

	private static final int WINDOW_WIDTH = 800;		// Window size
	private static final int WINDOW_HEIGHT = 700;		// Window size
	private static final int TOP_OF_WINDOW = 22;	// Top of the visible window

	private static final int DELAY_IN_MILLISEC = 100;  // Time delay between ball updates

	// METHODS:

	/**
	 * main -- Start up the window.
	 * 
	 * @param	args
	 */
	public static void main(String args[])
	{
		// Create the window.
		
		SpaceshipGame game = new SpaceshipGame();
		game.addKeyListener(game);
	
		game.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		game.setVisible(true);
		
		// shuts down program window on closing
		
		Timer clock = new Timer(DELAY_IN_MILLISEC, game);			
		clock.start();

	}


	/**
	 * Constructor for SpaceshipGame class.  
	 * Creates one SpaceShipGame object, starts up the window and starts the timer.
	 */
	public SpaceshipGame()
	{
	
		super ("Spaceship Game"); // prints on top of JFrame
		
		try {
			image = ImageIO.read(new File("Space.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ship = new Spaceship (3 * WINDOW_WIDTH / 4 , WINDOW_HEIGHT / 2, 0, 0, Color.RED, Color.YELLOW);
		enemy = new Spaceship (WINDOW_WIDTH / 4, WINDOW_HEIGHT / 2, 0, 0, Color.GREEN, Color.WHITE);
		
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setVisible(true);
  
		Timer clock= new Timer(DELAY_IN_MILLISEC, this);	
		
		clock.start();	
	}

	/**
	 * actionPerformed() is called automatically by the timer every time the requested 
	 * delay has elapsed.  It will keep being called until the clock is stopped or the
	 * program ends.  All actions that we want to happen then should be performed here.
	 * Any class that implements ActionListener MUST have this method.
	 * 
	 * In this example it is called to move the ball every DELAY_IN_MILLISEC.
	 * 
	 * @param e		Contains info about the event that caused this method to be called
	 */
	public void actionPerformed(ActionEvent e)		// NEW #5 !!!!!!!!!!
	{
		ship.move();	
		enemy.move();
		ship.wrap(0, WINDOW_WIDTH, TOP_OF_WINDOW, WINDOW_HEIGHT);
		enemy.wrap (0, WINDOW_WIDTH, TOP_OF_WINDOW, WINDOW_HEIGHT);

		// Update the window.
		repaint();
	}

	/**
	 * paint 		draw the window
	 * 
	 * @param g		Graphics object to draw on
	 */
	public void paint(Graphics g)
	{
		// Clear the window.
		g.setColor(Color.black);
		g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
		g.drawImage (image, 0, TOP_OF_WINDOW, null);
		
		ship.draw(g);
		enemy.draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

		int keyCode = e.getKeyCode();

		if (keyCode == KeyEvent.VK_LEFT)
		{
			ship.turnLeft();
		}
		else if (keyCode == KeyEvent.VK_RIGHT)
		{
			ship.turnRight();
		}
		else if (keyCode == KeyEvent.VK_UP)
		{
			ship.thrust();
		}
		else if (keyCode == KeyEvent.VK_DOWN)
		{
			ship.reverseThrust();
		}
		else if (keyCode == KeyEvent.VK_P) // for "photon"?
		{
			ship.shoot();
		}
		
		if (keyCode == KeyEvent.VK_A)
		{
			enemy.turnLeft();
		}
		else if (keyCode == KeyEvent.VK_D)
		{
			enemy.turnRight();
		}
		else if (keyCode == KeyEvent.VK_W)
		{
			enemy.thrust();
		}
		else if (keyCode == KeyEvent.VK_X)
		{
			enemy.reverseThrust();
		}
		else if (keyCode == KeyEvent.VK_S)
		{
			enemy.shoot();
		}
		
		repaint();

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	
}



