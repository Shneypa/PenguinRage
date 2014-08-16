package com.Shneypa.RealmOfTheMadGod.Level;

import com.Shneypa.RealmOfTheMadGod.util.Vector2i;

// A*-STAR SEARCH ALGORYTHM  - NODE 

public class Node {
	
	// VARIABLES
	public Vector2i tile;  // next step where we want to go 
	public Node parent;
	public double fCost, gCost, hCost;
								// hCost - DIRECT distance (as BIRD FLIES) from current node to final node
								// gCost - distance.. sum of all node-to-node distances 
	
			// gCost or hCost??  ( (but taking into consideration the obstructions along the way e.g. if we get slowed down by river - we will add this to the cost)
	
	// CONSTRUCTOR
	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
	
}
