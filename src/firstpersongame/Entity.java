package firstpersongame;

import processing.core.PApplet;

public class Entity
{
	protected WorldManager world;
	public float x, y;
	
	protected Entity(PApplet p, WorldManager world, float x, float y)
	{
		this.p = p;
		this.world = world;
		this.x = x;
		this.y = y;
	}
	
	protected PApplet p;
}
