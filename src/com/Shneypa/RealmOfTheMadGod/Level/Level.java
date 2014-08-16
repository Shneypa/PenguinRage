package com.Shneypa.RealmOfTheMadGod.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.Shneypa.RealmOfTheMadGod.Entity.Entity;
import com.Shneypa.RealmOfTheMadGod.Entity.Mob.Player;
import com.Shneypa.RealmOfTheMadGod.Entity.particle.Particle;
import com.Shneypa.RealmOfTheMadGod.Entity.projectile.Projectile;
import com.Shneypa.RealmOfTheMadGod.Level.tile.Tile;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.util.Vector2i;

// quick constructor - random
// long constructor - from image file

public class Level {

										// protected Tile[] tiles;
	protected int width, height; 
	protected int[] tilesInt;  			// tile IDs
	protected int[] tiles; 				// this will contain all the level's tiles
	protected int tile_size; 				// this he has as "protected"
	
	private List<Entity> entities = new ArrayList<Entity>(); // we are using ArrayList and not array, so the size of it can be dynamic (only as many elements as there are entities)
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	public  List<Player> players = new ArrayList<Player>();
	
	// COMPARATOR to sort nodes by fCost (total cost)
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1; // if n1 fCost is less than n0 then move n1 up one position in the openList array 
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	public static Level spawn = new SpawnLevel("/levels/spawnlevel.png"); 		// creating a new instance of level by name spawn. 
	
	
	public Level(int width, int height)	{
				this.width = width;
				this.height = height;
				tilesInt = new int[width * height];
														//old line: generateLevel();
	}

	public Level(String path) {
		loadLevel(path); 		// получили массив пикселей из spawnlevel.png 
														//old line: generateLevel();
		
		}
	
	

		
		protected void loadLevel(String path) {
													// how is this empty??? :) 
													// A: because we created an instance of SpawnLevel. 
													// And SpawnLevel Class has its own loadLevel(String path) method which is used. (it overrides this empty method)  
													// Q14: then why this empty level is even here?  
		}
		
		public void update() {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).update();
			}
			for (int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).update();
			}
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).update();
				
			}
			for (int i = 0; i < players.size(); i++) {
				players.get(i).update();
			}
			remove();
		}
		
		private void remove() {
			for (int i = 0; i < entities.size(); i++) {
				if (entities.get(i).isRemoved()) entities.remove(i);
			}
			for (int i = 0; i < projectiles.size(); i++) {
				if (projectiles.get(i).isRemoved()) projectiles.remove(i);
			}
			for (int i = 0; i < particles.size(); i++) {
				if (particles.get(i).isRemoved()) particles.remove(i);
			}
			for (int i = 0; i < players.size(); i++) {
				if (players.get(i).isRemoved()) players.remove(i);
			}
		}
		
		public List<Projectile> getProjectiles() {
			return projectiles;
		}
		
		private void time() {   // so we can have nightlevels ( change of time of the day )  
			
		}
		
		
		// COLLISION DETECTION
		
		public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {			// x and y is the position of entity that is colliding. xa and ya will be the direction of where it's heading
			boolean solid = false;
			for (int c = 0; c < 4; c++) {   // 4 corner system. checks for the corner of tile. and if any corner belongs to a solid tile, it returns solid. 
				int xt = (x - c % 2 * size + xOffset) >> 4;	 
				int yt = (y - c / 2 * size + yOffset) >> 4; 	
				 // System.out.println("X: " + xt + " Y: " + yt + "       (tile precision)    Level.java");
				if (getTile(xt, yt).solid()) solid = true; 
			}
			return solid;
		}
		
		
		// CORNER PINS
		
		public void render(int xScroll, int yScroll, Screen screen) {
			screen.setOffset(xScroll, yScroll);				 // E34
			int x0 = xScroll >> 4; 							// dealing with tile coordinates, not pixel coordinates (tile coordinate (1,2), not tile at (16,32). Keep it simple)
			int x1 = (xScroll + screen.width + 16) >> 4;
			int y0 = yScroll >> 4;
			int y1 = (yScroll + screen.height + 16) >> 4; 
		
			for (int y = y0; y < y1; y++) {
				for (int x = x0; x < x1; x++) {
					getTile(x, y).render(x, y, screen); 
				
					/*if (x + y * 16 < 0 || x + y * 16 >= 256)  {
						Tile.voidTile.render(x, y, screen);
						continue;
					}
					tiles[x + y * 16].render(x, y, screen);
					*/
				}
				
			}
			
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).render(screen);
			}
			
			for (int i = 0; i < projectiles.size(); i++) {
				projectiles.get(i).render(screen);
			}
			
			for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
			}
			
			for (int i = 0; i < players.size(); i++) {
				players.get(i).render(screen);
				}
		}
		
		
		public void add(Entity e) {
			e.init(this);
			if (e instanceof Particle) {
				particles.add((Particle) e);
			} else if (e instanceof Projectile) {
				projectiles.add((Projectile) e);
			} else if (e instanceof Player) {
				players.add((Player) e);				// добавл€ем экземпл€р игрока в ArrayList<> игроков
			} else {
				entities.add(e);
			}
		}
		
		// method to return Players' positions so the Chaser can know his coordinates and can follow him 
		public List<Player> getPlayers() {
			return players;
		}
		
		public Player getPlayerAt(int index) {
			return players.get(index);
		}
		
		public Player getClientPlayer() {
			return players.get(0); 
		}
		
		// EPISODE 100  - A*-Star Search Algorithm
		
		// Array list of Nodes for the A*-Star Search Algorithm
		// at the start all tiles (nodes) go into openList
		// if we discard a tile (node) we remove it from openList and put into "don't look at it anymore"-list  aka closedList
		
		public List<Node> findPath(Vector2i start, Vector2i goal) {
			List<Node> openList = new ArrayList <Node>();
			List<Node> closedList = new ArrayList <Node>();
			Node current = new Node(start, null, 0, getDistance(start, goal));
			openList.add(current);
			while(openList.size() > 0) {
				Collections.sort(openList, nodeSorter);			//  sorts array (openList) by rules of the comparator (nodeSorter) 
				current = openList.get(0);
				if (current.tile.equals(goal)) {
					
					List<Node> path = new ArrayList<Node>();				// Reconstruction of the path
					while (current.parent != null) {			// RETRACE from FINISH to START.  keep looping from finish to start (at start node the parent is equal to null)
						path.add(current);
						current = current.parent; 			// we set current tile to parrent, thus jumping backwards one tile until we reach the start.
						
					}
					openList.clear();
					closedList.clear();
					return path;
				}
				openList.remove(current);
				closedList.add(current);
				
				for (int i = 0; i < 9; i++) {
					if (i == 4) continue;			// tile #4 is where we're standing, we don't need to check it
					int x = current.tile.getX();	
					int y = current.tile.getY();
					int xi = (i % 3) - 1 ;		// values:   -1  0  1     //  x direction
					int yi = (i / 3) - 1 ; 		// why we divide? 
					Tile at = getTile (x + xi, y + yi);
					if (at == null) continue;
					if (at.solid()) continue; 
					Vector2i a = new Vector2i(x + xi, y + yi);
					double gCost = current.gCost + getDistance(current.tile, a) == 1 ? 1 : 0.95; // gCost is distance between our tile (current) and the one we're looking into (at)  or vector a. (Current gCost stores all g-costs up to the current point. But at the start, its Zero)  
													// ^^ a tweak so mob prefers diagonals (we reduced the gCost of diagonal from 1.41 to 0.95) 
					double hCost = getDistance(a, goal);						// hCost is distance from tile we're looking at (a) to the finish (goal)
					Node node = new Node(a, current, gCost, hCost); // creating new node with current node as parent, vector specified (a) and gCost and hCost calculated  
					if (vecInList(closedList, a) && gCost >= node.gCost) continue;	// 36:00 node CAN get RE-OPENED 					// check if the node is the same as the one we've just came from
					if (!vecInList(openList, a) || gCost <= node.gCost) openList.add(node);	
				}
			}
			closedList.clear();
			return null;
		}
		
		// Method that checks if a vector is already corresponding a node which is in the closedList
		private boolean vecInList(List<Node> list, Vector2i vector) {
			for (Node n : list) {
				if (n.tile.equals(vector)) return true;
			}
			return false;
		}
		
		
		// calculation of hCost (DIRECT DISTANCE) 	
		private double getDistance(Vector2i tile, Vector2i goal) {
			double dx = tile.getX() - goal.getX();
			double dy = tile.getY() - goal.getY();
			double distance = Math.sqrt(dx * dx + dy * dy);
			return distance;
		}
		
		public List<Entity> getEntities(Entity e, int radius) {
			List<Entity> result = new ArrayList<Entity>();
			int ex = (int)e.getX();	// casting correct but in episode 104 its int again !
			int ey = (int)e.getY(); // casting correct but in episode 104 its int again !
			for (int i = 0; i < entities.size(); i++) {
				Entity entity = entities.get(i); 
				if (entity.equals(e)) continue; // we don't want the shooter to add itself as a closest target
				int x = (int)entity.getX();	// casting correct but in episode 104 its int again !
				int y = (int)entity.getY(); // casting correct but in episode 104 its int again !
				
				int dx = Math.abs(x - ex);
				int dy = Math.abs(y - ey);
				double distance = Math.sqrt((dx*dx) + (dy*dy));
				
				if (distance <= radius) result.add(entity);			// if the player is in radius of the chaser 
				
			}
			return result;
		}
		
		
		
		public List<Player> getPlayers(Entity e, int radius) {
			List<Player> result = new ArrayList<Player>();
			int ex = (int)e.getX();	// casting correct
			int ey = (int)e.getY(); // casting correct
			for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			// System.out.println("i = " + i + "      - Level.java");
			// System.out.println("players.size() " + players.size() + "      - Level.java");
					int x = (int)player.getX(); // casting correct
					int y = (int)player.getY(); // casting correct
					int dx = Math.abs(x - ex);
					int dy = Math.abs(y - ey);
					double distance = Math.sqrt((dx*dx) + (dy*dy));
					if (distance <= radius) result.add(player);	
								
			}
			return result;
		}
		
		
		
		public Tile getTile(int x, int y) {
			if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;	     // prevents Array out of bounds exception
			// System.out.println("Good (says Level.java)");   // this message was a source of massive FPS drop
			if (tiles[x + y * width] == Tile.col_spawn_floor) return Tile.spawn_floor;  // *** Q: I don't get this check
			if (tiles[x + y * width] == Tile.col_spawn_grass) return Tile.spawn_grass;
			if (tiles[x + y * width] == Tile.col_spawn_hedge) return Tile.spawn_hedge;
			if (tiles[x + y * width] == Tile.col_spawn_wall1) return Tile.spawn_wall1;
			if (tiles[x + y * width] == Tile.col_spawn_wall2) return Tile.spawn_wall2;
			if (tiles[x + y * width] == Tile.col_spawn_water) return Tile.spawn_water;
			return Tile.voidTile; 										
		}
}




// this generateLevel method I had empty for some reason
/*	protected void generateLevel() {
		for (int y = 0; y < 64; y++) {				// For our 64x64 map 	
			for (int x = 0; x < 64; x++) {
				getTile(x, y); 
			}
		}
		tile_size = 16;
	} */ 

		//Figure out which tile we want to render based on our random generator
		// Method returns a Tile 
		// if someone gets out of map bounds we need to render some black tile 
		
		

		//Codes for tiles: 
		// Grass 	=   green 		 =    0xff00FF00			ADD ff up front for the alpha channel
		// Flower	=   yellow 		 =    0xffFFFF00
		// Rock 	=   dark yellow	 =    0xff7F7F00


//we can TEMPORARY return NULL, just to get rid of an error. Then we change it to VoidTile.

/*
public void addProjectile(Projectile p) {
p.init(this);
projectiles.add(p);
}
*/