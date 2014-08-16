package com.Shneypa.RealmOfTheMadGod.Entity.Spawner;

import com.Shneypa.RealmOfTheMadGod.Entity.Entity;
import com.Shneypa.RealmOfTheMadGod.Entity.particle.Particle;
import com.Shneypa.RealmOfTheMadGod.Level.Level;

public class Spawner extends Entity {

	// private List<Entity> entities = new ArrayList<Entity>();

	public enum Type {   // create a new type of variable which can take one of the following values : MOB or PARTICLE
		MOB, PARTICLE; 
	}
	
	private Type type; // creating a variable of this new type we've just created
	
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type; 
		
	}
}
