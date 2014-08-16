/*
 
 A LOT OF MOB SPRITES :   http://www.rpgmakervxace.net/topic/1940-kazzadors-sprites/
 
 Sprite.java - Assigns Player sprites and Tile sprites from spritesheet files.
 Transparency is handled by Screen.java
 * 
 */


/* EPISODES 85-ish that are new sprites  I SKIPPED
 *  Now in EPISODE 91 we should be building on the Animated sprites, but I'm trying to skip it for now
 * 
 * 
 * 
 * IN CASE OF BUG USE SYSO   -  SYSTEM.PRINTLN()    -  to monitor all the values in the method and 'above' methods.   
 */

/*
 * SOMEWHERE around Episode 90ish (precision Mob movement) Cherno switched doubles, now I had to cast to (int) in Star and Shooter 
 */
 
	// SHIT-LEFT =  MULTIPLICATION

package com.Shneypa.RealmOfTheMadGod;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;   // CTRL+ SHIFT + O   to import a package    to resolve an error saying e.g. "DataBufferInt cannot be resolved to a type" 
import java.util.Random;

import javax.swing.JFrame;

import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Dummy;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Player;
import com.Shneypa.RealmOfTheMadGod.Level.Level;
import com.Shneypa.RealmOfTheMadGod.Level.RandomLevel;
import com.Shneypa.RealmOfTheMadGod.Level.SpawnLevel;
import com.Shneypa.RealmOfTheMadGod.Level.TileCoordinate;
import com.Shneypa.RealmOfTheMadGod.graphics.Font;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;
import com.Shneypa.RealmOfTheMadGod.graphics.SpriteSheet;
import com.Shneypa.RealmOfTheMadGod.input.Keyboard;
import com.Shneypa.RealmOfTheMadGod.input.Mouse;

														// Q1. what is Canvas?
														// Q2. What is Runnable? 
public class Game extends Canvas implements Runnable			 //  Game class is now kinda subclass of Canvas, inherits its superpowers
{				
	private static final long serialVersionUID = 1L;	// Q3. what is serialVersionUID ? 
		
		private static int width = 300;					// Q4. Width of what exactly? Of BufferedImage. 
		private static int height = width / 16 * 9;   								 // 16:9 screen ratio
		private static int scale = 3; 								 //  game renders for 300 pixels but shows on screen in a 900 window
		public static String title = "Realm of The Mad God";
		
		private Thread thread; 							// Q5. Threads.		 // thread is a sub-process so we can do multiple things simultaneously
		private JFrame frame;							// Q6. JFrame. Java-окно. 
		private Keyboard key;							// Q7. Keyboard. 
		
		private Level level; 							// declares level as an instance of Level class.  
		private Player player;							// declares player  as an instance of Player class. 
		private Screen screen; 							// creates instance of Screen (or only declares it? ) Q8. 
		private Font font;								// creates instance of Font (or only declares it? ) 
	
		private boolean running = false;   				// indicator that or game is running. Q9. Do we use this indicator? 
		
		private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // Q10. BufferedImage. //  created our main rendered image frame. next line is for accessing the image. 
		private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();		// a Raster is like a rectangular array of pixels. we need to retrieve a raster of image^^. Basically we are converting a raster of image into an integer array of pixels.  REWATCH EPISODE 7 14:00	
		
		public Game()    // this a constructor - only runs ONCE when object "Game" is created
		{
			Dimension size = new Dimension(width * scale, height * scale);		// Q11. Dimension. 
			setPreferredSize(size);												// Q12. setPreferredSize. 
			
			screen = new Screen(width, height);									// initialization (we give initial value to the screen ( 48 600 pixels).  notice width and height are _not scaled_ at this point) 
			frame = new JFrame();												// initialization
			key = new Keyboard();												// initialization
			level = Level.spawn; 												// our private instance of Level is equal to instance of level called 'spawn' 
			TileCoordinate playerSpawn = new TileCoordinate(21, 42);			// assigns spawn coordinates for hero
			player = new Player(playerSpawn.x(), playerSpawn.y(), key);			// creates hero at assigned coordinates 
			level.add(player);
			font = new Font();
			
			addKeyListener(key); // add AFTER "key = new Keyboard();"    Gets input from buttons pressed by player
			
			Mouse mouse = new Mouse();
			addMouseListener(mouse);
			addMouseMotionListener(mouse);
		
		}
		
		
		public static int getWindowWidth() {
			return width * scale;
		}
		
		public static int getWindowHeight() {
			return height * scale; 
		}
		
		
		public synchronized void start() 
		{ 					
			running = true;
			thread = new Thread(this, "Display");			// ? I Don't understand this logic
			thread.start();									// ????
		}
		
		public synchronized void stop() 
		{
			running = false;
			try 
			{
				thread.join();
			} catch (InterruptedException e) 
				{ e.printStackTrace();
				}
			
		}
		
		public void run() 
		{
			long lastTime = System.nanoTime(); // ver very very very precise (nanoseconds instead of milliseconds )
			long timer = System.currentTimeMillis();// our 1 second timer
			final double ns = 1000000000.0 / 60.0; // nanosecond conversion    ns variable equals to the time length of 1 frame. (one sixtieth of a second in our case). shown in nanoseconds  
			double delta = 0; 
			int frames = 0; // gonna count how many frames we can render every second
			int updates = 0; // shows how many times update method is called. should be always 60. 
			
			requestFocus(); // puts the game window in focus at launch.  Otherwise we have to click on the window before it will register input from arrow keys. 
			
			while (running)   // long way to write:     while (running == true) 
			{
				
				long now = System.nanoTime();       // TIMER EPISODE 13. REWATCH TO BETTER UNDERSTAND
				delta += (now - lastTime) / ns;
				
				lastTime = now;
				while (delta >= 1)     // delta will be greater than one 60 times every second. when delta exceeds 1, we update frame. then reset delta. 
				{
					update();   // TIME TICK, capped at 60 FPS. UPDATE LOOP of our game (HAS to be NORMALIZED so player on different computers move in the same "Time Zone". Otherwise fast computer will move hero for 1000 pixels every second and slow computer will move hero for 30 pixels every second. 
					updates++;
					delta--;
				}
				
				// System.out.println(now - lastTime);    // way to measure how much time it took to execute code (in nanoseconds)  
							
				render(); 	// rendering, unrestricted. can be 60 FPS, 100 FPS, 1000 FPS. (REDRAW FREQUENCY basically.) 
				frames++; 
				
				if (System.currentTimeMillis() - timer > 1000)
				{
					timer += 1000;
				
					// System.out.println(updates + " updates per second, " + frames + " fps" + " (Game.java)");
					frame.setTitle(title + " | " + updates + " updates per second, " + frames + " fps");
					updates = 0;
					frames = 0;
				}
			}
			stop(); 
		}
		
		
		
		
		
		
		/* int x = 0;
		int y = 0; */
		
		public void update()
		{
			key.update();
			level.update();
			
		}
		
		
	
		public void render() {
			BufferStrategy bs = getBufferStrategy(); 		 // "bs" is a name. its short for "buffer strategy". 	 derives from Canvas
			if (bs == null)									// we only need to initialize buffer once. so we check if its not initialized yet, then initialize it			
					{
						createBufferStrategy(3);  			 // triple buffering.  optimal buffering. 
						return;
					}
		
			screen.clear(); 								// clear old image (otherwise trails will stay)
			double xScroll = player.getX() - screen.width/2;		// putting player in the center of the screen
			double yScroll = player.getY() - screen.height/2;												// screen.render(x, y);  // OLD render method was here
			level.render((int)xScroll, (int)yScroll,  screen); // NEW
			// double ^^ and casting ^^ is correct  // BUT CHERNO HAS AS INT !!!
			
			font.render("HELLO", screen);
			
			// EXPERIMENTAL
			// rogue.render(screen);
			// screen.renderSheet(40, 40, SpriteSheet.player_right, false);
			
				
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			
			Graphics g = bs.getDrawGraphics();   // "g" is a name. its short for "graphics".  linking graphics context for the buffer
			
				// ALL GRAPIHCS GO HERE
				g.drawImage(image,  0,  0,  getWidth(),  getHeight(), null);
				
				// g.setColor(Color.WHITE);
				// g.setFont(new Font("Verdana", 0, 50));
				
				// g.drawString("X: " +  player.x + "Y: " + player.y, 350, 300);     // Shows X and Y. 
				// g.fillRect(Mouse.getX() - 32, Mouse.getY() - 32, 64, 64);				// Mouse check. Draws rectangle at our mouse coordinates. 
				// g.drawString("Button: " + Mouse.getButton(), 80, 80);					// Mouse Button Test. 
				// if (Mouse.getButton() != -1) g.drawString("Button: " + Mouse.getButton(), 80, 80);					// Better Mouse Button Test. 
		
				g.dispose(); 	 // disposes of current graphics used in a frame and releases all system resources 
			bs.show();			// shows next available buffer. buffer swapping (blitting)
		}
		
		
				
			
				
		// THIS IS THE START OF OUR GAME
		
		public static void main(String[] args)   
		{
			Game game = new Game(); 				 // Create a new object of class "Game"
			game.frame.setResizable(false); 		 // we want to LOCK our screen size. Resizing causes TONS of graphical errors, so we want it so our window cannot be resized. 
			game.frame.setTitle(title);
			game.frame.add(game);					// we can add an instance of Game because it is subclass of Canvas
			game.frame.pack();						 // sets frame size to the game size we've defined earlier
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.frame.setLocationRelativeTo(null);			 // sets our window to be at the center of the screen, not in the corner
			game.frame.setVisible(true); 				// VERY IMPORTANT !! ACTUALLY _SHOWS_ THE FRAME :))
		
			game.start();
			
			
		}
		
		
}


// Was in update()
/* OLD MOVEMENT "method"
if (key.up) y--; 
if (key.down) y++; 
if (key.left) x--; 
if (key.right) x++;
*/ 


/* REWATCH: 
 * Episode 76  11:40.
 * 
 * 
 * 
 * ERROR LOG
 *
 * ARRAY OUT OF BOUND EXCEPTIONS:
 * 1) Into the Sprite Constructor we passed a spriteSheet which was 96x128   but misplaced the order of width and height values  (passed parameters of 128 x 96 instead of  96 x 128) 
 *  ERROR WAS HERE :  public static SpriteSheet bard = new SpriteSheet("/Textures/sheets/bardSpriteSheet.png", >>> 96, 128<<< );
 * 	DEBUGGER threw us into the CONSTRUCTOR, which means we have to look "one Level higher", where we're creating an instance of that class and passing specified values as parameters. 
 *
 *
 * X-Y SCREWUPS
 * 		EPISODE 95 CHASING DOESN'T WORK !  << ERROR was in XY-Screwup :       getY  getY      instead of     getX getY     . Found by Sysouting the values.
 *
 *		EPISODE 36 - Rendering the level. // When replacing the render method I
 * 		accidentally set // this.x = yOffset // this.y = xOffset (mixed x and y
 * 		correspondence) // all controls got twisted e.g. press RIGHT and hero moves
 * 		UP and also rendering got weird :)
 *
 *
 *
 * ACTIVE BUG: 
 * Player keeps moving when u click on another screen, chrome browser for example. 
 * And when u switch back to the game, the movement continues until the button is pressed again in that direction. 
 *
 * EPISODE 74. 
 * Projectiles collide with floor and don't collide with wall. (exact opposite)
 * error was in the condition checker in move() method of WizardProjectile. forgot the "!" sign :)
 * if (!level.tileCollision(x, y, nx, ny, 7)) {
		x += nx;
		y += ny;
		}
 *
 * EPISODE 74. 
 * In Level.java projectiles were not initialized.     " p.init(this); "   solved it   
 *
 * EPISODE 69. Projectiles. 
 * When we tested projectile by shooting grass, it only shot in some angles (looked like 45, 90 degrees). that was because dir (angle of shooting) was set as "int" in Projectile constructor. Obviously, it should be "double", not "int".  
 *
 *
 *
 * EPISODE 58-60 
 * When adding new textures for the spawnlevel, there was a MASSIVE fps drop from 2000 to 400. 
 * It seems that the game is rendering tiles multiple times or something like that.  
 *
 *	Also, when rearranging spritesheet.png, there were different errors:
 *	a) Renders blank squares 
 * 	b) Renders tiles of grass AND black square borders on top of them. It seems like it way rendering two layers of tiles on top of each other. <<<< may be the FPS issue
 * 
 * 
 * When we changed level to spawnlevel, all tiles are void. we have to make sure we READ all the new tiles.  (  getTile method (Level.java)  ) 
 * In fact,  this check in getTile method in Level.java was a source of massive FPS drop :   System.out.println("Good (says Level.java)");  
 * 

 * 
 * 
 * 
 * ERROR in a cycle
 * 
 *  огда цикл не работает, проверь, что он не тормозитс€ точкой с зап€той
 * 
 * while(x <= 0); { <<< “очка с зап€той лишн€€ !!!
 * 
 * }
 * 
 * 
 * EPISODE 75.  Advanced sprite creation. We render a sprite 'on top' of our pixels array.
 * 
 * ERRONEOUS: 		if (xa < 0 || xa > width || ya < 0 || ya >= height) continue;			// CRASHED after some performance when XA was EQUAL to WIDTH (gave Out of bounds exception in the Display thread)
 * CORRECT: 		if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;			
 * 
 * 
 * 
 * 
 * EPISODE 82. Collision Detection of particles.
 * Particles get "STUCK" in or near the wall.  Basically collision is detected already INSIDE the wall so they particles can't get out anymore
 * This error is due to ROUNDING ! 
 * 
 * 
 * 
 */



//EXPERIMENTAL
			// TileCoordinate rogueSpawn = new TileCoordinate(22, 68);	
			// rogue = new Dummy(rogueSpawn.x(),rogueSpawn.y(), key);
			// rogue.init(level);

// WHITE RANDOM PARTICLES 
/* 
Sprite sprite = new Sprite (2, 2, 0xffffff);
Random random = new Random(); 
for (int i = 0; i < 100; i++) {
	int x = random.nextInt(20);
	int y = random.nextInt(20);
	screen.renderSprite(width - 60 + x, 50 + y, sprite, true);
}
*/