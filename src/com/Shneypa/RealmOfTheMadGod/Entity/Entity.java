package com.Shneypa.RealmOfTheMadGod.Entity;

import java.util.Random;

import com.Shneypa.RealmOfTheMadGod.Level.Level;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;

public class Entity {

	protected double x, y; // changed from int to doubles in EPISODE 97. for slow and precise movement
	protected Sprite sprite;
	private boolean removed = false;   // removed from the level or not
	protected Level level; 
	protected final Random random = new Random();
	
	public Entity() {
		       			 
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite; 
	}
	
	public void update() {
		
	}
	
	// casting is correct
	public void render(Screen screen) {
		if (sprite != null) screen.renderSprite((int)x, (int)y, sprite, true); // casting to integers ROUNDS DOWN.  for more precision we need to use Math.ceil and Math.floor
	}
	
	public void remove() {
		// remove from level		
		removed = true;
	}
	
	// double is correct
	public double getX() {
		return x;
	}
	
	// double is correct
	public double getY() {
		return y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level; 
	}
}
