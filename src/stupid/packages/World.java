package stupid.packages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {
	
	Texture background;
	Texture border;
	Texture enemy;
	Texture projectile;
	Texture sprite_1;
	
	Character gameCharacter;
	
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>(); 
	
	public World(){
		
		loadData();
		
		gameCharacter = new Character(this, sprite_1);

		Random rnd = new Random();
		
		int numOfEnemies = rnd.nextInt(5) + 3;
		for(int i = 0; i < numOfEnemies; i ++){
			int newX = rnd.nextInt(200) + 40;
			int newY = rnd.nextInt(95) + 40;
			float newXVel = rnd.nextFloat();
			float newYVel = rnd.nextFloat();
			enemies.add(new Enemy(this, gameCharacter, newX, newY, newXVel, newYVel, enemy));
		}
				

	}
	
	public void loadData(){
		
		try{
			
			background = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/background_1.png"), GL11.GL_NEAREST);
			border = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/border_1.png"), GL11.GL_NEAREST);
			enemy= TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/sprites/sprite_2.png"), GL11.GL_NEAREST);
			projectile = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/projectiles/laser_1.png"), GL11.GL_NEAREST);
			sprite_1 = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/sprites/sprite_1.png"), GL11.GL_NEAREST);
			// load texture in here
			
		}
		catch (IOException e){
			e.printStackTrace();
		}	
	}
	
	public void createProjectile(float x, float y, float angle){
		projectiles.add(new Projectile(this, x, y, angle, projectile));
	}
	
	public void update(int delta){
		
		for(int i = 0; i < enemies.size(); i++){
			for(int j = 0; j < projectiles.size(); j++){
				if(enemies.get(i).isColliding(enemies.get(i).getX(), enemies.get(i).getY(), projectiles.get(j).getX(), projectiles.get(j).getY())){
					
				}
			}
		}
		
		drawTexture(background, 0, 0);
		
		if(!gameCharacter.getDed()){
			gameCharacter.update(delta);

		}
		
		for(int i = 0; i < projectiles.size(); i++){
			projectiles.get(i).update();
		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update(delta);
		}
		drawTexture(border, 0, 0);
	}
	
	public void drawTexture(Texture newTexture, float newX, float newY){
		
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
	
	public void drawTexture(Texture newTexture, float newX, float newY, float newAngle, int xOffset, int yOffset){
		newTexture.bind();
		
		GL11.glPushMatrix();
		
		GL11.glTranslated(newX + xOffset, newY + yOffset, 0);
		
		GL11.glRotated(newAngle, 0, 0, 1);
		
		GL11.glTranslated(-(newX + xOffset), -(newY + yOffset), 0);
		
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
    	
    	GL11.glPopMatrix();
	}
	
}
