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
	Texture energyOutline;
	Texture button;
	Texture mouseCursor;
	
	Texture[] font = new Texture[52];
	
	int level = 1;
	
	Character gameCharacter;
	
	Interface gameInterface;
	
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	ArrayList<Enemy> enemies = new ArrayList<Enemy>(); 
	
	boolean play = false;
	boolean lost = false;
	
	public World(){
		
		loadData();
		
		for(int i = 0; i < 52; i++){
			font[i] = loadTexture("res/font/font_" + i + ".png");
		}
		
		gameCharacter = new Character(this, sprite_1);
		
		gameInterface = new Interface(this, gameCharacter, button, energyOutline, mouseCursor, sprite_1, enemy);
	}
	
	public void loadData(){
		
		try{
			for(int i = 0; i > 42; i++){
				String path = "res/font/font_" + i + ".png";
				font[i] = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);
			}
			background = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/background_1.png"), GL11.GL_NEAREST);
			border = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/border_1.png"), GL11.GL_NEAREST);
			button = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/button.png"), GL11.GL_NEAREST);
			enemy = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/sprites/sprite_2.png"), GL11.GL_NEAREST);
			energyOutline = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/energyOutline.png"), GL11.GL_NEAREST);
			mouseCursor = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/bg/cursor.png"), GL11.GL_NEAREST);
			projectile = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/projectiles/laser_1.png"), GL11.GL_NEAREST);
			sprite_1 = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/sprites/sprite_1.png"), GL11.GL_NEAREST);
			// load texture in here
			
		}
		catch (IOException e){
			e.printStackTrace();
		}	
	}
	
	public Texture loadTexture(String path){
		
		Texture newTexture = null;
		
		
		
		try{
			newTexture = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream(path), GL11.GL_NEAREST);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return newTexture;
		
	}
	
	public boolean isColliding(float x1, float y1, float x2, float y2 , int distance){
		boolean isColliding = false;
		if(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)) < distance){
			isColliding = true;
		}
		return isColliding;
	}
	
	public void createProjectile(float x, float y, float angle, int energy){
		projectiles.add(new Projectile(this, x, y, angle, energy, projectile));
	}
	
	public void update(int delta){
		
		if(lost){
			
			level = 1;
			
			for(int i = 0; i < enemies.size(); i ++){
				enemies.remove(i);
			}
			for(int i = 0; i < projectiles.size(); i ++){
				projectiles.remove(i);
			}
			
			gameCharacter.setEnergy(100);
		}
		
		if(play){
			
			if(enemies.size() == 0 && projectiles.size() < 10){
				Random rnd = new Random();
				
				int numOfEnemies = 3 + level;
				
				for(int i = 0; i < numOfEnemies; i ++){
					int newX = rnd.nextInt(200) + 40;
					int newY = rnd.nextInt(95) + 40;
					float newXVel = rnd.nextFloat();
					float newYVel = rnd.nextFloat();
					enemies.add(new Enemy(this, gameCharacter, newX, newY, newXVel, newYVel, enemy));
				}
				level++;
			}
			
			for(int i = 0; i < projectiles.size(); i++){
				if(isColliding(gameCharacter.getX() + 4, gameCharacter.getY() + 4, projectiles.get(i).getX(), projectiles.get(i).getY(), 5) && projectiles.get(i).spawnProtection == 0){
					gameCharacter.setEnergy(gameCharacter.getEnergy() + projectiles.get(i).getEnergy());
					projectiles.remove(i);
				}
			}
			
			for(int i = 0; i < enemies.size(); i++){
				for(int j = 0; j < projectiles.size(); j++){
					if(isColliding(enemies.get(i).getX() + 4, enemies.get(i).getY() + 4, projectiles.get(j).getX(), projectiles.get(j).getY(), 5) && projectiles.get(j).spawnProtection == 0){
						enemies.get(i).energy += projectiles.get(j).energy;
						projectiles.remove(j);
						
					}
				}
				
				if(enemies.get(i).energy <= 0 || enemies.get(i).energy > 100){
					enemies.remove(i);
				}
				
			}
			
			for(int i = 0; i < enemies.size(); i++){
				if(isColliding(enemies.get(i).getX() + 4, enemies.get(i).getY() + 4, gameCharacter.getX() + 4, gameCharacter.getY() + 4, 8)){
					do2DCircleCollision(528, i);
					enemies.get(i).energy -= 1;
					gameCharacter.setEnergy(gameCharacter.getEnergy() - 1);
				}
				for(int j = i; j < enemies.size(); j++){
					if(i != j && i < enemies.size() && j < enemies.size()){
						if(isColliding(enemies.get(i).getX() + 4, enemies.get(i).getY() + 4, enemies.get(j).getX() + 4, enemies.get(j).getY() + 4, 8)){
							do2DCircleCollision(i, j);
							enemies.get(i).energy -= 1;
							enemies.get(j).energy -= 1;
						}
					}
				}
			}
			
			for(int i = 0; i < projectiles.size(); i++){
				projectiles.get(i).update();
			}
			
			for(int i = 0; i < enemies.size(); i++){
				enemies.get(i).update(delta);
			}
			
			if(!gameCharacter.getDed()){
				gameCharacter.update(delta);
	
			}
		}
		gameInterface.update();
		
		drawTexture(border, 0, 0);
	}
	public void do2DCircleCollision(int i, int j){
		
		float x1;
		float y1;
		float xVel1;
		float yVel1;
		float angle1;
		
		if(i == 528){

			x1 = gameCharacter.getX() + 4;
			y1 = gameCharacter.getY() + 4;
			xVel1 = gameCharacter.getXVel();
			yVel1 = gameCharacter.getYVel();
			angle1 = (float) Math.atan2(yVel1, xVel1);
			
		}else{
			x1 = enemies.get(i).getX() + 4;
			y1 = enemies.get(i).getY() + 4;
			xVel1 = enemies.get(i).getXVel();
			yVel1 = enemies.get(i).getYVel();
			angle1 = (float) Math.atan2(yVel1, xVel1);
		}
		float x2 = enemies.get(j).getX() + 4;
		float y2 = enemies.get(j).getY() + 4;
		float xVel2 = enemies.get(j).getXVel();
		float yVel2 = enemies.get(j).getYVel();
		float angle2 = (float) Math.atan2(yVel2, xVel2);
		
		double collisionAngle = Math.atan2(y2 - y1, x2 - x1);
		
		double magnitude1 = Math.sqrt(xVel1 * xVel1 + yVel1 * yVel1);
		double magnitude2 = Math.sqrt(xVel2*xVel2 + yVel2 * yVel2);
		
		double newXVel1 = magnitude1 * Math.cos(angle1 - collisionAngle);
		double newYVel1 = magnitude1 * Math.sin(angle1 - collisionAngle);
		
		double newXVel2 = magnitude2 * Math.cos(angle2 - collisionAngle);
		double newYVel2 = magnitude2 * Math.sin(angle2 - collisionAngle);
		
		double finalXVel1 = newXVel2;
		double finalYVel1 = newYVel1;
		
		double finalXVel2 = newXVel1;
		double finalYVel2 = newYVel2;
		
		xVel1 = (float) (Math.cos(collisionAngle) * finalXVel1 + Math.cos(collisionAngle + Math.PI / 2) * finalYVel1);
		yVel1 = (float) (Math.sin(collisionAngle) * finalXVel1 + Math.sin(collisionAngle + Math.PI/2) * finalYVel1);
		
		xVel2 = (float) (Math.cos(collisionAngle) * finalXVel2 + Math.cos(collisionAngle + Math.PI / 2) * finalYVel2);
		yVel2 = (float) (Math.sin(collisionAngle) * finalXVel2 + Math.sin(collisionAngle + Math.PI / 2) * finalYVel2);
		
		//float collisionX = (enemies.get(i).getX() + enemies.get(j).getX()) / 2;		
		//float collisionY = (enemies.get(i).getY() + enemies.get(j).getY()) / 2;
		
		if(i == 528){
			
			gameCharacter.setXVel(xVel1);
			gameCharacter.setYVel(xVel1);
			
			
		}else{
			
		enemies.get(i).setXVel(xVel1);
		enemies.get(i).setYVel(yVel1);
		
		enemies.get(i).setX(enemies.get(i).getX() + xVel1);
		enemies.get(i).setY(enemies.get(i).getY() + yVel1);
		
		}
		
		enemies.get(j).setXVel(xVel2);
		enemies.get(j).setYVel(yVel2);
		
		enemies.get(j).setX(enemies.get(j).getX() + xVel2);
		enemies.get(j).setY(enemies.get(j).getY() + yVel2);
	}
	
	public void drawString(String newString, int newX, int newY, int newSize){
		
		newString = newString.toLowerCase();
		
		for(int i = 0; i < newString.length(); i ++){
			
			float offset = (float) ((i * 3.9 * newSize) + newX);
			
			char c = newString.charAt(i);
			
			if(c == '0')
				drawTexture(font[0], offset, newY, newSize);
			if(c == '1')
				drawTexture(font[1], offset, newY, newSize);
			if(c == '2')
				drawTexture(font[2], offset, newY, newSize);
			if(c == '3')
				drawTexture(font[3], offset, newY, newSize);
			if(c == '4')
				drawTexture(font[4], offset, newY, newSize);
			if(c == '5')
				drawTexture(font[5], offset, newY, newSize);
			if(c == '6')
				drawTexture(font[6], offset, newY, newSize);
			if(c == '7')
				drawTexture(font[7], offset, newY, newSize);
			if(c == '8')
				drawTexture(font[8], offset, newY, newSize);
			if(c == '9')
				drawTexture(font[9], offset, newY, newSize);
			if(c == 'a')
				drawTexture(font[10], offset, newY, newSize);
			if(c == 'b')
				drawTexture(font[11], offset, newY, newSize);
			if(c == 'c')
				drawTexture(font[12], offset, newY, newSize);
			if(c == 'd')
				drawTexture(font[13], offset, newY, newSize);
			if(c == 'e')
				drawTexture(font[14], offset, newY, newSize);
			if(c == 'f')
				drawTexture(font[15], offset, newY, newSize);
			if(c == 'g')
				drawTexture(font[16], offset, newY, newSize);
			if(c == 'h')
				drawTexture(font[17], offset, newY, newSize);
			if(c == 'i')
				drawTexture(font[18], offset, newY, newSize);
			if(c == 'j')
				drawTexture(font[19], offset, newY, newSize);
			if(c == 'k')
				drawTexture(font[20], offset, newY, newSize);
			if(c == 'l')
				drawTexture(font[21], offset, newY, newSize);
			if(c == 'm')
				drawTexture(font[22], offset, newY, newSize);
			if(c == 'n')
				drawTexture(font[23], offset, newY, newSize);
			if(c == 'o')
				drawTexture(font[24], offset, newY, newSize);
			if(c == 'p')
				drawTexture(font[25], offset, newY, newSize);
			if(c == 'q')
				drawTexture(font[26], offset, newY, newSize);
			if(c == 'r')
				drawTexture(font[27], offset, newY, newSize);
			if(c == 's')
				drawTexture(font[28], offset, newY, newSize);
			if(c == 't')
				drawTexture(font[29], offset, newY, newSize);
			if(c == 'u')
				drawTexture(font[30], offset, newY, newSize);
			if(c == 'v')
				drawTexture(font[31], offset, newY, newSize);
			if(c == 'w')
				drawTexture(font[32], offset, newY, newSize);
			if(c == 'x')
				drawTexture(font[33], offset, newY, newSize);
			if(c == 'y')
				drawTexture(font[34], offset, newY, newSize);
			if(c == 'z')
				drawTexture(font[35], offset, newY, newSize);
			if(c == '.')
				drawTexture(font[36], offset, newY, newSize);
			if(c == '!')
				drawTexture(font[37], offset, newY, newSize);
			if(c == '?')
				drawTexture(font[38], offset, newY, newSize);
			if(c == ',')
				drawTexture(font[39], offset, newY, newSize);
			if(c == ':')
				drawTexture(font[40], offset, newY, newSize);
			if(c == ';')
				drawTexture(font[41], offset, newY, newSize);
			if(c == '+')
				drawTexture(font[42], offset, newY, newSize);
			if(c == '-')
				drawTexture(font[43], offset, newY, newSize);
			if(c == '*')
				drawTexture(font[44], offset, newY, newSize);
			if(c == '%')
				drawTexture(font[45], offset, newY, newSize);
			if(c == '^')
				drawTexture(font[46], offset, newY, newSize);
			if(c == '[')
				drawTexture(font[47], offset, newY, newSize);
			if(c == ']')
				drawTexture(font[48], offset, newY, newSize);
			if(c == '|')
				drawTexture(font[49], offset, newY, newSize);
			if(c == ' ')
				drawTexture(font[50], offset, newY, newSize);
			if(c == '=')
				drawTexture(font[51], offset, newY, newSize);
		}
		
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
	
	public void drawTexture(Texture newTexture, float newX, float newY, float newScale){
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			
		GL11.glTexCoord2f(0,0);
   		GL11.glVertex2f(newX,newY);
   		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(newX + (newTexture.getTextureWidth() * newScale),newY);
		GL11.glTexCoord2f(1,1); 
		GL11.glVertex2f(newX + (newTexture.getTextureWidth() * newScale), newY + (newTexture.getTextureHeight() * newScale));
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(newX, newY + (newTexture.getTextureHeight() * newScale));	
    		
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
	
	public void drawElement(Texture newTexture, int newX, int newY, int newWidth, int newHeight){
		
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			
		GL11.glTexCoord2f(0,0);
   		GL11.glVertex2f(newX,newY);
   		GL11.glTexCoord2f(1,0);
		GL11.glVertex2f(newX + newWidth, newY);
		GL11.glTexCoord2f(1,1); 
		GL11.glVertex2f(newX + newWidth, newY + newHeight);
		GL11.glTexCoord2f(0,1);
		GL11.glVertex2f(newX, newY + newHeight);	
    		
    	GL11.glEnd();
		
	}
	
}
