package com.Shneypa.RealmOfTheMadGod.util;

import com.Shneypa.RealmOfTheMadGod.graphics.Screen;

public class Debug {

	// this is a static class hence keyword 'private'
	// this is not supposed to be instantiated... ever
	
	private Debug() {
		
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed) {
		screen.drawRect(x, y, width, height, 0xff0000, fixed);
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, int colour, boolean fixed) {
			screen.drawRect(x, y, width, height, colour, fixed);
	}
		
	
}
