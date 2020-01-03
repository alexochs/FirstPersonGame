package firstpersongame;

import java.util.ArrayList;
import processing.core.PApplet;

public class Level
{
	private PApplet p;
	private WorldManager world;
	private String map;
	private int width, height;
	public ArrayList<Cell> cells = new ArrayList<Cell>();
	private Ray[] rays;
	
	private void tick()
	{
		//Create Rays
		rays = new Ray[p.width];
		for(int i = 0; i < p.width; i++)
		{
			rays[i] = new Ray(p, world, i);
		}
	}
	
	private void draw2D()
	{
		for(Cell cell : cells)
			cell.draw2D();
		
		for(Ray ray : rays)
		{
			if(ray.length == -1)
				continue;
			
			p.line(world.player.pos.x, world.player.pos.y, ray.x, ray.y);
		}
	}
	
	private void draw3D()
	{
		//p.fill(0);
		p.fill(7, 11, 52);
		p.rect(0, 0, p.width, p.height/2);
		p.fill(100);
		p.rect(0, p.height/2, p.width, p.height);
		
		for(Ray ray : rays)
			ray.draw();
	}
	
	public void draw()
	{
		tick();
		
		if(world.mode2d)
			draw2D();
		else
			draw3D();
	}
	
	Level(PApplet p, WorldManager world, String map, int width, int height)
	{
		this.p = p;
		this.world = world;
		this.map = map;
		this.width = width;
		this.height = height;
		
		int currentCell = 0;
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				cells.add(new Cell(p, Integer.parseInt(String.valueOf(map.charAt(currentCell))), j, i, currentCell));
				currentCell++;
			}
		}
	}
}
