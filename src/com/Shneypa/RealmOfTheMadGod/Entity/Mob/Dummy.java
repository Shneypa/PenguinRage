package com.Shneypa.RealmOfTheMadGod.Entity.Mob;

import com.Shneypa.RealmOfTheMadGod.graphics.AnimatedSprite;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;
import com.Shneypa.RealmOfTheMadGod.graphics.SpriteSheet;
import com.Shneypa.RealmOfTheMadGod.input.Keyboard;


public class Dummy extends Mob {
	
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.bard_left, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.bard_up, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.bard_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.bard_down, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private int time = 0; 
	int xa = 0;
	int ya = 0;
	
	// CONSTRUCTOR for our Dummy Mob with coordinates of where it should spawn
	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4; 
		sprite = Sprite.bard;
	}
	
	
	
	public void update() {
		time++;  // every second time is incremented by 60  (because update is 60 times per second)
		
		// if (time % 60 == 0) {			// event that happens once  per second
		if (time % (random.nextInt(50) + 30) == 0) {			// event that happens once between 30 to 80 seconds
			// xa *= -1;  // reverse direction	
			// xa = random.nextInt(3);   // returns integer between 0 and 2 <<<  (  3 elements  )    
			xa = random.nextInt(3) - 1; // Now we get values:   -1  0  1   (move left, not move, move right)
			ya = random.nextInt(3) - 1;
			if (random.nextInt(3) == 0 ) {    // has 1 in 3 chance of stopping for a second
				xa = 0; 
				ya = 0;
			}
		}
		
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
	
		if (xa != 0 || ya != 0) {
			move(xa, ya); 
			walking = true;
		} else {
			walking = false;
		}
	}

	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)x, (int)y, sprite, 0); // casting correct
	}
}
