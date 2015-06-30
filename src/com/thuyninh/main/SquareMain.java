package com.thuyninh.main;

import javax.swing.JFrame;

public class SquareMain extends JFrame
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	Form form = new Form();
	public final static int WIDTH = 650;
	public final static int HEIGHT = 650;
	public SquareMain()
	{
		super ();
		add(form);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(WIDTH,HEIGHT);
		setResizable(false);
		JFrame.setDefaultLookAndFeelDecorated(true);
		setLocation(150, 50);
       // setUndecorated(true);
		setVisible(true);
	}
	public static void main(String[] args)
	{
		new SquareMain();
	}
}
