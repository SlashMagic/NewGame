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
	
	float xVel = 0.5f;
	float yVel = 0.5f;
	
	float angle = 90;
	
	Texture sprite_1;
	
	World gameWorld;
	
	int attackTimer = 0;
	
	int energy;
	
	public Character(World newWorld) {
		gameWorld = newWorld;
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
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			if(xVel < 0){
				x += xVel - 0.25;
			}
			else{
				x += xVel + 0.25;
			}
			if(yVel < 0){
				y += yVel - 0.25;
			}
			else{
				y += yVel + 0.25;
			}
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			if(xVel < 0){
				x += xVel + 0.25;
			}
			else{
				x += xVel - 0.25;
			}
			if(yVel < 0){
				y += yVel + 0.25;

			}
			else{
				y += yVel - 0.25;

			}
		}
		else{
			x += xVel;
			y += yVel;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			angle -= 7;
			if(angle < -180){
				angle = 180;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			angle += 7;
			if(angle > 180){
				angle = -180;
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && attackTimer > 1000){
			gameWorld.createProjectile(x, y, angle);
			attackTimer = 0;
		}
		
		attackTimer += delta;
		
		drawTexture(sprite_1,x, y);
	}
	
	public void drawTexture(Texture newTexture, float x, float y){
		newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 4, y + 4, 0);
		
		GL11.glRotated(angle, 0, 0, 1);
		
		GL11.glTranslated(-(x + 4), -(y + 4), 0);
		
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
    	
    	GL11.glPopMatrix();
	
	}

}
