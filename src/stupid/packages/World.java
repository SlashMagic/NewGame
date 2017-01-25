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
	
	public boolean isColliding(float x1, float y1, float x2, float y2 , int distance){
		boolean isColliding = false;
		if(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) < distance){
			isColliding = true;
		}
		return isColliding;
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
	
	public void createProjectile(float x, float y, float angle, int energy){
		projectiles.add(new Projectile(this, x, y, angle, energy, projectile));
	}
	
	public void update(int delta){
		
		
		
		for(int i = 0; i < enemies.size(); i++){
			for(int j = 0; j < projectiles.size(); j++){
				if(isColliding(enemies.get(i).getX() + 4, enemies.get(i).getY() + 4, projectiles.get(j).getX(), projectiles.get(j).getY(), 5) && projectiles.get(j).spawnProtection == 0){
					enemies.get(i).energy += projectiles.get(j).energy;
					projectiles.remove(j);
					
				}
			}
			
			if(enemies.get(i).energy <= 0 || enemies.get(i).energy > 1000){
				enemies.remove(i);
			}
			
		}
		
		for(int i = 0; i < enemies.size(); i++){
			for(int j = i; j < enemies.size(); j++){
				if(i != j && i < enemies.size() && j < enemies.size()){
					
					float x1 = enemies.get(i).getX() + 4;
					float y1 = enemies.get(i).getY() + 4;
					float xVel1 = enemies.get(i).getXVel();
					float yVel1 = enemies.get(i).getYVel();
					float angle1 = (float) Math.atan2(yVel1, xVel1);
					
					float x2 = enemies.get(j).getX() + 4;
					float y2 = enemies.get(j).getY() + 4;
					float xVel2 = enemies.get(j).getXVel();
					float yVel2 = enemies.get(j).getYVel();
					float angle2 = (float) Math.atan2(yVel2, xVel2);
					
					if(isColliding(x1, y1, x2, y2, 8)){
						
						double A = Math.atan2(y1 - y2, x1 - x2);
						
						double u1 = Math.sqrt(Math.pow(xVel1, 2) + Math.pow(yVel1, 2));
						double u2 = Math.sqrt(Math.pow(xVel2, 2) + Math.pow(yVel2, 2));
						
						double v1x = (u1 * Math.cos(angle1 - A));
						double v1y = (u1 * Math.sin(angle1 - A));
						
						double v2x = (u2 * Math.cos(angle2 - A));
						double v2y = (u2 * Math.sin(angle2 - A));
						
						double f1x = ((v1x * (1 - 1)) + (2 * 1 * v2x) / (1 + 1));
						
						double f2x = ((v2x * (1 - 1)) + (2 * 1 * v1x) / (1 + 1));
						
						double v1 = Math.sqrt((Math.pow(f1x, 2) * Math.pow(f1x, 2)) + (v1y * Math.pow(v1y, 2)));
						
						double v2 = Math.sqrt((Math.pow(f2x, 2) * Math.pow(f2x, 2)) + (v2y * Math.pow(v2y, 2)));
						
						double D1 = Math.atan2(v1y, f1x) + A;
						
						double D2 = Math.atan2(v2y, f2x) + A;
						
						float newXVel1 = (float) (v1 * Math.cos(D1));
						float newYVel1 = (float) (v1 * Math.sin(D1));
						
						float newXVel2 = (float) (v2 * Math.cos(D2));
						float newYVel2 = (float) (v2 * Math.sin(D2));
						
						float collisionX = (enemies.get(i).getX() + enemies.get(j).getX()) / 2;		
						float collisionY = (enemies.get(i).getY() + enemies.get(j).getY()) / 2;
						
						System.out.println("v1y : " + v1y + " v2y : " + v2y + " v1 : " + v1 + " v2 : " + v2 + " xVel1 : " + newXVel1 + " yVel1 : " + newYVel1 + " xVel2 : " + newXVel2 + " yVel2 : " + newYVel2) ;
						
						//float iTempX = (2 * 1 * enemies.get(j).getXVel()) / 2;
						//float iTempY = (2 * 1 * enemies.get(j).getYVel()) / 2;
						
						//float jTempX = (2 * 1 * enemies.get(i).getXVel()) / 2;
						//float jTempY = (2 * 1 * enemies.get(i).getYVel()) / 2;
						
						enemies.get(i).setXVel(newXVel1);
						enemies.get(i).setYVel(newYVel1);
						
						enemies.get(i).setX(enemies.get(i).getX() + newXVel1);
						enemies.get(i).setY(enemies.get(i).getY() + newYVel1);
						
						enemies.get(j).setXVel(newXVel2);
						enemies.get(j).setYVel(newYVel2);
						
						enemies.get(j).setX(enemies.get(j).getX() + newXVel2);
						enemies.get(j).setY(enemies.get(j).getY() + newYVel2);
						
						}
					}
			}
		}
		
		drawTexture(background, 0, 0);
		
		if(!gameCharacter.getDed()){
			gameCharacter.update(delta);

		}
		
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).update(delta);
		}
		
		for(int i = 0; i < projectiles.size(); i++){
			projectiles.get(i).update();
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
			GL11.glVertex2f(newX + newTexture.getImageWidth(),newY);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(newX+newTexture.getImageWidth(),newY+newTexture.getImageHeight());
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(newX,newY+newTexture.getImageHeight());	
    		
    	GL11.glEnd();
    	
    	GL11.glPopMatrix();
	}
	
}
