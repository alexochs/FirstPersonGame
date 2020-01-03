package firstpersongame;

import processing.core.PApplet;

public class WorldManager
{
	private PApplet p;
	public Player player;
	public Level level;
	public boolean mode2d;
	public final float fov = 45;
	public final int numOfRays = 90;
	
	void draw()
	{
		p.noStroke();
		level.draw();
		player.draw();
	}
	
	void keyPressed()
	{
		player.keyPressed();
		if(p.key == 'm')
			mode2d = !mode2d;
	}
	
	void keyReleased()
	{
		player.keyReleased();
	}
	
	WorldManager(PApplet p)
	{
		this.p = p;
		this.mode2d = false;
		player = new Player(p, this, 128, 128);
		String map = "2222222222211111111221111111122222122222211111111221111111122111221112211111111221111111122222222222";
		level = new Level(p, this, map, 10, 10);
	}
}
