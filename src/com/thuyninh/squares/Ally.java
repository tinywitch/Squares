package com.thuyninh.squares;

import java.util.Random;

public class Ally extends RectRandom
{
	private Random ran = new Random();
	private int parameterOfSpeed = 2;

	public Ally()
	{
		type = TypeofSquare.A;
		size =25;
		speed =ran.nextInt(this.parameterOfSpeed) + this.parameterOfSpeed;
	}
	
	public int getSize()
	{
		return size;
	}
	public void setSize(int size)
	{
		this.size = size;
	}

	public int getParameterOfSpeed()
	{
		return parameterOfSpeed;
	}

	public void setParameterOfSpeed(int parameterOfSpeed)
	{
		this.parameterOfSpeed = parameterOfSpeed;
	}
	
}
