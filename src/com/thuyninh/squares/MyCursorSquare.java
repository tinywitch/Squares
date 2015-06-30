
package com.thuyninh.squares;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.thuyninh.audioplay.die;
import com.thuyninh.audioplay.squarewav;
import com.thuyninh.main.Form;

public class MyCursorSquare
{
	private float	a;
	private double	x, y;
	private int		SIZE;
	private Form f;

	public MyCursorSquare(double x, double y, int a, Form f)
	{
		this.x = x;
		this.y = y;
		this.SIZE = a;
		this.f = f;
	}
	
	
	public void drawRect(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
		java.awt.geom.Rectangle2D.Double rect = new Rectangle2D.Double(x,y,SIZE,SIZE);
		g2.rotate(Math.toRadians(a++), x + SIZE/2,y+ SIZE/2); // tạo hiệu ứng xoay cho hình vuông
		g2.fill(rect);
		
	}
	
	public boolean collidesWithAlly(Ally a)
	{
		// chạm phải ô vuông có ích
		if (this.getRect().intersects(a.getRect()))
		{
			a.setOutofFrame(true);
			new squarewav();
			f.score+=1;
			return true;
		}
		else return false;
	}
	
	public boolean collidesWithAlly(ArrayList<Ally> a)
	{
		for (Ally ally : a)
		{
			if (collidesWithAlly(ally))
			return true;
		}
		return false;
	}
	
	public boolean collidesWithEnemy(Enemy a)
	{
		// chạm ô vuông đỏ có hại
		if (this.getRect().intersects(a.getRect()))
		{
			new die();
			f.setFinishGame(true);
			f.setMusicloop(0);
			return true;
		}
		else return false;
	}
	
	public boolean collidesWithEnemy(ArrayList<Enemy> a)
	{
		for (Enemy enemy : a)
		{
			if (collidesWithEnemy(enemy))
			return true;
		}
		return false;
	}
	public Rectangle getRect()
	{
		return new Rectangle((int) x,(int) y,SIZE,SIZE);
	}
}
