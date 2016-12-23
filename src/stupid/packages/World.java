package stupid.packages;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {
	
	Texture background = null;
	
	Character gameCharacter = new Character(this);

	public World(){
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
		
		gameCharacter.update(delta);
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
