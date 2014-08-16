package com.Shneypa.RealmOfTheMadGod.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {				// Q13. KeyListener. 

	private boolean[] keys = new boolean[120]; 
	public boolean up, down, left, right;
	
	// every update we assign "true" of "false"  for each of WASD or ARROW keys. If it happens to be "true", then our variable becomes 'true' for current update cycle 
	public void update()
	{
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		
	}
		
	/*
		for (int i = 0; i < keys.length; i++)      //   cycle which checks every element in keys array and if it is true (i.e. it is being pressed, then it prints out the number of key being pressed
		{											//
			if (keys[i]) 							//
			{										//
			System.out.println("KEY: " + i + " (Keyboard.java)");		//
			
			}
		}
		*/	
	
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;				// KeyListener tracks occurance of KeyEvents. If it occured, we assign its array spot as "true"
		
	}

	
	public void keyReleased(KeyEvent e) {
		
		keys[e.getKeyCode()] = false;
	}

	
	public void keyTyped(KeyEvent e) {
		
		
	}

}
