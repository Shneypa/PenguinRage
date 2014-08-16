// EPISODE 36 is replacement of render() method in class ______   which created some out of borders map problem

// SCREEN IS THE RENDERING CLASS

package com.Shneypa.RealmOfTheMadGod.graphics;

import java.awt.Color;
import java.util.Random;

import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Chaser;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Mob;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Player;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Star;
import com.Shneypa.RealmOfTheMadGod.Level.tile.Tile;

public class Screen 
{
	public static int width; // create 2 variables in one line of code
	public static int height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE]; // 4096 tiles on a map
	public int xOffset, yOffset;						// section. Tiles are
	private Random random = new Random();				// 32x32 pixels

	

	public Screen(int width, int height) {
		this.width = width;								//  screen's width is now equal to the passed parameter (300) 
		this.height = height;							//  screen's height is now equal to the passed parameter (168.75) ?   Rounded to 168?  Q: To 162 for some reason??? 
		pixels = new int[width * height];				// we create an array of integers (colors will be coded in integers). Array is 300 * 162 or 48 600 elements long. 
	}

	// This is RANDOM TILE generator.  
	// (It was inside the ^^ Screen(int width, int height) ^^  constructor). 
	//
	// for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {	
	// 			tiles[i] = random.nextInt(0xffffff);
	// 		}
	
	
	// Method that CLEARS all the elements of the pixel array. 
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	
	
	
	// EPISODE 75. Advanced sprite creation. we cycle through a sprite's pixels and draw them "on top of" our pixels array
	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {  // gonna render a sprite	
		if (fixed) {
			xp -= xOffset;	// fixed means fixed to the level (to some coordinates in the level), not fixed to the screen
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;			// CRASHED after some performance when XA was EQUAL to WIDTH
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != 0xffff00ff && col != 0xff7f007f) pixels[xa + ya * width] = col;
			}
		}
	}
	
	// FOR DEBUGGING PURPOSES
	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {  // gonna render a sprite	
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;			// CRASHED after some performance when XA was EQUAL to WIDTH
				pixels[xa + ya * width] = sheet.pixels[x + y * sheet.SPRITE_WIDTH];
			}
		}
	}
	
	public void renderTile(int xp, int yp, Sprite sprite) {
		xp -= xOffset; // Moving the player  Episode 34
		yp -= yOffset; // Moving the player  Episode 34
		
		for (int y = 0; y < sprite.SIZE; y++)	{
			int ya = y + yp; 									// ya  is Y coordinate Absoulte position . yp will change by offset
			for (int x = 0; x < sprite.SIZE; x++)	{
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; 			// MAKES sure we only render what we can see on the screen. This allows us to have a huge map and not render it all, but only the visible portion. 
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sprite.SIZE];
				if (col != 0xffFF00B6) pixels[xa + ya * width] = sprite.pixels[x + y * sprite.SIZE];  // RENDERING OCCURES HERE !  Added Transparency check 
			}
		}
	}
	
	public void renderTile(int xp, int yp, Tile tile) {			// at some point Cherno replaced Tile tile to Sprite sprite in renderTile method ^^. This mini-method takes care of all snowballing errors, so we dont have to search for stuff like tile.sprite.SIZE and replace it with sprite.SIZE throughout our code. 
		renderTile(xp, yp, tile.sprite);						// in Episode 70 Cherno created a separate render method renderProjectile
	}
	
	public void renderMob(int xp, int yp, Mob mob) {
		xp -= xOffset; 
		yp -= yOffset; 
		
		for (int y = 0; y < 32; y++)	{
			int ya = y + yp;
			int ys = y;
			
			for (int x = 0; x < 32; x++)	{
				int xa = x + xp;
				int xs = x;
		
				if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; 	
				if (xa < 0) xa = 0;
				int col = mob.getSprite().pixels[xs + ys * 32];      // variable that stores color
				if ((mob instanceof Chaser) && col == 0xff30471F) col = 0xff6E591C; // if a mob we are rendering is a Chaser, we change his  green colors for yellow-gold color (to differentiate Chasers and Dummies) 
				if ((mob instanceof Chaser) && col == 0xff57702E) col = 0xffB39233;
				if ((mob instanceof Chaser) && col == 0xff789633) col = 0xffD8BE33;
				
				if ((mob instanceof Star) && col == 0xff30471F) col = 0xff6E591C; // if a mob we are rendering is a Chaser, we change his  green colors for yellow-gold color (to differentiate Chasers and Dummies) 
				if ((mob instanceof Star) && col == 0xff57702E) col = 0xffB39233;
				if ((mob instanceof Star) && col == 0xff789633) col = 0xffD8BE33;
				
				if (col != 0xffFF00B6) pixels[xa + ya * width] = col;			// pink is transparent for now. it renders if color is not FF00B6 (pink). but RGB also has alpha channel which goes into BufferedImage so we need to add "ff" and the color index becomes 0xffFFFFFF
			}
		}
	}
	
	public void renderMob(int xp, int yp, Sprite sprite, int flip) {
		// flip = 0     =    no flip
		// flip = 1		=    flip x (sideways)		
		// flip = 2		=    flip y (upside down)
		// flip = 3		=    flip both x and y
		
		xp -= xOffset; 
		yp -= yOffset; 
		
		for (int y = 0; y < 32; y++)	{
			int ya = y + yp;
			int ys = y;
			
			if(flip == 2 || flip == 3) {
				ys = 31 - y;
			}					 // variable to flip the sprite, but we're not flipping upside down
			
			for (int x = 0; x < 32; x++)	{
				int xa = x + xp;
				int xs = x;
			
				if(flip == 1 || flip == 3) {
					xs = 31 - x; 		// variable to flip the sprite for sideway motion (left-right)   note that it is 31, not 32, because values are  from  0 to 31 (32 values for pixels in our sprite)
				}
				if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; 	
				if (xa < 0) xa = 0;
				int col = sprite.pixels[xs + ys * 32];      // variable that stores color
				if (col != 0xffFF00B6) pixels[xa + ya * width] = col;			// pink is transparent for now. it renders if color is not FF00B6 (pink). but RGB also has alpha channel which goes into BufferedImage so we need to add "ff" and the color index becomes 0xffFFFFFF
			}
		}
	}
	
	
	// NEW setOffset method  (WHY ISNT IT IN THE LEVEL.render method ? 
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset; 
	}

	// drawRect for Debug class
	public void drawRect(int xp, int yp, int width, int height, int colour, boolean fixed) {
		if (fixed) {
			xp -= xOffset;	// fixed means fixed to the level (to some coordinates in the level), not fixed to the screen
			yp -= yOffset;
		}
		for (int x = xp; x < xp + width; x++) {
			if (x < 0 || x >= this.width || yp >= this.height) continue;  // clipping
			if (yp > 0)	pixels[x + yp * this.width] = colour;
			if (yp + height >= this.height)continue; 
			if (yp + height > 0) pixels[x + (yp + height) * this.width] = colour;
		}
		// the FOR-loops aren't nested because we want just a rectangle from lines, not a filled rectangle 
		
		for (int y = yp; y <= yp + height; y++) {
			if (xp >= this.width || y < 0 || y >= this.height) continue;  // clipping
			if (xp > 0) pixels[xp + y  * this.width] = colour;
			if (xp + width >= this.width) continue; 
			if (xp + width > 0) pixels[(xp + width) + y  * this.width] = colour;
			
		}
		
		
	}

	
	
}



//OLD RENDER METHOD
//	public void render(int xOffset, int yOffset) {
//		for (int y =0; y < height; y++) {
//			int yp = y + yOffset;
//			if (yp < 0 || yp >= height) continue;
//			for (int x = 0; x < width; x++) {
//				int xp = x + xOffset;
//				if (xp < 0 || xp >= width) continue;
//				pixels[xp + yp * width] = Sprite.grass.pixels[(x & 15) + (y & 15) * Sprite.grass.SIZE];
//			}
//		}
//		
//	}




// COUNTER
// counter++;
// if (counter % 100 == 0) xtime--; // syntax?
// if (counter % 100 == 0) ytime--;



// OLD RENDER METHOD BASED ON TILES AND OFFSETS
// public void render(int xOffset, int yOffset)
// {
//	for(int y = 0; y < height; y++)
//		{
//			int yy = y + yOffset;						// *** QUESTION: How does Offset work? 
//		//	if (yy <0 || yy >= height) break;
			
//		//	for (int x = 0; x < width; x++)
//				{
//					int xx = x + xOffset;
							 
//				 //	if (xx <0 || xx >= width) break;
//					int tileIndex = ((xx >> 4) & MAP_SIZE_MASK) + ((yy >> 4) & MAP_SIZE_MASK)* MAP_SIZE; // finds a tile which starts at target pixel.     |    x >> 4  is binary. means "x shifted right by 4". means x is divided by 2^4 (two in the power of four which is equal to 16).  Called "Bitwise operator" 
				
					
//				}
//		}


//}
