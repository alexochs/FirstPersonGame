package firstpersongame;

import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends Entity
{
	public float angle;
	private float speed;
	private boolean moveForward, moveBackward;
	private boolean turnRight, turnLeft;
	
	private float[] checkBounds(float oldX, float newX, float oldY, float newY)
	{
		//"wall sliding"?
		//Loop through cells and check if we are in its bounds
		for(Cell cell : world.level.cells)
		{	
			if(cell.type == CellType.WALL)
			{
				boolean oobX = (newX >= cell.bounds[0] && newX <= cell.bounds[1]) ? true : false;
				boolean oobY = (newY >= cell.bounds[2] && newY <= cell.bounds[3]) ? true : false;
				
				if(oobX && oobY)
				{
					newX = oldX;
					newY = oldY;
				}
			}
		}
	
		float[] pos = { newX, newY };
		return pos;
	}
	
	private void turn()
	{
		//maybe clamp?
		if(turnRight)
			angle += PConstants.PI/90;
		
		if(turnLeft)
			angle -= PConstants.PI/90;
	}
	
	private void move()
	{
		//Calculate new position
		float newX, newY; newX = newY = 0; 
		if(moveForward)
		{
			newX = x + (float)(Math.cos(angle) * speed);
			newY = y + (float)(Math.sin(angle) * speed);
		}
		if(moveBackward)
		{
			newX = x - (float)(Math.cos(angle) * speed);
			newY = y - (float)(Math.sin(angle) * speed);
		}
		
		//Check for bounds and return position
		float[] pos = checkBounds(x, newX, y, newY);
		
		//Set new position
		x = pos[0];
		y = pos[1];
	}
	
	public void tick()
	{	
		if(turnRight || turnLeft)
			turn();
		
		if(moveForward || moveBackward)
			move();
	}
	
	private void draw2D()
	{
		//Player
		p.fill(0, 255, 0);
		p.circle(x, y, 20);
		p.stroke(10);
		p.line(x, y, (x + (float)(Math.cos(angle) * 25)), (y + (float)(Math.sin(angle) * 25)));
	}
	
	private void draw3D()
	{
		
	}
	
	public void draw()
	{
		tick();
		
		//Draw Info
		p.fill(0, 102, 153);
		p.textSize(16);
		p.text("X:" + x + "   Y: " + y, 50, 50);
		p.text("Angle: " + angle, 50, 82);
		p.text("FPS: " + p.frameRate, 50, 114);
		
		if(world.mode2d)
			draw2D();
		else
			draw3D();
	}
	
	void keyPressed()
	{	
		if(p.keyCode == PConstants.UP)
			moveForward = true;
		
		if(p.keyCode == PConstants.DOWN)
			moveBackward = true;
		
		if(p.keyCode == PConstants.RIGHT)
			turnRight = true;
		
		if(p.keyCode == PConstants.LEFT)
			turnLeft = true;
	}
	
	void keyReleased()
	{
		if(p.keyCode == PConstants.UP)
			moveForward = false;
		
		if(p.keyCode == PConstants.DOWN)
			moveBackward = false;
		
		if(p.keyCode == PConstants.RIGHT)
			turnRight = false;
		
		if(p.keyCode == PConstants.LEFT)
			turnLeft = false;
	}
	
	Player(PApplet p, WorldManager world, float x, float y)
	{
		super(p, world, x, y);
		angle = 0;
		speed = 8;
	}
	
}
