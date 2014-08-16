package com.Shneypa.RealmOfTheMadGod.Level.tile.spawn_level;

import com.Shneypa.RealmOfTheMadGod.Level.tile.Tile;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;

public class SpawnWallTile extends Tile{

	public SpawnWallTile(Sprite sprite) {
		super(sprite);
		
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);  			
	}

	public boolean solid() {
		return true;
	
	}
	
}
