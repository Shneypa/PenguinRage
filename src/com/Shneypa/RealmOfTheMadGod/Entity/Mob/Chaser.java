package com.Shneypa.RealmOfTheMadGod.Entity.Mob;


import java.util.List;

import com.Shneypa.RealmOfTheMadGod.graphics.AnimatedSprite;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;
import com.Shneypa.RealmOfTheMadGod.graphics.SpriteSheet;

public class Chaser extends Mob{
	
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.bard_left, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.bard_up, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.bard_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.bard_down, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private double xa = 0;
	private double ya = 0;
	private double speed = 0.8;
	
	public Chaser(int x, int y) {
		this.x = x << 4; // into tile precision
		this.y = y << 4; 
		sprite = Sprite.bard;
	}

	// CHASER'S MOVE method that FOLLOWS the PLAYER
	// For that we need to know the player's position
	private void move() {
		xa = 0;
		ya = 0;
		
		
		List<Player> players = level.getPlayers(this, 100);
		// System.out.println("players.size() = " + players.size() + "        - Chaser.java" );
		if (players.size() > 0) {
		Player player = players.get(0);
		if (x < player.getX()) xa+=speed;  // if Chaser's x coordinate is less than player's x coordinate then Chaser should go right 
		if (x > player.getX()) xa-=speed;
		if (y < player.getY()) ya+=speed;
		if (y > player.getY()) ya-=speed;
		}
	
		
		if (xa != 0 || ya != 0) {
			move(xa, ya); 
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void update() {
		move();
		
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		
		if (ya < 0 ) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {			// else is here so we can't select both up and down at one time
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
	
		
	
		
	}


	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16), (int)(y - 16), this); // casting correct
		
	}
}
