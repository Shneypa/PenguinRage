package com.Shneypa.RealmOfTheMadGod.Entity.Mob;

import java.util.List;

import com.Shneypa.RealmOfTheMadGod.Entity.Entity;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Mob.Direction;
import com.Shneypa.RealmOfTheMadGod.Entity.particle.Particle;
import com.Shneypa.RealmOfTheMadGod.graphics.AnimatedSprite;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;
import com.Shneypa.RealmOfTheMadGod.graphics.SpriteSheet;
import com.Shneypa.RealmOfTheMadGod.util.Debug;
import com.Shneypa.RealmOfTheMadGod.util.Vector2i;

public class Shooter extends Mob {
	
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.bard_left, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.bard_up, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.bard_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.bard_down, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	private int time = 0;
	private int xa = 0, ya = 0;
	private int timeBetweenShots = 0;
	private Entity rand = null;
	
	
	// INITIALIZATION
	public Shooter(int x, int y) {
		this.x = x * 16;
		this.y = y * 16;
		sprite = Sprite.bard;
	
	}
	
	// LOGIC 
	// EPISODE 103: 
	// Movement
	public void update() {
		// System.out.println("Shooter X = " + x + " Shooter Y = " + y);
		time++; 
		if (time % (random.nextInt(50) + 30) == 0) {	    
			xa = random.nextInt(3) - 1; 
			ya = random.nextInt(3) - 1;
			if (random.nextInt(3) == 0 ) {    
				xa = 0; 
				ya = 0;
			}
		}
		
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		
		if (ya < 0 ) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {			
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {		
			animSprite = right;
			dir = Direction.RIGHT;
		}
	
		if (xa != 0 || ya != 0) {
			move(xa, ya); 
			walking = true;
		} else {
			walking = false;
		}
		shootRandom();		
	}

	// SHOOTING RANDOM MOBS (Entities) 
	private void shootRandom() {
		List<Entity> entities = level.getEntities(this, 60);
		entities.add(level.getClientPlayer());
		
		if (time % (30 + random.nextInt(91)) == 0) {					// delay for switching targets
			int index = random.nextInt(entities.size()); 
			rand = entities.get(index);
		}	
			
		if (rand != null && (rand instanceof Mob || rand instanceof Player) ) {				// Стреляет по игроку и мобам. 
			double dx =  rand.getX() - x;
			double dy =  rand.getY() - y;
			double dir = Math.atan2(dy, dx);  // Y first !!!
		
			timeBetweenShots++;	
			if (timeBetweenShots % 20 == 0)  
			shoot(x + 8, y + 8, dir);
		}
	}

		
	// Shooting CLOSEST entity
	private void shootClosest() {
		List<Entity> entities = level.getEntities(this, 500);
		entities.add(level.getClientPlayer());
		
		double minDistance = 0;
		Entity closest = null;
				
		for (int i = 0; i < entities.size(); i++) {											// for every entity	...									
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i((int)x,(int)y), new Vector2i((int)e.getX(),(int)e.getY())); 	// ... we calculate distance from shooter mob to that entity
												// ^^ casting by myself 
			if (i == 0 || distance < minDistance) { 
				minDistance = distance;
				closest = e;
			}
				}
		
		
		// At first it was getting player's coordinates and then shot at the player ;) 
		// Player p = level.getClientPlayer();
		if (closest != null) {
		double dx =  closest.getX() - x;
		double dy =  closest.getY() - y;
		double dir = Math.atan2(dy, dx);  // Y first !!!
		
		timeBetweenShots++;	
		if (timeBetweenShots % 45 == 0)  shoot(x + 8, y + 8, dir);
		}
	}
	

	
	// VISUAL
	public void render(Screen screen) {
		Debug.drawRect(screen, 40, 40, 100, 40, 0xffffff, true);
		// screen.renderSprite(80, 80, new Sprite (80, 80, 0xff0000), false);
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16), (int)(y-16), this);  // ??? why casting to integer
	//	System.out.println("render X = " + x + " render Y = " + y);
	}

}
