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
	float screenX;
	float angle;
	float step;
	
	float calculateLength()
	{
		//float dx = Math.abs(x - world.player.pos.x);
		//float dy = Math.abs(y - world.player.pos.y);
		//float hypo = (float)(Math.sqrt((dx*dx) + (dy*dy)));
		float adj = (float)(Math.sqrt((world.player.direction.x*world.player.direction.x) + (world.player.direction.y*world.player.direction.y)));
		//return hypo;
		return adj*step;
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
	
	void cast()
	{	
		//Calculate Ray
		float camX = 2 * screenX/p.width - 1;
		float dirX = (float)(world.player.direction.x + world.player.plane.x*0.66*camX);
		float dirY = (float)(world.player.direction.y + world.player.plane.y*0.66*camX);
		
		x = (float)(world.player.pos.x + dirX * step);
		y = (float)(world.player.pos.y + dirY * step);
		
		//Continue to extend Ray till we hit a wall
		while(!didHitWall())
		{
			if(step > 500)
			{
				length = -1;
				return;
			}

			x = (float)(world.player.pos.x + dirX * step);
			y = (float)(world.player.pos.y + dirY * step);
			
			step++;
		}
	}
	
	void draw()
	{
		if(length == -1)
			return;
		
		float height = (p.height/(length/hitCell.height));
		float screenMid = p.height/2;
		p.stroke(hitCell.color - ((float)(length)));
		p.line(screenX, screenMid-height/2, screenX, screenMid+height/2);
	}
	
	Ray(PApplet p, WorldManager world, int curX) { this.p = p; this.world = world; x = y = 0; hitCell = null; length = 0; screenX = curX; this.step = 1; cast(); }
}
