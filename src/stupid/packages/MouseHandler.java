package stupid.packages;

import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class MouseHandler {

	float mouseX = 100;
	float mouseY = 100;
	
	float mouseXStep = 0;
	float mouseYStep = 0;
	
	Texture cursor = null;
	
	final float MOUSE_SPEED = 0.5f;
	
	public MouseHandler() {
		
		loadData();
		
		try{
			
			Mouse.create();
			
		} catch (LWJGLException e) {
			
	        e.printStackTrace();
	        System.exit(0);
	    }  
		
		Mouse.setGrabbed(true);
	}
	
	public void loadData(){
		
		try{
			
			cursor = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/ui/cursor.png"), GL11.GL_NEAREST);
			
		}
		catch (IOException e){
			e.printStackTrace();
		}	
		
	}
	
	public void update(){
		
		mouseXStep = Mouse.getDX() * MOUSE_SPEED;
		mouseYStep = -Mouse.getDY() * MOUSE_SPEED;
		
		mouseX += mouseXStep;
		mouseY += mouseYStep;
		
		if(mouseX < -2){
			mouseX = -2;
		}
		if(mouseY < -2){
			mouseY = -2;
		}
		if(mouseX > Display.getWidth()){
			mouseX = Display.getWidth();
		}
		if(mouseY > Display.getHeight()){
			mouseY = Display.getHeight();
		}
		
		drawTexture(cursor, mouseX, mouseY);
		
	}
	
	public void drawTexture(Texture newTexture, float newX, float newY){
		
		newTexture.bind();
		
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			
		GL11.glTexCoord2f(0,0);
   		GL11.glVertex2f(newX,newY);
   		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(newX + newTexture.getTextureWidth(),newY);
		GL11.glTexCoord2f(1,1);
		GL11.glVertex2f(newX+newTexture.getTextureWidth(),newY+newTexture.getTextureHeight());
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(newX,newY+newTexture.getTextureHeight());	
    		
    	GL11.glEnd();
	}

}
