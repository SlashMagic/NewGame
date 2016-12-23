package stupid.packages;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Character {
	
	float x = 50;
	float y = 50;
	
	float xVel = 1f;
	float yVel = 1f;
	
	Texture sprite_1;
	
	public Character(World newWorld) {
		
		loadData();
	
	}
	
	public void loadData(){
		
		try{
			
			sprite_1 = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/sprites/sprite_1.png"), GL11.GL_NEAREST);
		
		}
		catch(IOException e){
			
		}
	}
	
	public void update(int delta){
		
		if(x < 2 && xVel < 0){
			xVel = -xVel;
		}
		if(y < 2 && yVel < 0){
			yVel = -yVel;
		}
		if(x > 230 && xVel > 0){
			xVel = -xVel;
		}
		if(y > 125 && yVel > 0){
			yVel = -yVel;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && yVel < 0){
			x += xVel;
			y = y + yVel - 0.8f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S) && yVel > 0){
			x += xVel;
			y = y + yVel + 0.8f;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A) && xVel < 0){
			x = x + xVel - 0.8f;
			y += yVel;
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D) && xVel > 0){
			x = x + xVel + 0.8f;
			y += yVel;
		}
		else{
			x += xVel;
			y += yVel;
		}
		drawTexture(sprite_1,x, y);
	}
	
	public void drawTexture(Texture newTexture, float x, float y){
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glTexCoord2f(0,0);
			GL11.glVertex2f(x,y);
			GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(x + newTexture.getTextureWidth(),y);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(x+newTexture.getTextureWidth(),y+newTexture.getTextureHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(x,y+newTexture.getTextureHeight());	
    		
    	GL11.glEnd();
	}

}
