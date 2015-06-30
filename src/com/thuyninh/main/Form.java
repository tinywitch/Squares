
package com.thuyninh.main;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import com.thuyninh.audioplay.buttonOver;
import com.thuyninh.audioplay.gamemusic;
import com.thuyninh.audioplay.menumusic;
import com.thuyninh.squares.Ally;
import com.thuyninh.squares.Enemy;
import com.thuyninh.squares.MyCursorSquare;
import com.thuyninh.squares.Ornament;

public class Form extends JPanel implements MouseListener, MouseMotionListener
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	public final static int		WIDTH				= 600;
	public final static int		HEIGHT				= 600;
	private int					x					= 250;
	private int					y					= 250;
	private int					SIZE				= 30;
	private ArrayList<Ornament>	ornament			= new ArrayList<Ornament>();
	private ArrayList<Ally>		ally				= new ArrayList<Ally>();
	private ArrayList<Enemy>	enemy				= new ArrayList<Enemy>();
	private final int			AMOUNT_OF_SQUARE	= 5;
	private int					recStartingVisible	= 0;
	private int					stillVisible		= 0;
	private boolean				startGame			= false;
	private boolean				finishGame			= false;
	private MyCursorSquare		cursor;
	private int					bestScore;
	public int					score				= 0;
	private int					musicloop			= 0;
	private menumusic			mn;
	private gamemusic			gm;
	public boolean				congratulation		= false;
	BufferedImage				cursorImg			= new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor						blankCursor			= Toolkit.getDefaultToolkit().createCustomCursor(
															cursorImg, new Point(0, 0), "blank cursor");

	public Form()
	{
		mn = new menumusic(); // tạo nhạc game
		gm = new gamemusic();
		setSize(WIDTH, HEIGHT);
		addMouseMotionListener(this);
		setNewOrnamentSquares();
		bestScore = (ScoreSaved.getProperty("bestScore")); //đọc bestScore từ file thuộc tính rồi gán vào biến
		cursor = new MyCursorSquare(x, y, SIZE, this); 
		addMouseListener(this);
		new Thread(new PaintThread()).start();
	}

	public void setNewOrnamentSquares()
	{
		//tạo anim lúc màn hình start và game over
		for (int i = 0; i < 10; i++)
			ornament.add(new Ornament());

	}

	public void setNewAllySquares()
	{
		//tạo các hình vuông màu đen và có ích
		for (int i = 0; i < AMOUNT_OF_SQUARE; i++)
			ally.add(new Ally());
	}

	public void setNewEnemySquares()
	{
		// tạo hình vuông màu đỏ, ăn phải thì game over
		for (int i = 0; i < AMOUNT_OF_SQUARE; i++)
			enemy.add(new Enemy());
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g2);
		
		if (startGame == false && finishGame == false)
		{
			paintStartGame(g2);
		}
		else if (startGame == true && finishGame == false)
		{
			paintPlayGame(g2);
		}
		else if (startGame == true && finishGame == true)
		{
			paintFinishGame(g2);
		}
	}

	public void paintStartGame(Graphics2D g)
	{
		if (musicloop == 0) //việc bắt đầu đoạn nhạc sẽ chỉ thực hiện một lần
		{
			mn.startAudio(); // bật nhạc start game
			gm.stopAudio(); // tắt nhạc khi đang chơi game
			musicloop++;
		}

		this.setCursor(Cursor.getDefaultCursor());
		g.setFont(new Font("consolas", Font.BOLD, 80));
		g.drawString("SQUARES", 50, 150);
		g.setFont(new Font("consolas", Font.BOLD, 40));
		g.drawString("Play", x + 10, y + 40);
		g.drawString("Exit", x + 10, y + 90);

		Iterator<Ornament> e = ornament.iterator();
		{
			while (e.hasNext())
			{
				Ornament r = e.next();
				if (r.isOutofFrame())
				{
					e.remove();
				}
				else
					r.draw(g);
			}
		}
		if (ornament.size() == 0)
			setNewOrnamentSquares();
		if (recStartingVisible != 0)
		{
			cursor.drawRect(g);
		}
	}

	public void paintPlayGame(Graphics2D g)
	{
		if (musicloop == 0)
		{
			gm.startAudio(); // bật nhạc khi đang chơi game
			mn.stopAudio(); //tắt nhạc start game
			musicloop++;
		}
		this.setCursor(blankCursor);
		Iterator<Ally> a = ally.iterator();
		{
			while (a.hasNext())
			{
				Ally ta = a.next();
				if (ta.isOutofFrame())
				{
					a.remove();
				}
				else
					ta.draw(g);
			}
		}
		if (ally.size() < AMOUNT_OF_SQUARE)
			ally.add(new Ally()); // nếu trên gamemap giảm số lượng ô vuông thì tăng bổ sung

		Iterator<Enemy> e = enemy.iterator();
		{
			while (e.hasNext())
			{
				Enemy te = e.next();
				if (te.isOutofFrame())
				{
					e.remove();
				}
				else
					te.draw(g);
			}
		}
		if (enemy.size() < AMOUNT_OF_SQUARE)
			enemy.add(new Enemy()); // nếu trên gamemap giảm số lượng ô vuông thì tăng bổ sung

		g.setColor(Color.black);
		g.setFont(new Font("Consolas", Font.BOLD, 40));
		g.drawString("Score:", 50, 70);
		g.setFont(new Font("Consolas", Font.BOLD, 80));
		g.setColor(Color.ORANGE);
		g.drawString(score + "", 190, 80);
		g.setFont(new Font("Consolas", Font.BOLD, 40));
		g.setColor(Color.black);
		g.drawString("Best score:", 300, 70);
		g.drawString(bestScore + "", 580, 70);
		recStartingVisible = 0;
		cursor.drawRect(g);
		cursor.collidesWithAlly(ally);
		cursor.collidesWithEnemy(enemy);
	}

	public void paintFinishGame(Graphics2D g)
	{
		if (musicloop == 0)
		{
			mn.startAudio();
			gm.stopAudio();
			musicloop++;
		}
		this.setCursor(Cursor.getDefaultCursor());
		g.setFont(new Font("consolas", Font.BOLD, 40));
		g.drawString("Replay", x + 10, y + 40);
		g.drawString("Exit", x + 10, y + 90);
		if (score > bestScore) //ghi thành tích mới
		{
			bestScore = score; 
			ScoreSaved.setProperty("bestScore", bestScore + "");
			congratulation = true;
		}
		g.setColor(Color.black);
		g.setFont(new Font("Consolas", Font.BOLD, 40));
		g.drawString("Score:", 180, 150);
		g.drawString(score + "", 350, 150);
		g.drawString("Best score:", 180, 200);
		g.drawString(bestScore + "", 460, 200);
		
		if (congratulation)
		{
			g.setColor(Color.red);
			g.setFont(new Font("Consolas", Font.BOLD, 60));
			g.drawString("New High Score!", 80, 450);
		}
		enemy.removeAll(enemy);
		ally.removeAll(ally);
		Iterator<Ornament> e = ornament.iterator();
		{
			while (e.hasNext())
			{
				Ornament r = e.next();
				if (r.isOutofFrame())
				{
					e.remove();
				}
				else
					r.draw(g);
			}
		}
		if (ornament.size() == 0)
			setNewOrnamentSquares();
		if (recStartingVisible != 0)
			cursor.drawRect(g);
	}

	public class PaintThread implements Runnable
	{
		@Override
		public void run()
		{
			while (true)
			{
				repaint();
				try
				{
					Thread.sleep(7);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		if (startGame == true && finishGame == false) // playing, not game over
		{
			cursor = new MyCursorSquare(arg0.getX(), arg0.getY(), SIZE - 10, this);
		}

		else
		{
			if (startGame == false && finishGame == false) // prepare for starting playing
			{
				if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY())
						&& (arg0.getY() < y + 50))
				{
					if (recStartingVisible != 1)
					{
						recStartingVisible = 1;
						stillVisible = 1;
					}
				}
				else if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
						&& (arg0.getY() < y + 100))
				{

					if (recStartingVisible != 2)
					{
						recStartingVisible = 2;
						stillVisible = 1;
					}
				}
				else
					recStartingVisible = 0;
			}

			else if (startGame == true && finishGame == true) // game was started and being gameover
			{
				if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY())
						&& (arg0.getY() < y + 50))
				{
					if (recStartingVisible != 1)
					{
						recStartingVisible = 1;
						stillVisible = 1;
					}
				}
				else if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
						&& (arg0.getY() < y + 100))
				{
					if (recStartingVisible != 2)
					{
						recStartingVisible = 2;
						stillVisible = 1;
					}
				}
				else
					recStartingVisible = 0;
			}

			if (stillVisible == 1)
			{
				switch (recStartingVisible)
				{
					case 1:
						cursor = new MyCursorSquare(x - 50, y + 10, SIZE, this);
						break;
					case 2:
						cursor = new MyCursorSquare(x - 50, y + 60, SIZE, this);
						break;
				}
				stillVisible++;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		if (startGame == true && finishGame == false) // playing, not game over
		{
			cursor = new MyCursorSquare(arg0.getX(), arg0.getY(), SIZE - 10, this);
		}

		else
		{
			if (startGame == false && finishGame == false) // prepare for starting playing
			{

				if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY())
						&& (arg0.getY() < y + 50))
				{
					if (recStartingVisible != 1)
					{
						recStartingVisible = 1;
						stillVisible = 1;
					}
				}
				else if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
						&& (arg0.getY() < y + 100))
				{

					if (recStartingVisible != 2)
					{
						recStartingVisible = 2;
						stillVisible = 1;
					}
				}
				else
					recStartingVisible = 0;
			}

			else if (startGame == true && finishGame == true) // game was started and being gameover
			{
				if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY())	&& (arg0.getY() < y + 50))
				{
					if (recStartingVisible != 1)
					{
						recStartingVisible = 1;
						stillVisible = 1;
					}
				}
				else if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())	&& (arg0.getY() < y + 100))
				{
					if (recStartingVisible != 2)
					{
						recStartingVisible = 2;
						stillVisible = 1;
					}
				}
				else
					recStartingVisible = 0;
			}

			if (stillVisible == 1)
			{
				switch (recStartingVisible)
				{
					case 1:
						cursor = new MyCursorSquare(x - 50, y + 10, SIZE, this);
						new buttonOver();
						break;
					case 2:
						cursor = new MyCursorSquare(x - 50, y + 60, SIZE, this);
						new buttonOver();
						break;
				}
				stillVisible++;
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		if ((startGame == false && finishGame == false) || (startGame == true && finishGame == true))
		{
			if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
					&& (arg0.getY() < y + 100))
			{
				System.exit(0);
			}
			if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY()) && (arg0.getY() < y + 50))
			{
				startGame = true;
				finishGame = false;
				cursor = new MyCursorSquare(arg0.getX(), arg0.getY(), SIZE - 10, this);
				score = 0;
				musicloop = 0;
				congratulation = false;
				setNewAllySquares();
				setNewEnemySquares();
				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		if (startGame == true && finishGame == false) // playing, not game over
		{
			cursor = new MyCursorSquare(arg0.getX(), arg0.getY(), SIZE - 10, this);
		}

		else
		{
			if (startGame == false && finishGame == false) // prepare for
															// starting playing
			{
				if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY())
						&& (arg0.getY() < y + 50))
				{
					if (recStartingVisible != 1)
					{
						recStartingVisible = 1;
						stillVisible = 1;
					}
				}
				else if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
						&& (arg0.getY() < y + 100))
				{

					if (recStartingVisible != 2)
					{
						recStartingVisible = 2;
						stillVisible = 1;
					}
				}
				else
					recStartingVisible = 0;
			}

			else if (startGame == true && finishGame == true) // game was
																// started and
																// being
																// gameover
			{
				if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY())
						&& (arg0.getY() < y + 50))
				{
					if (recStartingVisible != 1)
					{
						recStartingVisible = 1;
						stillVisible = 1;
					}
				}
				else if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
						&& (arg0.getY() < y + 100))
				{
					if (recStartingVisible != 2)
					{
						recStartingVisible = 2;
						stillVisible = 1;
					}
				}
				else
					recStartingVisible = 0;
			}

			if (stillVisible == 1)
			{
				switch (recStartingVisible)
				{
					case 1:
						cursor = new MyCursorSquare(x - 50, y + 10, SIZE, this);
						break;
					case 2:
						cursor = new MyCursorSquare(x - 50, y + 60, SIZE, this);
						break;
					default:
						break;
				}
				stillVisible++;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
		if (startGame == true && finishGame == false) // playing, not game over
		{
			cursor = new MyCursorSquare(arg0.getX(), arg0.getY(), SIZE - 10, this);
		}

		else
		{
			if (startGame == false && finishGame == false) // prepare for starting playing
			{
				if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY())
						&& (arg0.getY() < y + 50))
				{
					if (recStartingVisible != 1)
					{
						recStartingVisible = 1;
						stillVisible = 1;
					}
				}
				else if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
						&& (arg0.getY() < y + 100))
				{

					if (recStartingVisible != 2)
					{
						recStartingVisible = 2;
						stillVisible = 1;
					}
				}
				else
					recStartingVisible = 0;
			}

			else if (startGame == true && finishGame == true) // game was started and being gameover
			{
				if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY())
						&& (arg0.getY() < y + 50))
				{
					if (recStartingVisible != 1)
					{
						recStartingVisible = 1;
						stillVisible = 1;
					}
				}
				else if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
						&& (arg0.getY() < y + 100))
				{
					if (recStartingVisible != 2)
					{
						recStartingVisible = 2;
						stillVisible = 1;
					}
				}
				else
					recStartingVisible = 0;
			}

			if (stillVisible == 1)
			{
				switch (recStartingVisible)
				{
					case 1:
						cursor = new MyCursorSquare(x - 50, y + 10, SIZE, this);
						break;
					case 2:
						cursor = new MyCursorSquare(x - 50, y + 60, SIZE, this);
						break;
				}
				stillVisible++;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{

		if ((startGame == false && finishGame == false) || (startGame == true && finishGame == true))
		{
			if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y + 50 < arg0.getY())
					&& (arg0.getY() < y + 100))
			{
				System.exit(0);
			}
			if ((x < arg0.getX()) && (arg0.getX() < x + 150) && (y < arg0.getY()) && (arg0.getY() < y + 50))
			{
				startGame = true;
				finishGame = false;
				cursor = new MyCursorSquare(arg0.getX(), arg0.getY(), SIZE - 10, this);
				musicloop = 0;
				score = 0;
				congratulation = false;
				setNewAllySquares();
				setNewEnemySquares();
				repaint();
			}
		}
	}

	public boolean isStartGame()
	{
		return startGame;
	}

	public void setStartGame(boolean startGame)
	{
		this.startGame = startGame;
	}

	public boolean isFinishGame()
	{
		return finishGame;
	}

	public void setFinishGame(boolean finishGame)
	{
		this.finishGame = finishGame;
	}

	public void setMusicloop(int musicloop)
	{
		this.musicloop = musicloop;
	}

}
