package com.Shneypa.RealmOfTheMadGod.Level.tile;

import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;

public class RockTile extends Tile {
	
	public RockTile(Sprite sprite) {
		super(sprite);
		
	}

	public void render(int x, int y, Screen screen) {		// SHIT-LEFT =  MULTIPLICATION
		screen.renderTile(x << 4, y << 4, this);  			// converting back to pixel precision by multiplying by 16. we also need to do the same for the VoidTile
	}
	
	public boolean soild()   // Solid property of a tile (hero and mobs can't walk through rocks)
	{
		return true;
	}
}
