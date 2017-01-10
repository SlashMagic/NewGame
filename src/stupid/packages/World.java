package stupid.packages;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {
	
	Texture background;
	Texture border;
	
	Character gameCharacter = new Character(this);
	
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public World(){
		loadData();
	}
	
	public void loadData(){
		
		try{
			
			background = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/background_1.png"), GL11.GL_NEAREST);
			border = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/border_1.png"), GL11.GL_NEAREST);
		}
		catch (IOException e){
			e.printStackTrace();
		}	
	}
	
	public void createProjectile(float x, float y, float angle){
		projectiles.add(new Projectile(this, x, y, angle));
	}
	
	public void update(int delta){
		
		drawTexture(background, 0, 0);
		
		gameCharacter.update(delta);
		
		for(int i = 0; i < projectiles.size(); i++){
			projectiles.get(i).update();
		}
		
		drawTexture(border, 0, 0);
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
