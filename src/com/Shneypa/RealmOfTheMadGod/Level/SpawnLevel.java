package com.Shneypa.RealmOfTheMadGod.Level;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Chaser;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Dummy;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Shooter;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Star;
import com.Shneypa.RealmOfTheMadGod.input.Keyboard;

public class SpawnLevel extends Level {
	
	 
	private static final String path = null;   // why is it still here? 
	private int[] levelPixels;
	// private int[] tiles;
	
	public SpawnLevel(String path) {
		super(path);				// throws us back into Level(path) constructor. 
	}
	
	
	
	protected void loadLevel(String path) {     // *** Q15. I Don't get this
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));    // load an image  from a particular path
			int w = width = image.getWidth();													// calculate image width
			int h = height = image.getHeight();													// calculate image height
																				//old line: tiles = new Tile[w * h];
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);								// converts image into an array of pixels called 'tiles'. 
																				// We're using array that was declared in the superclass ( Level ).
		
		} catch(IOException e) {												// Q16. Catching Exceptions. 
			e.printStackTrace();												// Q17. printStackTrace()
			System.out.println("Exception! Could not load level file!      -   Spawnlevel.java");
		}
		for (int i = 0; i < 1; i++) {											// we add an i number of mobs 
			add(new Chaser(22,64));												// Q18. What does 'add' actually mean? 
			add(new Star(19,35));
			add(new Shooter(18,65));
			add(new Shooter(18,67));
			
			add(new Dummy(18,64));
		
		}
		
	
	}
	
	// Codes for tiles: 
	// Grass 	=   green 		 =    0xff00FF00			ADD ff up front for the alpha channel
	// Flower	=   yellow 		 =    0xffFFFF00
	// Rock 	=   dark yellow	 =    0xff7F7F00
	protected void generateLevel() {		// method that CONVERTS pixels into tiles
											// fills up Tile[] array with tile objects based on the colors of  levelPixels array
		
		
		/* for (int i = 0; i < levelPixels.length; i++) {
			if (levelPixels[i] == 0xff00ff00) tiles [i] = Tile.grass; 
			if (levelPixels[i] == 0xffffff00) tiles [i] = Tile.flower; 
			if (levelPixels[i] == 0xff7f7f00) tiles [i] = Tile.rock; 
		
		}*/
	}
}
