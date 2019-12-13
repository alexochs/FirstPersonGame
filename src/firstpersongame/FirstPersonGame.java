package firstpersongame;

import processing.core.PApplet;

public class FirstPersonGame extends PApplet
{	
	private GameManager game;
	
	public static void main(String[] args)
	{
		String[] name = {"FirstPersonGame"};
		FirstPersonGame game =  new FirstPersonGame();
		PApplet.runSketch(name, game);
	}
	
	public void settings()
	{
		//size(displayWidth, displayHeight);
		size(800, 600);
	}
	
	public void setup() 
	{
		game = new GameManager(this);
	}

	public void draw() 
	{
		background(0);
		game.draw();
	}
	
	public void keyPressed()
	{
		game.keyPressed();
	}
	
	public void keyReleased()
	{
		game.keyReleased();
	}
}
