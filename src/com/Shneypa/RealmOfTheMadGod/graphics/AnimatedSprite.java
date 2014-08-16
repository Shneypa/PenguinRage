package com.Shneypa.RealmOfTheMadGod.graphics;

public class AnimatedSprite extends Sprite {

	private int frame = 0; 
	private Sprite sprite;
	private int rate = 5;
	private int length = -1; // amount of frames our animation has. we initialize to -1 so we dont get an error. 
	private int time = 0;
	
	
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.getSprites()[0];
		if (length > sheet.getSprites().length) System.out.println("Error.  Length of animation is too long  - AnimatedSprite.java");
		
	}


	public void update() {
		time++;
		if (time % rate == 0 ) {
			if (frame >= length - 1) frame = 0;
			else frame++;
			sprite = sheet.getSprites()[frame]; // get sprite AT index array 'frame' 
		}
		// System.out.println(sprite + ", Frame " + frame + "      - AnimatedSprite.java" );
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int frames) {
		rate = frames;
	}
	
	public void setFrame(int index) {
		if (index > sheet.getSprites().length -1) {
			System.err.println("Error:  Index out of bounds in  "+ this + "   -     AnimatedSprite.java");
			return;
		}
		sprite = sheet.getSprites()[index];
	}
}