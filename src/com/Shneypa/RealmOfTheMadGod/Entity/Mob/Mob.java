package com.Shneypa.RealmOfTheMadGod.Entity.Mob;

import java.util.ArrayList;
import java.util.List;

import com.Shneypa.RealmOfTheMadGod.Entity.Entity;
import com.Shneypa.RealmOfTheMadGod.Entity.Spawner.ParticleSpawner;
import com.Shneypa.RealmOfTheMadGod.Entity.projectile.Projectile;
import com.Shneypa.RealmOfTheMadGod.Entity.projectile.WizardProjectile;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;

public abstract class Mob extends Entity {

		// protected Sprite sprite; 
		// protected int dir = 0; 		 // 0 is usually north, 1 is east, 2 is south and 3 is west. (like a compass which is used clockwise)
		protected boolean moving = false; 
		protected boolean walking = false; 
		
		// protected List<Projectile> projectiles = new ArrayList<Projectile>();    // projectiles belong to Mob class, that way we can easily track who shot the projectile and who gets XP. (we check which instance of Mob this projectile belonged to  
		
		protected enum Direction {
			UP, DOWN, LEFT, RIGHT;
		}
		
		protected Direction dir;  
		
		public void move(double xa, double ya) {		// x movement, y movement	// Episode 96 (Faster Mob Movement)  changed these from int to doubles so we can  move at speeds 0.5 or 1.5 or 1.6  etc... 
		
			
			if (xa != 0 && ya != 0) {
				move(xa, 0);
				move(0, ya);
				return;
			} 
			// -1, 0, 1   movement modifiers. e.g.  we can move left, not move at all or move right.   
			
			// determining direction of movement (so we can change hero sprites and animate his movement
			if (xa > 0) dir = Direction.RIGHT;   	// east
			if (xa < 0) dir = Direction.LEFT;   	// west 
			if (ya > 0) dir = Direction.DOWN;	// south
			if (ya < 0) dir = Direction.UP;	// north
			
			
			// 1, 2, 3, 4, 5, etc - ALLOWS
			// 0.4, 0.6, 1.4, 5.5. - DOESNT'T ALLOW
			// BUT PIXELS ARE INTEGERS ! there can't be 0.4 pixels :)
			
			// however now we store Entities' coordinates in DOUBLES (more precise) and just before rendering to pixels we will cast X and Y to integers for rendering
			
			// EPISODE 97 Slow and Precise Entity Movement
			while (xa != 0) {
				if (Math.abs(xa) > 1) {			// if we subtract 1 from xa... will it still be greater than 0 ?   We only move the mob by a maximum of 1 pixel per update (so our collision works well) 
					if (!collision(abs(xa), ya)) {  				// we move only if there is no collision
						this.x += abs(xa);
					}
					xa-= abs(xa);
				} else {
						if (!collision(abs(xa), ya)) {  				
							this.x += xa;
						}				
						xa = 0;
						
				}
			}
			
			while (ya != 0) {
					if (Math.abs(ya) > 1) {			// if we substract 1 from xa... will it still be greater than 0 ?   We only move the mob by a maximum of 1 pixel per update (so our collision works well) 
						if (!collision(xa, abs(ya))) {  				// we move only if there is no collision
							this.y += abs(ya);
						}
						ya-= abs(ya);
					} else {
						if (!collision(xa, abs(ya))) {  				
								this.y += ya;
						}				
							ya = 0;
							
					  }
			}
		//	System.out.println("Projectiles in the array: " + level.getProjectiles().size() + "                   (Mob.Java)");
		}

		
		private int abs(double value) {
			if (value < 0) return -1;
			return 1;
		}
		
		public abstract void update();
		
		public abstract void render(Screen screen);
		
		protected void shoot(double x, double y, double dir) {	// doubles correct, but in EPISODE 103 its back to int
			//dir = dir * (360 / (2 * Math.PI));				// переводим из радианов в градусы (360 градусов = 2Пи Радиан).  Также можно просто написать      dir = Math.toDegrees(dir);
			Projectile p = new WizardProjectile(x, y, dir);
			//projectiles.add(p);									// *** Q: what is "add" ? 
			level.add(p);
		
			// System.out.println("Shooting Angle = " + dir + " (Mob.java)");
		}
		
		private boolean collision(double xa, double ya) {
			boolean solid = false;
			for (int c = 0; c < 4; c++) {   // 4 corner system. checks for the corner of tile. and if any corner belongs to a solid tile, it returns solid. 
				double xt = ((x + xa) - c % 2 * 15 ) / 16;
				double yt = ((y + ya) - c / 2 * 15 )/ 16; 
				int ix = (int) Math.ceil(xt);  // Math.ceil(double) =   rounding the number UP when we cast to an integer
				int iy = (int) Math.ceil(yt); 
				if (c % 2 == 0) ix = (int) Math.floor(xt); // c % 2 == 0  is when we are checking for the left side  
				if (c / 2 == 0) iy = (int) Math.floor(yt); 
				if (level.getTile(ix, iy).solid()) solid = true; 
			}
		
			
			// System.out.println(x+xa + ", " + y+ya + "   -   Mob.java");
			return solid;
		}
		
		
		
}
