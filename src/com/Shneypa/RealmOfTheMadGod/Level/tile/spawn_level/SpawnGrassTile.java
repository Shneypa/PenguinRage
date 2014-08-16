package com.Shneypa.RealmOfTheMadGod.Level.tile.spawn_level;

import com.Shneypa.RealmOfTheMadGod.Level.tile.Tile;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;

public class SpawnGrassTile extends Tile{

	public SpawnGrassTile(Sprite sprite) {
		super(sprite);
		
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);  			// converting back to Tile precision by multiplying by 16. we also need to do the same for the VoidTile
	}
	
}
