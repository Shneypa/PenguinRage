package com.Shneypa.RealmOfTheMadGod.Entity.Spawner;

import com.Shneypa.RealmOfTheMadGod.Entity.Spawner.Spawner.Type;
import com.Shneypa.RealmOfTheMadGod.Entity.particle.Particle;
import com.Shneypa.RealmOfTheMadGod.Level.Level;

public class ParticleSpawner extends Spawner {
	private int life;
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++ ) {
			level.add(new Particle(x, y, life));
			
		}
	}

}
