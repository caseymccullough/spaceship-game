import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/*********************************************************************************
 * Spaceship Class
 * Represents a single spaceship, which can 
 * rotate right
 * rotate left
 * forward thrust
 * reverse thrust
 * shoot
 ***********************************************************************************/
public class Spaceship
{
	private static final double TURN_ANGLE = Math.PI / 8;
	private static final double INITIAL_ORIENTATION = Math.PI / 2; // upward facing
	private static final double ACCELERATION = 4;
	private static final int SHOOT_VELOCITY = 20;
	private static final int SIZE = 15; // represents radius of large circle
	
	// DATA:
	private int x, y;  // Center of the ball
	private int dx, dy;  // Velocity - how much to move the ball in one time unit
	private int radius;  // Radius of the ball
	private Color color; // Color of the ship
	private Color turretColor;
	private double orientation; // an angle measured in radians.
	private double myAcceleration;
	private int smallRadius;
	
	ArrayList <Bullet> myBullets;
	

	// METHODS:

	/**
	 * Spaceship constructor initializes the Spaceship object
	 * 
	 * @param xIn  x coordinate of center
	 * @param yIn  y coordinate of center
	 * @param dxIn  x velocity
	 * @param dyIn  y velocity
	 * @param radiusIn radius
	 * @param colorIn color
	 */
	public Spaceship (int xIn, int yIn, int dxIn, int dyIn, Color c, Color turret)
	{
		x = xIn;
		y = yIn;
		dx = dxIn;
		dy = dyIn;
		
		radius = SIZE;
		smallRadius = SIZE / 3;
		
		this.color = c;
		turretColor = turret;
		orientation = INITIAL_ORIENTATION; // in radians
		myAcceleration = ACCELERATION;
		
		myBullets = new ArrayList<Bullet> ();
	}
	public double getFrontX ()
	{
		return x + Math.cos(orientation) * radius;	
	}
	
	public double getFrontY ()
	{
		return  y - Math.sin(orientation) * radius;
	}
	
	
	/**
	 * Move the ship.  Add the velocity to its center.
	 */
	public void move()
	{
		x = x + dx;
		y = y + dy;
		
		for (int i = 0; i < myBullets.size(); i++)
		{
			myBullets.get(i).move();
		}
	}
	
	public void shoot()
	{
		 double bulletDx = dx + SHOOT_VELOCITY * Math.cos(orientation);
		 double bulletDy = dy - SHOOT_VELOCITY * Math.sin(orientation);

		Bullet b = new Bullet((int) getFrontX(), (int) getFrontY(), (int) bulletDx, (int) bulletDy);
		myBullets.add(b);
	}
	
	public void thrust() // ship accelerates in direction of orientation
	{
		double deltaDX = Math.cos(orientation) * myAcceleration;
		double deltaDY = Math.sin(orientation) * myAcceleration;
		
		dx += deltaDX;
		dy -= deltaDY; // positive in Cartesian coordinates means 
						// a decreasing y value on graphics screen
	}
	
	public void reverseThrust() // ship accelerates in direction of orientation
	{
		double deltaDX = Math.cos(orientation) * myAcceleration;
		double deltaDY = Math.sin(orientation) * myAcceleration;
		
		dx -= deltaDX;
		dy += deltaDY; 
	}

	/**
 if the ship moves off screen it emerges on the opposite side
	 */
	public void wrap (int xLow, int xHigh, int yLow, int yHigh)
	{
		// if we move off left edge of screen
		if (x - radius <= xLow && dx < 0)
		{
			x = xHigh; 
		}

		if (x + radius >= xHigh && dx > 0)
		{
			x = 0;
		}

		// check if ship goes off top of screen
		if (y - radius <= yLow && dy < 0) 
		{
			y = yHigh;
		}

		if (y + radius >= yHigh && dy > 0)
		{
			y = 0;
		}
	}
	
	public void turnRight()
	{
		orientation -= TURN_ANGLE;	
	}
	
	public void turnLeft()
	{
		orientation += TURN_ANGLE;
	}

	/**
	 * Draw the ship.
	 * 
	 * @param g   Graphics object in which to draw
	 */
	public void draw(Graphics g)
	{
		g.setColor(color);
		double gunLength = 15;
		
		//g.fillOval (200, 200, 25, 25);
		g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
		g.setColor(turretColor);
		
		g.fillOval((int) getFrontX() - smallRadius,(int) getFrontY() - smallRadius, smallRadius * 2, smallRadius * 2);
		g.drawLine((int) getFrontX(), (int) getFrontY(), (int) (getFrontX() + gunLength * Math.cos (orientation)), (int) (getFrontY() - gunLength * Math.sin(orientation)));
		
		for (int j = 0; j < myBullets.size(); j++)
		{
			myBullets.get(j).draw(g);
		}
	}
}