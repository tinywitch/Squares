
package com.thuyninh.audioplay;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class menumusic
{
	private URL					url;
	private AudioInputStream	audioIn;
	Clip						clip;

	public menumusic()
	{
		setClip();
	}

	private void setClip()
	{
		try
		{
			url = this.getClass().getClassLoader().getResource("com/thuyninh/audio/menumusic.wav");
			audioIn = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioIn);
		}
		catch (UnsupportedAudioFileException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	public void stopAudio()
	{
		this.clip.stop();
	}

	public void startAudio()
	{
		this.clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public Clip getClip()
	{
		return clip;
	}

	public void setClip(Clip clip)
	{
		this.clip = clip;
	}

}
