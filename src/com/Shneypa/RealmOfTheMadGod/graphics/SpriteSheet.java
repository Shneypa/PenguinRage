package com.Shneypa.RealmOfTheMadGod.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path; // path to our spriteSheet
	public final int SIZE; // = 16. when we make something final, it is a convention to CAPITALIZE the varaible name. 
	public final int SPRITE_WIDTH, SPRITE_HEIGHT; // = 16.  width and height of each _sprite_ ???
	private int width, height; // width and height of the _spritesheet_
	public int[] pixels; 
	
	// public static SpriteSheet font = new SpriteSheet("/fonts/arial.png", 208, 96); // CHERNO has 16
	
	
	public static SpriteSheet mainspritesheet =  new SpriteSheet("/Textures/sheets/spritesheet.png", 256); //  START with the FORWAD SLASH !   or it won't read the path !
	public static SpriteSheet spawn_level = new SpriteSheet("/Textures/sheets/spawn_level.png", 256);
	public static SpriteSheet projectile_wizard = new SpriteSheet("/Textures/sheets/projectiles/wizardProjectiles.png", 48);
	
	// SUB-SPRITE-SHEETS CREATION: 
	public static SpriteSheet player = new SpriteSheet("/Textures/sheets/playerSpriteSheet.png", 128, 96);
		// We 'extract' sub-spritesheet of player moving down from the whole playerSpriteSheet 
		public static SpriteSheet player_left = new SpriteSheet(player,0,0,1,3,32);
		public static SpriteSheet player_up = new SpriteSheet(player,1,0,1,3,32);
		public static SpriteSheet player_right = new SpriteSheet(player,2,0,1,3,32);
		public static SpriteSheet player_down = new SpriteSheet(player,3,0,1,3,32);
	
	public static SpriteSheet bard = new SpriteSheet("/Textures/sheets/bardSpriteSheet.png", 96, 128);
		public static SpriteSheet bard_left = new SpriteSheet(bard,0,1,3,1,32);
		public static SpriteSheet bard_up = new SpriteSheet(bard,0,3,3,1,32);
		public static SpriteSheet bard_right = new SpriteSheet(bard,0,2,3,1,32);
		public static SpriteSheet bard_down = new SpriteSheet(bard,0,0,3,1,32);
		
		
	private Sprite[] sprites;	
		
		
	// CONSTRUCTOR for a SpriteSheet that takes a SpriteSheet and creates a Sub-SpriteSheet out of it 
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {

		// width and height are in Sprite precision
		// w and h are in pixel precision (because we multiply them by spriteSize) 
		
		int xx = x * spriteSize; // offset 
		int yy = y * spriteSize; // offset
		int w = width * spriteSize;  // absolute width
		int h = height * spriteSize; // absolute height
		
		if (width == height) SIZE = width;
		else SIZE = -1;
		
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		
		pixels = new int[w * h];
		
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
			
		// EPISODE 87: Animated Sprite Class  19:00
		
		int frame  = 0;
		sprites = new Sprite[width*height];
		for (int ya = 0; ya < height; ya++) { 			// this first XY loop
			for (int xa = 0; xa < width; xa++) {		// iterates through our physical sprites  ( width = 1,  height  = 3 ) 
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {				// the second XY loop 
					for (int x0 = 0; x0 < spriteSize; x0++) {			// deals with pixels of the sprite
						// System.err.println(spritePixels.length + ", " + pixels.length + "                - SpriteSheet.java" );
						spritePixels[x0 + y0 * spriteSize] = pixels [(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH ];
						
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize); 
				sprites[frame++] = sprite; // as soon as we add a sprite into sprites array, we increment frames  (frame++)   
			}
		}
		
	}
	
	// CONSTRUCTOR FOR A SPRITESHEET THAT is SQUARE
	public SpriteSheet(String path, int size)
	{
		this.path = path;
		SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		
		pixels = new int[SPRITE_WIDTH*SPRITE_HEIGHT];
		load();		
		System.out.println("SpriteSheet from " + path + " created!  (SQUARE METHOD)        -  SpriteSheet.java");
	}
	
	// CONSTRUCTOR FOR A SPRITESHEET THAT is NOT SQUARE
	public SpriteSheet(String path, int width, int height)
	{
		this.path = path;
		SIZE = -1; // EPISODE 86 15:30 : ".. lets set size to -1 so we can see if something's wrong here" 
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height; 
		
		pixels = new int[SPRITE_WIDTH*SPRITE_HEIGHT];
		load();		
		System.out.println("SpriteSheet from " + path + " created! (NOT SQUARE METHOD)        -  SpriteSheet.java");
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
	public int getWidth() {
		return width;
	}
		
	public int getHeight() {
		return height;
	}
	
	public int[] getPixels() {
										
		return pixels;
	}
	
	
	private void load()  // Translates pixels which we've loaded from the spriteSheet into our pixels array
	{
		try {
			System.out.print("Trying to load: " + path + "...");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			System.out.println(" success!");
			
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			System.err.println(" failed!");
		}
	}

	
	
}
