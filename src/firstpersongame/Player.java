package firstpersongame;

import processing.core.PApplet;
import processing.core.PConstants;

public class Player extends Entity
{
	public float angle;
	private float speed;
	private boolean moveForward, moveBackward;
	private boolean turnRight, turnLeft;
	public Vector direction;
	public Vector plane;
	public float planeDist;
	
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
			angle += PConstants.PI/30;
		
		if(turnLeft)
			angle -= PConstants.PI/30;
	}
	
	private void move()
	{
		//Calculate new position
		float newX, newY; newX = newY = 0; 
		if(moveForward)
		{
			newX = pos.x + direction.x * speed;
			newY = pos.y + direction.y * speed;
		}
		if(moveBackward)
		{
			newX = pos.x - direction.x * speed;
			newY = pos.y - direction.y * speed;
		}
		
		//Check for bounds and return position
		float[] pos = checkBounds(this.pos.x, newX, this.pos.y, newY);
		
		//Set new position
		this.pos.x = pos[0];
		this.pos.y = pos[1];
	}
	
	public void tick()
	{	
		if(turnRight || turnLeft)
			turn();
		
		if(moveForward || moveBackward)
			move();
		
		//Calculate direction vector depending on speed and angle
		direction.x = (float)(Math.cos(angle) - Math.sin(angle));
		direction.y = (float)(Math.sin(angle) + Math.cos(angle));
		
		//Calculate camera plane
		float rot = (float)Math.toRadians(90);
		plane.x = (float)(Math.cos(angle+rot) - Math.sin(angle+rot));
		plane.y = (float)(Math.sin(angle+rot) + Math.cos(angle+rot));
	}
	
	private void draw2D()
	{
		//Player
		p.fill(0, 255, 0);
		p.circle(pos.x, pos.y, 10);
		p.stroke(10);
		
		//Direction Line
		p.line(pos.x, pos.y, pos.x + direction.x*planeDist, pos.y + direction.y*planeDist);
		
		//Camera Plane Line
		p.line(pos.x+direction.x*planeDist-plane.x*planeDist/2, pos.y+direction.y*planeDist-plane.y*planeDist/2, pos.x+direction.x*planeDist+plane.x*planeDist/2, pos.y+direction.y*planeDist+plane.y*planeDist/2);
	}
	
	private void draw3D()
	{
		
	}
	
	public void draw()
	{
		tick();
		
		//Draw Info
		//p.fill(0, 102, 153);
		//p.textSize(16);
		//p.text("X:" + pos.x + "   Y: " + pos.y, 50, 50);
		//p.text("Angle: " + angle, 50, 82);
		//p.text("Direction XY: " + direction.x + " " + direction.y, 50, 114);
		//p.text("FPS: " + p.frameRate, 50, 130);
		
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
		direction = new Vector();
		plane = new Vector();
		angle = 0;
		speed = 2;
		planeDist = 30;
	}
	
}
