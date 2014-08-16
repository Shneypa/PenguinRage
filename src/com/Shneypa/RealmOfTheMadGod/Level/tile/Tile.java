package com.Shneypa.RealmOfTheMadGod.Level.tile;

import com.Shneypa.RealmOfTheMadGod.Level.tile.spawn_level.SpawnFloorTile;
import com.Shneypa.RealmOfTheMadGod.Level.tile.spawn_level.SpawnGrassTile;
import com.Shneypa.RealmOfTheMadGod.Level.tile.spawn_level.SpawnHedgeTile;
import com.Shneypa.RealmOfTheMadGod.Level.tile.spawn_level.SpawnWallTile;
import com.Shneypa.RealmOfTheMadGod.Level.tile.spawn_level.SpawnWaterTile;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;


// EVERY TILE RENDERS ITSELF.
// Level class holds a list of tiles which we want rendered


public class Tile {
	
	public int x, y;
	public Sprite sprite;
	
	
	public static Tile grass = new GrassTile(Sprite.grass); 
	public static Tile flower = new FlowerTile(Sprite.flower); 
	public static Tile rock = new RockTile(Sprite.rock); 
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public static Tile spawn_grass = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1 = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2 = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_floor = new SpawnFloorTile(Sprite.spawn_floor);
	
	public static final int col_spawn_grass = 0xff00FF00; 
	public static final int col_spawn_hedge = 0; // not currently used in spawn level
	public static final int col_spawn_water = 0; // not currently used in spawn level
	public static final int col_spawn_wall1 = 0xff808080;
	public static final int col_spawn_wall2 = 0xff303030;
	public static final int col_spawn_floor = 0xff684700;
	
	
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
					}
	
	public void render(int x, int y, Screen screen)   // CTRL + CLICK on "Screen"  to go to the source
	{
		
	}
	
	public boolean solid()   // Solid property of a tile (hero and mobs can't walk through it)
	{
		return false;
	}
 	
}
