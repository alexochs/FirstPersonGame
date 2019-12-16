package firstpersongame;

import processing.core.PApplet;

enum CellType
{
	VOID,
	FLOOR,
	WALL;
}

public class Cell 
{
	private PApplet p;
	private float x, y;
	public CellType type;
	public int color;
	public float width, height;
	public float[] bounds = new float[4];
	public float[] screenX = new float[2];
	public float[] screenY = new float[2];
	public int id;
	
	public void draw2D()
	{	
		p.stroke(1);
		p.fill(color);
		p.square(x, y, width);
	}
	
	public void draw3D()
	{
		p.fill(color);
		p.quad(screenX[0], screenY[0], screenX[1], screenY[0], screenX[1], screenY[1], screenX[0], screenY[1]);
	}
	
	public void draw()
	{
		
	}
	
	private CellType parseType(int type)
	{
		switch(type)
		{
		case 0: return CellType.VOID;
		case 1: return CellType.FLOOR;
		case 2: return CellType.WALL;
		default: return CellType.VOID;
		}
	}
	
	Cell(PApplet p, int type, float x, float y, int id)
	{
		this.p = p;
		this.type = parseType(type);
		width = height = 32;
		this.x = x*width;
		this.y = y*height;
		bounds[0] = this.x;
		bounds[1] = this.x+width;
		bounds[2] = this.y;
		bounds[3] = this.y+height;
		this.id = id;
		
		switch(this.type)
		{
		case VOID: color = 0; break;
		case FLOOR: color = 150; break;
		case WALL: color = 255; break;
		default: color = 0; break;
		}
	}
}
