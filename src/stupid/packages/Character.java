package stupid.packages;

import java.io.IOException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Character {

	double x = 0;
	double y = 0;
	
	double xVel = 0;
	double yVel = 0;
	
	double xVelPercent = 50;
	double yVelPercent = 50;
	
	double totalVelocity = 0;
	
	double angle = 0;
	
	int delta = 0;
	
	final double SPEED = 0.0000000005;
	
	final double ROTATIONAL_SPEED = 0.00000003;
	
	final double TOP_VELOCITY = 0;
	
	final double ACCELERATION = 0.00000001;
	
	double boost = 100;
	
	Texture character = null;
	
	public Character(double newX, double newY, double newXVel, double newYVel, double newAngle, int newDelta) {
		
		delta = newDelta;
		
		x = newX;
		y = newY;
		
		xVel = newXVel;
		yVel = newYVel;
		
		angle = newAngle;
		
		loadData();
	}
	
	public void loadData(){
		
		try{
			
			character = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/sprites/character.png"), GL11.GL_NEAREST);
			
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			angle -= ROTATIONAL_SPEED * delta;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			angle += ROTATIONAL_SPEED * delta;
		}
		
		if(x < 6){
			xVel = -xVel;
		}
		if(y < 6){
			yVel = -yVel;
		}
		if(x > 1920 - 69){
			xVel = -xVel;
		}
		if(y > 1080 - 69){
			yVel = -yVel;
		}
		
		x += xVel;
		y += yVel;
	
	}
	
	public void draw(){
		drawTexture(character, x, y);
	}
	
	public void drawTexture(Texture newTexture, double newX, double newY){
		
		newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(x + 32, y + 32, 0);
		
		GL11.glRotated(angle, 0, 0, 1);
		
		GL11.glTranslated(-(x + 32), -(y + 32), 0);
		
		GL11.glBegin(GL11.GL_QUADS);
			
		GL11.glTexCoord2f(0,0);
   		GL11.glVertex2d(newX,newY);
   		GL11.glTexCoord2f(1,0);
		GL11.glVertex2d(newX + newTexture.getTextureWidth(),newY);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2d(newX+newTexture.getTextureWidth(),newY+newTexture.getTextureHeight());
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2d(newX,newY+newTexture.getTextureHeight());	
    		
    	GL11.glEnd();
		
    	GL11.glPopMatrix();
	}

}
