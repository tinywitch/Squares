package com.thuyninh.squares;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.thuyninh.main.SquareMain;

public class RectRandom
{
	private int x, y;
	private Direction dir;
	private Random ran = new Random();
	protected int speed;
	protected int size;
	private boolean outofFrame ;
	private Map<TypeofSquare, Color> drawingSquare = new HashMap<TypeofSquare, Color>();
	protected TypeofSquare type ;
	protected int widthLimit ,heightLimit, widthStart, heightStart ;
	public RectRandom()
	{
		outofFrame = false;
		Direction[] dirs = Direction.values();
		int temp = ran.nextInt(dirs.length);
		dir = dirs[temp];
		setLimit(); //tạo các giới hạn cho hình vuông trên gamemap
		setDrawingSquare();
		setStart();
		
	}
	
	public void setLimit()
	{
		heightLimit = SquareMain.HEIGHT;
		widthLimit = SquareMain.WIDTH;
		widthStart=0;
		heightStart=0;
	}
	
	public void setDrawingSquare()
	{
		drawingSquare.put(TypeofSquare.A, Color.black); // ally. màu của ô vuông do người chơi điều khiển và cần ăn được
		drawingSquare.put(TypeofSquare.E, Color.red); // enemy, màu của những ô vuông mà người dùng k đc chạm vào
		drawingSquare.put(TypeofSquare.O, new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255))); // dùng để trang trí
	}
	
	public void draw(Graphics g)
	{
		g.setColor(drawingSquare.get(type));
		g.fillRect(x, y, size, size);
		move();
	}
	
	public void setStart()
	{
		switch (dir)
		{
			case L:
				x =widthLimit-30;
				y = ran.nextInt(heightLimit-100) +50;
				break;
			case R:
				x =widthStart;
				y = ran.nextInt(heightLimit-100 ) +50;
				break;
			case U:
				y =heightLimit-30;
				x = ran.nextInt(widthLimit-100) +50;
				break;
			case D:
				y =heightStart;
				x = ran.nextInt(widthLimit-100 ) +50;
				break;	
		}
	}
	
	public void move()
	{
		switch (dir)
		{
			case L:
				x -= speed;
				break;
			case R:
				x += speed;
				break;
			case U:
				y -= speed;
				break;
			case D:
				y += speed;
				break;
		} 
		if (x<widthStart || x>this.widthLimit  || y <heightStart || y>this.heightLimit )
			outofFrame = true;
	}
	
	public Rectangle getRect()
	{
		return new Rectangle((int) x,(int) y,size,size);
	}

	public Direction getDir()
	{
		return dir;
	}

	public void setDir(Direction dir)
	{
		this.dir = dir;
	}

	public boolean isOutofFrame()
	{
		return outofFrame;
	}

	public void setOutofFrame(boolean outofFrame)
	{
		this.outofFrame = outofFrame;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}
}
