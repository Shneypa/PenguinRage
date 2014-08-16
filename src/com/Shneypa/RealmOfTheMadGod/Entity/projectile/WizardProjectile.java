package com.Shneypa.RealmOfTheMadGod.Entity.projectile;

import com.Shneypa.RealmOfTheMadGod.Entity.Spawner.ParticleSpawner;
import com.Shneypa.RealmOfTheMadGod.Entity.Spawner.Spawner;
import com.Shneypa.RealmOfTheMadGod.Level.Level;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;

public class WizardProjectile extends Projectile {

	public static final int FIRE_RATE = 6; 		// actually, delay between shots. the higher the number, the slower actual rate of fire. 
	
	public WizardProjectile(double x, double y, double dir) { // doubles correct
		super(x, y, dir);
		range = random.nextInt(100) + 150; // range is randomly between 150 and 250
		speed = 3.5;
		damage = 20;
		sprite = Sprite.yellowDot;
		
		nx = Math.cos(angle) * speed;
		ny = Math.sin(angle) * speed;
	}
	
	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 7, 4, 5)) {
			level.add(new ParticleSpawner((int) x, (int) y, 90, 20, level));
			remove();
		}
		move();
	}
	
	protected void move() {
		// System.out.println("x: " + x + "                    (WizardProjectile)"); // test to see that projectiles keep going long after they're gone from the screen. Forever. Very bad for performance. We need to stop them 
		
		x += nx;
		y += ny;
		if (distance() > range) remove();
	// 	System.out.println("Distance: " + distance() + "                    (WizardProjectile.java)"); 
	}
	
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x)*(xOrigin - x) + (yOrigin - y)*(yOrigin - y))); 
		return dist;
	}
	
	public void render(Screen screen) {
		screen.renderTile((int) x - 12, (int) y - 6, sprite);			// cast x and y back into integers from double which they are in Projectile class
	}
	
	
}
