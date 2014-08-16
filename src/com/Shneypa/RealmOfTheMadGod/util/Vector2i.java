package com.Shneypa.RealmOfTheMadGod.util;

// Vector 2D (x,y) and   "i" for integer
public class Vector2i {

	private int x, y; 
	
	//CONSTRUCTORS:
	
		// All-purpose
		public Vector2i(int x, int y) {
			set(x,y);
	
		}
		
		// new vector with location (0,0) (no parameters needed)
		public Vector2i() {		
			set(0,0);
		}
		
		
		// make new vector out of existing vector
		public Vector2i(Vector2i vector) {				
			set(vector.x, vector.y);
		}
	// end of CONSTRUCTORS
	
	
		
		
	// Creating vectors, at the same time adjusting coordinate if we want so	
	public void test() {
		Vector2i playerPosition = new Vector2i(80,40).setX(15);		// we initialize new Vector2i and instantly change its X coordinate (just cuz we can :) ) 
		Vector2i mobPosition = new Vector2i(playerPosition).setX(50);		// we create another vector out of player's vector (and shift it a bit to 50). Mob has to have his own vector of course. 
	}
	
	// Add a vector to another vector
	public Vector2i add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;			// <<<  Don't forget what we're returning !!!
	}
	
	// Subtract a vector from another vector
	public Vector2i subtract(Vector2i vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;			// <<<  Don't forget what we're returning !!!
	}
	
	// Calculate distance between two points (two vectors) 
	public static double getDistance(Vector2i v0, Vector2i v1) {
		double x = v0.getX() - v1.getX();
		double y = v0.getY() - v1.getY();
		return Math.sqrt(x * x + y * y);
		
	}
	
	// Our own Equals Method
	public boolean equals(Object object) {
		if (!(object instanceof Vector2i) ) return false;
		Vector2i vec = (Vector2i) object;
		if (vec.getX() == this.getX() && vec.getY() == this.getY()) return true;
		return false;
	}
	
	
	// GETTERS AND SETTERS
		public int getX() {
			return x;
		}
			
		public int getY() {
			return y;
		}
		
		public Vector2i setX(int x) {  			// instead of VOID we return Vector2i-type object
			this.x = x; 
			return this;						// returning Vector2i object
		}
		
		public Vector2i setY(int y) {
			this.y = y; 
			return this;
		}
		
		public void set(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		// end of GETTERS AND SETTERS
	
}

