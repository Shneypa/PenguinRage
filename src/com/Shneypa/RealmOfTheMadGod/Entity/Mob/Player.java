package com.Shneypa.RealmOfTheMadGod.Entity.Mob;

import java.util.List;

import com.Shneypa.RealmOfTheMadGod.Game;
import com.Shneypa.RealmOfTheMadGod.Entity.Entity;
import com.Shneypa.RealmOfTheMadGod.Entity.projectile.Projectile;
import com.Shneypa.RealmOfTheMadGod.Entity.projectile.WizardProjectile;
import com.Shneypa.RealmOfTheMadGod.graphics.AnimatedSprite;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;
import com.Shneypa.RealmOfTheMadGod.graphics.SpriteSheet;
import com.Shneypa.RealmOfTheMadGod.input.Keyboard;
import com.Shneypa.RealmOfTheMadGod.input.Mouse;

public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 1;
	private boolean walking = false; 
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	
	private AnimatedSprite animSprite = down;
	
	private int fireRate = 0;
	
	
	public Player(Keyboard input) {
		this.input = input; 
		sprite = Sprite.player_forward;
		animSprite = down;
	}
	
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input; 
		sprite = Sprite.player_forward;
		fireRate = WizardProjectile.FIRE_RATE;
	}
	
	public void update() {
		// Check of how many entities are around the player witing 80 pixel radius
		// 	List<Entity> es = level.getEntities(this, 80);
		 // System.out.println(es.size() + "        - Player.java");
		// for (Entity e : es) {
			// System.out.println(e + "        - Player.java");
		// } 
		// ^^ This is just for monitoring purposes and can be cut out ^^ 
		
		// Update() method body:
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (fireRate > 0) fireRate--;
		double xa = 0, ya = 0; 
		if (anim < 7500) anim++;			// anim is increasing at a rate of +60 per second
		else anim = 0;						// so anim doen't exceed int range if someone leaves the game running for too long
		
		double speed = 2;
		if (input.up) {
			ya-= speed;
			animSprite = up;
		} else if (input.down) {			// else is here so we can't select both up and down at one time
			ya+= speed;
			animSprite = down;
		}
		if (input.left) {
			xa-= speed;
			animSprite = left;
		}
		else if (input.right) {		
			xa+= speed;
			animSprite = right;
		}
	
		if (xa != 0 || ya != 0) {
			move(xa, ya); 
			walking = true;
		} else {
			walking = false;
		}
		clear();
		updateShooting();
	
	}
	
	private void clear() {										// removes projectiles from Projectile List once they exceed specified range
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if(p.isRemoved()) level.getProjectiles().remove(i);
		}
		
	}

	private void updateShooting() {	
		if (Mouse.getButton() == 1 && fireRate <= 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2; 
			double dy = Mouse.getY() - Game.getWindowHeight() / 2 ; 
			double dir = Math.atan2(dy, dx); // вычисляет арктангенс (значение угла между позицией игрока и курсором мышки)  (direction of shooting) 
			shoot(x, y, dir); 
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}
	
	
	public void render(Screen screen) {
		int flip = 0; 
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16), (int)(y - 16), sprite, flip);	// casting correct
		
	}
}
		

		// OLD RENDER METHOD  (EACH DIRECTION OF MOVEMENT HAS ITS OWN SPRITE) 
		/*
		  public void render(Screen screen) {
		int flip = 0; 
		/*if (dir == 0) {
			sprite = Sprite.player_forward;
			if (walking) {
				if (anim % 20 > 10) { 					// if remainder of anim/20 is more than 10 (basically, half the time), then it uses the second sprite. (anim is increasing at a rate of +60 per second)    
					sprite = Sprite.player_forward_1;		
				} else sprite = Sprite.player_forward_2;
			}
				
		}
		if (dir == 1) {
			sprite = Sprite.player_side;
			if (walking) {
				if (anim % 20 > 10) { 					
					sprite = Sprite.player_side_1;
				} else sprite = Sprite.player_side_2;
			}
		}
		if (dir == 2) {
			flip = 0; 
			sprite = Sprite.player_back;
			if (walking) {
				if (anim % 20 > 10) { 					
					sprite = Sprite.player_back_1;
				} else sprite = Sprite.player_back_2;
			}
		}
		if (dir == 3) {
			sprite = Sprite.player_side;
			if (walking) {
				if (anim % 20 > 10) { 					
					sprite = Sprite.player_side_1;
				} else sprite = Sprite.player_side_2;
			}
			flip = 0;
		}
		
		sprite = animSprite.getSprite();
		
		
		// if (dir == 3) sprite = Sprite.player_left;
		screen.renderMob((int)(x - 16), (int)(y - 16), sprite, flip);
	
		
		 */
		
		
		
		
		
		/*		  4 parts of king cherno rendered separately: 
		int xx = x - 16;
		int yy = y - 16;
		screen.renderPlayer(xx, yy, Sprite.player0);
		screen.renderPlayer(xx + 16, yy, Sprite.player1);
		screen.renderPlayer(xx, yy + 16, Sprite.player2);
		screen.renderPlayer(xx + 16, yy + 16, Sprite.player3);
		*/
	







/*  BASIC MOVEMENT: 

if (input.up) y--;
if (input.down) y++;
if (input.left) x--;
if (input.right) x++;
*/