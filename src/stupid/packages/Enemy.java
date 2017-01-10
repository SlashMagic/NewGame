package stupid.packages;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Enemy {

	float x;
	float y;
	
	float xVel;
	float yVel;
	
	float angle;
	
	Texture enemy;
	
	int energy;
	
	public Enemy() {
		
		loadData();
	}
	public void loadData(){
		try{
			
			enemy= TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/sprites/sprite_1.png"), GL11.GL_NEAREST);
		
		}
		catch(IOException e){
			
		}
	}
}
