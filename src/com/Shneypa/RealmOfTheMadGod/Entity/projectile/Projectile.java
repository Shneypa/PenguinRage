package com.Shneypa.RealmOfTheMadGod.Entity.projectile;

import java.util.Random;

import com.Shneypa.RealmOfTheMadGod.Entity.Entity;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;

public abstract class Projectile extends Entity {

	protected final double xOrigin, yOrigin; // double is correct
	protected double angle;
	protected Sprite sprite;
	protected double x, y; 
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	
	protected final Random random = new Random();
	
	public Projectile(double x, double y, double dir) { 	// double is correct
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x;
		this.y = y;
	}
	
	protected void move() {

	}
	
	public Sprite getSprite() {      // This is a getter for the spinoff renderProjectile method introduced in EPISODE 70. 
		return sprite; 
	}
	
	public int getSpriteSize() {	// This is a getter for the spinoff renderProjectile method introduced in EPISODE 70. 
		return sprite.SIZE;
	}

}
