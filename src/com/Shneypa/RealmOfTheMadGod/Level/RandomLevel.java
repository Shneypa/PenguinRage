package com.Shneypa.RealmOfTheMadGod.Level;

import java.util.Random;

public class RandomLevel extends Level {

	private static final Random random = new Random();
	
	public RandomLevel(int width, int height) {
		super(width, height);
	
	}

	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tilesInt[x + y * width] = random.nextInt(4);   // will generate up to 4 values (from 0 to 3) 
			}
		}
	
	}
	
}
