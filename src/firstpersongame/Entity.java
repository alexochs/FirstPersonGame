package firstpersongame;

import processing.core.PApplet;

public class Entity
{
	protected WorldManager world;
	public Vector pos;
	
	protected Entity(PApplet p, WorldManager world, float x, float y)
	{
		this.p = p;
		this.world = world;
		pos = new Vector(x, y);
	}
	
	protected PApplet p;
}
