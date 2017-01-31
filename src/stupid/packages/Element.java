package stupid.packages;

import org.newdawn.slick.opengl.Texture;

public class Element {
	
	World gameWorld;
	
	Interface gameInterface;
	
	Texture button;
	
	int x;
	int y;
	
	int width;
	int height;
	
	boolean isClickable;
	
	String text;
	int textSize;
	
	int action;
	
	public Element(World newWorld, Interface newInterface, Texture newTexture, int newX, int newY, int newWidth, int newHeight, boolean newClick, String newText, int newSize, int newAction) {
		
		gameWorld = newWorld;
		
		gameInterface = newInterface;
		
		button = newTexture;
		
		x = newX;
		y = newY;
		
		width = newWidth;
		height = newHeight;
		
		isClickable = newClick;
		
		text = newText;
		textSize = newSize;
		
		action = newAction;
	}
	
	public void update(){
		
		if(isClickable){
			if(action == 1){
				if(gameInterface.isClicked(x - 2,y - 2,width / 2,height / 2)){
					gameWorld.play = true;
				}
			}
			if(action == 2){
				if(gameInterface.isClicked(x - 2,y - 2,width / 2,height / 2)){
					gameInterface.help = true;
				}
			}
			if(action == 3){
				if(gameInterface.isClicked(x - 2,y - 2,width / 2,height / 2)){
					System.exit(1);
				}
			}
			if(action == 4){
				if(gameInterface.isClicked(x - 2,y - 2,width / 2,height / 2)){
					gameInterface.help = false;
					gameWorld.lost = false;
				}
			}
		}
	
		gameWorld.drawElement(button, x, y, width, height);
		gameWorld.drawString(text, x + 8, y, textSize);
	}

}
