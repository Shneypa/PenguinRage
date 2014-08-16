package com.Shneypa.RealmOfTheMadGod.Entity.Mob;

import java.util.List;

import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Mob.Direction;
import com.Shneypa.RealmOfTheMadGod.Level.Node;
import com.Shneypa.RealmOfTheMadGod.graphics.AnimatedSprite;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;
import com.Shneypa.RealmOfTheMadGod.graphics.SpriteSheet;
import com.Shneypa.RealmOfTheMadGod.util.Vector2i;

public class Star extends Mob {
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.bard_left, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.bard_up, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.bard_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.bard_down, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private double xa = 0;
	private double ya = 0;
	private List<Node> path = null;
	private int time = 0;
	
	public Star(int x, int y) {
		this.x = x << 4; // into tile precision
		this.y = y << 4; 
		sprite = Sprite.bard;
	}

	// EPISODE 101  // Why did I have to cast to integers? 
	private void move() {
		xa = 0;
		ya = 0;
		int px = (int) level.getPlayerAt(0).getX(); // player's coordinate is in pixel precision. But our algorithm is in tile precision
		int py = (int) level.getPlayerAt(0).getY();
		Vector2i start = new Vector2i((int) getX() >> 4, (int) getY() >> 4); // divide by 16 to get to tile precision
		Vector2i destination = new Vector2i(px >> 4, py >> 4); // divide by 16 to get to tile precision
		if (time % 1 == 0) path = level.findPath(start, destination); // once per second we'll update this
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile; // start from the last node
			if (x < vec.getX() << 4) xa++ ; // if our current location is to the right then increase x++
			if (x > vec.getX() << 4) xa-- ; 
			if (y < vec.getY() << 4) ya++ ; 
			if (y > vec.getY() << 4) ya-- ; 
			}
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
		screen.renderMob((int)(x - 16), (int)(y - 16), this);
		
	}
}
