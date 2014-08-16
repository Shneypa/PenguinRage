package com.Shneypa.RealmOfTheMadGod.graphics;

public class Sprite {

		public final int SIZE;  // final is a CONSTANT
		private int x, y;
		private int width, height;
		public int[] pixels; 
		protected SpriteSheet sheet;
		
		public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.mainspritesheet); // we need to ADD info about Spritesheet instead of null. This line CREATES a sprite
		
		
		public static Sprite flower = new Sprite(16, 1, 0, SpriteSheet.mainspritesheet); 
		public static Sprite rock = new Sprite(16, 2, 0, SpriteSheet.mainspritesheet); 
		public static Sprite voidSprite = new Sprite(16, 0xB9ED85); // EPISODE 33. creates a black tile (or rather a black sprite for it). or rather a light green tile. Color chosen from http://www.colorpicker.com/     with ID  of B9ED85   so it has to be typed as 0xB9ED85  in the code 
		
		
		// PROJECTILE SPRITES HERE:
		public static Sprite shootingRock = new Sprite(16, 6, 0, SpriteSheet.spawn_level); // just for testing purposes I wanted to shoot some rocks
		public static Sprite turd = new Sprite(16, 0, 0, SpriteSheet.projectile_wizard);
		public static Sprite eightBall = new Sprite(16, 1, 0, SpriteSheet.projectile_wizard);
		public static Sprite dot = new Sprite(16, 2, 0, SpriteSheet.projectile_wizard);
		public static Sprite bomb2 = new Sprite(16, 0, 1, SpriteSheet.projectile_wizard);
		public static Sprite bomb1 = new Sprite(16, 1, 1, SpriteSheet.projectile_wizard);
		public static Sprite bombBoom = new Sprite(16, 2, 1, SpriteSheet.projectile_wizard);
		public static Sprite yellowDot = new Sprite(16, 0, 2, SpriteSheet.projectile_wizard);
		
		// SPAWN LEVEL SPRITES HERE:
		public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level); 
		public static Sprite spawn_hedge = new Sprite(16, 1, 1, SpriteSheet.spawn_level); 
		public static Sprite spawn_water = new Sprite(16, 0, 4, SpriteSheet.spawn_level); 
		public static Sprite spawn_wall1 = new Sprite(16, 3, 2, SpriteSheet.spawn_level); 
		public static Sprite spawn_wall2 = new Sprite(16, 2, 2, SpriteSheet.spawn_level); 
		public static Sprite spawn_floor = new Sprite(16, 0, 3, SpriteSheet.spawn_level); 
		
		// PLAYER SPRITES HERE:
		public static Sprite player_forward = new Sprite(32, 1, 5, SpriteSheet.mainspritesheet);
		public static Sprite player_back = new Sprite(32, 3, 5, SpriteSheet.mainspritesheet);
		// public static Sprite player_left = new Sprite(32, 3, 5, SpriteSheet.tiles);
		public static Sprite player_side = new Sprite(32, 2, 5, SpriteSheet.mainspritesheet); // subject to flipping 
			
		public static Sprite player_forward_1 = new Sprite(32, 1 , 6, SpriteSheet.mainspritesheet);
		public static Sprite player_forward_2 = new Sprite(32, 1 , 7, SpriteSheet.mainspritesheet);
		
		public static Sprite player_side_1 = new Sprite(32, 2 , 6, SpriteSheet.mainspritesheet);
		public static Sprite player_side_2 = new Sprite(32, 2 , 7, SpriteSheet.mainspritesheet);
		
		public static Sprite player_back_1 = new Sprite(32, 3 , 6, SpriteSheet.mainspritesheet);
		public static Sprite player_back_2 = new Sprite(32, 3 , 7, SpriteSheet.mainspritesheet);
		
		// BARD'S SPRITES HERE: FIRST ATTEMPT:
		/* public static Sprite bard_forward = new Sprite(32, 5, 7, SpriteSheet.mainspritesheet);
		public static Sprite bard_forward_1 = new Sprite(32, 4, 7, SpriteSheet.mainspritesheet);
		public static Sprite bard_forward_2 = new Sprite(32, 6, 7, SpriteSheet.mainspritesheet);
 
		public static Sprite bard_back = new Sprite(32, 5, 4, SpriteSheet.mainspritesheet);
		public static Sprite bard_back_1 = new Sprite(32, 4, 4, SpriteSheet.mainspritesheet);
		public static Sprite bard_back_2 = new Sprite(32, 6, 4, SpriteSheet.mainspritesheet);
		
		public static Sprite bard_left = new Sprite(32, 5, 5, SpriteSheet.mainspritesheet);
		public static Sprite bard_left_1 = new Sprite(32, 4, 5, SpriteSheet.mainspritesheet);
		public static Sprite bard_left_2 = new Sprite(32, 6, 5, SpriteSheet.mainspritesheet);
		
		public static Sprite bard_right = new Sprite(32, 5, 6, SpriteSheet.mainspritesheet);
		public static Sprite bard_right_1 = new Sprite(32, 4, 6, SpriteSheet.mainspritesheet);
		public static Sprite bard_right_2 = new Sprite(32, 6, 6, SpriteSheet.mainspritesheet);
		*/
		
		// Default bardsprite
		public static Sprite bard = new Sprite(32, 0, 0, SpriteSheet.bard_down);
		
		// Particles
		public static Sprite particle_normal = new Sprite(3, 0xC0C0C0);
		
		
		
		// SUPERCLASS CONSTRUCTOR FOR AnimatedSprite
		protected Sprite(SpriteSheet sheet, int width, int height) {
			
			SIZE = (width == height) ? width : -1; 
			
			// ^^ line above means:     
			
			// if (width == height) SIZE = width;
			// else SIZE = -1;
					
			this.width = width;
			this.height = height;
			this.sheet = sheet;
		}
		
		// CREATE A SPRITE that is SQUARE
		public Sprite(int size, int x, int y, SpriteSheet sheet) 
			{
				SIZE = size;
				this.width = size;
				this.height = size;
				pixels = new int [SIZE * SIZE];
				this.x = x * size; // setting the coordinates of our sprite in the spritesheet 
				this.y = y * size; 
				this.sheet = sheet;
				load();
			}
		
		// CREATE A SPRITE that is NOT SQUARE
		public Sprite(int width, int height, int colour) {
			SIZE = -1; 
			this.width = width;
			this.height = height; 
			pixels = new int[width * height];
			setColour(colour);
		}
		
		
		
		// EPISODE 33
		public Sprite(int size, int colour) {
			SIZE = size;
			this.width = size;
			this.height = size;
			pixels = new int [SIZE*SIZE];
			setColour(colour);
		}
		
		// EPISODE 108. Extracting Sprites SPRITE SPLIT
		public static Sprite[] split(SpriteSheet sheet) {
			System.out.println("Sprite[] split method activated");
			System.out.println(sheet.getWidth());
			System.out.println(sheet.getHeight());
			System.out.println(sheet.SPRITE_WIDTH);
			System.out.println(sheet.SPRITE_HEIGHT);
			
			int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT); // amount of sprites we're creating
			System.out.println("amount = " + amount + " ---- Sprite[] split  (Sprite.java) ");
			Sprite[] sprites = new Sprite[amount]; // Array of 78 Sprites
			int current = 0;
			
			
			int[] pixels = new int [sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];	// pixels array for the whole spritesheet or for each sprite? 
			
			for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT  ; yp++) {  // amount of sprites there are vertically
				// from 0 to 5
				for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {  // amount of sprites there are horizontally
					// from 0 to 12
					
					for (int y = 0; y < sheet.SPRITE_HEIGHT  ; y++) {
						for (int x = 0; x < sheet.SPRITE_WIDTH  ; x++) {
							int xo = x + xp * sheet.SPRITE_WIDTH;
							int yo = y + yp * sheet.SPRITE_HEIGHT;
							pixels[x + y * sheet.SPRITE_WIDTH ] = sheet.getPixels()[xo + yo * sheet.getWidth()]; 				// CRASH ERROR HERE !! 
							
							//System.out.println("sheet.getPixels()[xo + yo * sheet.getWidth()] = " + sheet.getPixels()[xo + yo * sheet.getWidth()]);
							
							//System.out.println("pixels[x + y * sheet.SPRITE_WIDTH ] = " + pixels[x + y * sheet.SPRITE_WIDTH ]);
							//System.out.println("pixels[1] = " +   pixels[1]);
							
							// System.out.println("sheet.getWidth() = " +  sheet.getWidth());
							//System.out.println("xOffset = " + xo);
							//System.out.println("yOffset = " + yo);
							//System.out.println("x = " + x);
							//System.out.println("y = " + y);
						}
					}
					
					sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH , sheet.SPRITE_HEIGHT);  // PROBLEM HERE. SPRITE ISN'T CREATED PROPERLY
					//System.out.println(pixels);
					//System.out.println("sprites[current] = " + sprites[current]);
						//current++; 
					//System.out.println("current = " + current);
				}
				//System.out.println("yp = " + yp);
				//System.out.println("sheet.getHeight() = " + sheet.getHeight());
				//System.out.println("sheet.SPRITE_HEIGHT = "  + sheet.SPRITE_HEIGHT);
				//System.out.println("sheet.getWidth() = " + sheet.getWidth());
				//System.out.println("sheet.SPRITE_WIDTH = "  + sheet.SPRITE_WIDTH);
		
			}
			return sprites;
		}
		
		public Sprite(int[] pixels, int width, int height) {
			SIZE = (width == height) ? width : -1; 
			this.width = width;
			this.height = height;
			this.pixels = new int[pixels.length];
			
			//this.pixels = pixels;
			
			for (int i = 0; i < pixels.length; i++) {			// copying each element of the array   also possible to write as: System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
				this.pixels[i] = pixels[i];
			}
			
		}

		// EPISODE 33
		private void setColour(int colour) {
			for (int i = 0; i < width * height; i++) {
				pixels[i] = colour;
			}
			
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}
		private void load()   // Extracting a single sprite out of our spritesheet
			{
				for (int y = 0; y < height; y++)
				{
					for (int x = 0; x < width; x++)
						pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.getWidth()]; // finds coordinates of target sprite in our spritesheet // ERROR? 
				}
			}
		
}


// *** Q:  how come Sprite has 3 constructors??? 


/* 4-PART King Cherno
public static Sprite player0 = new Sprite (16, 0, 10, SpriteSheet.tiles);
public static Sprite player1 = new Sprite (16, 1, 10, SpriteSheet.tiles);
public static Sprite player2 = new Sprite (16, 0, 11, SpriteSheet.tiles);
public static Sprite player3 = new Sprite (16, 1, 11, SpriteSheet.tiles);
*/
