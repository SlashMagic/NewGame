package stupid.packages;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {
	
	boolean isPaused = false;
	
	MouseHandler gameMouse;
	
	Texture background = null;
	
	Character mainCharacter;
	
	public World(int delta){
		mainCharacter = new Character(100, 100, -1, 1, 90, delta);
		gameMouse = new MouseHandler();
		loadData();
	}
	
	public void loadData(){
		
		try{
			
			background = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/background_1.png"), GL11.GL_NEAREST);
			
		}
		catch (IOException e){
			e.printStackTrace();
		}	
	}
	
	public void update(int delta){
		
		drawTexture(background, 0, 0);
		if(isPaused){
			gameMouse.update();
		}
		else if(!isPaused){
			mainCharacter.update();
			gameMouse.update();
			mainCharacter.draw();
		}
	}
	
	public void drawTexture(Texture newTexture, int newX, int newY){
		
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			
		GL11.glTexCoord2f(0,0);
   		GL11.glVertex2f(0,0);
   		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(0 + newTexture.getTextureWidth(),0);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(0+newTexture.getTextureWidth(),0+newTexture.getTextureHeight());
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(0,0+newTexture.getTextureHeight());	
    		
    	GL11.glEnd();
		
	}
	
}
