package firstpersongame;

import processing.core.PApplet;
import processing.core.PConstants;

public class Ray
{
	private PApplet p;
	private WorldManager world;
	float x, y;
	Cell hitCell;
	float length;
	float degree;
	
	float calculateLength()
	{
		float dx = Math.abs(x - world.player.x);
		float dy = Math.abs(y - world.player.y);
		float angle = (float)(-PConstants.QUARTER_PI + (PConstants.PI/world.numOfRays/2 * degree));
		return (float)(Math.sqrt((dx*dx) + (dy*dy)) * Math.cos(angle)); //whack fisheye method
	}
	
	boolean didHitWall()
	{
		//Loop through cells and check if we are in its bounds
		for(Cell cell : world.level.cells)
		{	
			if(cell.type == CellType.WALL)
			{
				boolean hitX = (x >= cell.bounds[0] && x <= cell.bounds[1]) ? true : false;
				boolean hitY = (y >= cell.bounds[2] && y <= cell.bounds[3]) ? true : false;
				if(hitX && hitY)
				{
					hitCell = cell;
					length = calculateLength();
					return true;
				}
			}
		}
		
		return false;
	}
	
	void cast(int deg)
	{
		//Setup
		float step = 1;
		deg++;
		
		//Calculate Ray
		x = world.player.x + (float)(Math.cos((world.player.angle) * step));
		y = world.player.y + (float)(Math.sin((world.player.angle) * step));
		
		//Continue to extend Ray till we hit a wall
		while(!didHitWall())
		{
			if(step > 1000)
			{
				length = -1;
				return;
			}
			
			x = world.player.x + (float)(Math.cos(world.player.angle + (-PConstants.QUARTER_PI + (PConstants.PI/world.numOfRays/2 * deg))) * step);
			y = world.player.y + (float)(Math.sin(world.player.angle + (-PConstants.QUARTER_PI + (PConstants.PI/world.numOfRays/2 * deg))) * step);
			
			step++;
		}
	}
	
	void draw()
	{
		//TODO: fix fisheye (euclidean angle), draw far to near
		
		if(length == -1)
			return;
		
		float xLeft = degree/world.numOfRays * p.width;
		float xRight = xLeft + p.width/world.fov;
		float yTop = p.height/2 - (p.height/(length/64));
		float yBottom = p.height/2 + (p.height/(length/64));
		
		p.fill(hitCell.color - ((float)(length/1.5)));
		p.quad(xLeft, yTop, xRight, yTop, xRight, yBottom, xLeft, yBottom);
	}
	
	Ray(PApplet p, WorldManager world, int deg) { this.p = p; this.world = world; x = y = 0; hitCell = null; length = 0; degree = deg; cast(deg); }
}
