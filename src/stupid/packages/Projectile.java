package stupid.packages;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Projectile {

	float x;
	float y;
	
	float xVel = 1f;
	float yVel = 1f;
	
	float angle;
	
	Texture projectile;
	
	int energy = 5;
	
	World gameWorld;
	
	final float TOTAL_VELOCITY = 0.5f;
	
	public Projectile(World newWorld, float newX, float newY, float newAngle) {
		
		gameWorld = newWorld;
		
		x = newX;
		y = newY;
		
		angle = newAngle;
		
		loadData();
	}

	public void loadData(){
		try{
			projectile = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/projectiles/laser_1.png"), GL11.GL_NEAREST);
		}
		catch(IOException e){
			
		}
	}
	public void update(){
		
		xVel =  (float) (TOTAL_VELOCITY * Math.cos((angle) * (Math.PI / 180)));
		yVel =  (float) (TOTAL_VELOCITY * Math.sin((angle) * (Math.PI / 180)));
		
		if(x < 2 && xVel < 0){
			float tempAngle = 90 - angle;
			angle = 90 + tempAngle;
			energy -= 1;
		}
		if(y < 2 && yVel < 0){
			float tempAngle = 0 - angle;
			angle = 0 + tempAngle;
			energy -= 1;
		}
		if(x > 236 && xVel > 0){
			float tempAngle = -90 - angle;
			angle = -90 + tempAngle;
			energy -= 1;
		}
		if(y > 130 && yVel > 0){
			float tempAngle = 180 - angle;
			angle = 180 + tempAngle;
			energy -= 1;
		}
		
		
		x += xVel;
		y += yVel;
		
		if(energy <= 0){
			gameWorld.projectiles.remove(this);
		}
		
		drawTexture(projectile);

	}
	
	public void drawTexture(Texture newTexture){
		
		newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 1, y + 2, 0);
		
		GL11.glRotated(angle + 90, 0, 0, 1);
		
		GL11.glTranslated(-(x + 1), -(y + 2), 0);
		
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
