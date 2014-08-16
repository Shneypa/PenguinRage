package com.Shneypa.RealmOfTheMadGod.Entity.particle;

import com.Shneypa.RealmOfTheMadGod.Entity.Entity;
import com.Shneypa.RealmOfTheMadGod.graphics.Screen;
import com.Shneypa.RealmOfTheMadGod.graphics.Sprite;

public class Particle extends Entity {

		// private List<Particle> particles = new ArrayList<Particle>();
		private Sprite sprite; 
		
		private int life;
		private int time = 0;
		
		protected double xx, yy, zz; 
		protected double xa, ya, za;
		
		public Particle(int x, int y, int life) {
			// System.out.println("Particle life:" + life + "			 - (Particle.java");
			this.x = x;
			this.y = y;
			this.xx = x;
			this.yy = y;
			this.life = life + (random.nextInt(50) - 25);
			sprite = Sprite.particle_normal;
			// particles.add(this); // adds the instance of Particle we've just created into our array List. 
		
			this.xa = random.nextGaussian();			
			this.ya = random.nextGaussian();
			this.zz = random.nextFloat();
		}
		
		
		
	
		public void update() {
			time++;
			if (time >= 7400 - 1) time = 0;
			if (time > life) remove();
			za -= 0.1;			// we are changing the RATE of change of ZZ coordinate	(and hence YY coordinate) 		
			
			if(zz < 0 ) {
				zz = 0;
				za *=-0.7;
				xa *= 0.5;
				ya *= 0.5;
			}
			
			move((xx +xa), (yy + ya) + (zz + za));
			
		}
		
		
		private void move(double x, double y) {
			if (collision(x,y)) {			// IF there is collision, then we want to REVERSE the direction the particle is going
				this.xa *= -0.5;
				this.ya *= -0.5;
				this.za *= -0.5;
			}
			this.xx += xa;
			this.yy += ya;
			this.zz += za;  	// every tick we subtract more and more from za, so we get kinda exponential change in coordinate (it looks like particles fall for gravity)
			
		}

	
		
		// COLLISION DETECTION FOR PARTICLES
		
				public boolean collision(double x, double y) {			
					boolean solid = false;
					for (int c = 0; c < 4; c++) {  
						double xt = (x - c % 2 * 16) / 16;	 
						double yt = (y - c / 2 * 16) / 16; 	
						int ix = (int) Math.ceil(xt);  // Math.ceil(double) =   rounding the number UP when we cast to an integer
						int iy = (int) Math.ceil(yt); 
						if (c % 2 == 0) ix = (int) Math.floor(xt); // c % 2 == 0  is when we are checking for the left side  
						if (c / 2 == 0) iy = (int) Math.floor(yt); 
						if (level.getTile(ix, iy).solid()) solid = true; 
					}
					return solid;
				}


		public void render (Screen screen) {
			screen.renderSprite((int) xx -1, (int) yy - (int) zz -2,  sprite,  true);
		}
}




/* DELETED OLD CONSTRUCTOR: 
public Particle(int x, int y, int life, int amount) {
this(x, y, life);			// Episode 76  11:40. Here, "this" treats the constructor as any other method and simply executes code inside it (inside Particle constructor)

for (int i = 0; i < amount - 1; i++) {
	particles.add(new Particle(x, y, life));
}
particles.add(this);
}

*/