package com.Shneypa.RealmOfTheMadGod.graphics;

public class Font {

		private static SpriteSheet font = new SpriteSheet("/fonts/arial.png", 16); // CHERNO has 16
		
		private static Sprite[] characters = Sprite.split(font);
		
		private static String charIndex = "ABCDEFGHIJKLM" + // 
										  "NOPQRSTUVWXYZ" + //
										  "abcdefghijklm" + //
										  "nopqrstuvwxyz" + //
										  "0123456789.,'" + //
										  "'\"\";:!@$%()-+"; //  \" is how we type "   without it acting as coding symbol. just as a regular symbol
		public Font() {
			System.out.println("Font constructor ran!      - Font.java");
		}

		public void render(Screen screen) {
			screen.renderSprite(50, 50, characters[3], false);
		}
		
		public void render(String text, Screen screen) {
			//System.out.println("characters[0] = " + characters[0] + " -  Font.java" );
			//System.out.println("text = " + text + " 		- Font.java");
			int x = 50;
			int y = 50; 
			
			for (int i = 0; i < text.length(); i++) {
			//	System.out.println("i = " + i);
			
				
				char currentChar = text.charAt(i);
				//System.out.print( currentChar + " ");
				int index = charIndex.indexOf(currentChar); 
				//System.out.print(index + " ");
				//System.out.println("text.lenght() = "  + text.length());
				//System.out.println("i = " + i);
				screen.renderSprite(x  + i * 16, y, characters[index], false);	
				
				if (index == -1) continue;
				//System.out.println("characters[0] = " + characters[0]);			// we get first sprite but not second from SPLIT
							// HERE WE CRASH 
			//	System.out.println(" chaar index = " + index + " ");
				// System.out.println("text lenght = " + text.length());
			}
			//System.out.println(" ^^ Font.java ^^");
		}
}
