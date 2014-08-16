package com.Shneypa.RealmOfTheMadGod.Level.tile;

import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;


// the color of the VoidTile is set in Sprite class ! 

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
		
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);			// converting back to Tile precision by multiplying by 16.
	}
}
