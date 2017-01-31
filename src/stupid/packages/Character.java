package stupid.packages;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.opengl.Texture;


public class Character {
	
	float x = 50;
	float y = 50;
	
	float xVel = 0.4f;
	float yVel = 0.4f;
	
	float angle = 90;
	
	Texture sprite_1;
	
	World gameWorld;
	
	int attackTimer = 0;
	
	int energy = 1000;
	
	boolean ded = false;
	
	float getX(){
		return x;
	}
	
	float getY(){
		return y;
	}
	
	boolean getDed(){
		return ded;
	}
	
	public Character(World newWorld, Texture newTexture) {
		
		sprite_1 = newTexture;
		gameWorld = newWorld;
	}
	
	public void update(float delta){
		
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
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_D)){
			
			if(Keyboard.isKeyDown(Keyboard.KEY_W)){
				yVel -= 0.002 * delta;
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
				yVel += 0.002 * delta;
			}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_A)){
				xVel -= 0.002 * delta;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D)){
				xVel += 0.002 * delta;
			}
		}
		else{
	
			if(xVel > 0){
				xVel -= 0.002 * delta;
			}else if(xVel < 0){
				xVel += 0.002 * delta;
			}
			
			if(yVel > 0){
				yVel -= 0.002 * delta;
			} else if(yVel < 0){
				yVel += 0.002 * delta;
			}
			
			if(xVel < -0.01 && xVel > 0.01){
				xVel = 0;
			}
			if(yVel < -0.01 && yVel > 0.01){
				yVel = 0;
			}
		}
		
	
		
		System.out.println(delta);
			x += xVel;
			y += yVel;
		
		
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
			energy -= 10;
			int newY = (int)  -((4) * Math.sin(angle * (Math.PI / 180)));
			int newX = (int)  ((4) * Math.cos(angle * (Math.PI / 180)));
			gameWorld.createProjectile(x + 4 + newX, y + 4 - newY, angle, 10);
			attackTimer = 0;
		}
		
		attackTimer += delta;
		
		gameWorld.drawTexture(sprite_1,x, y, angle, 4, 4);
		
		if(energy <= 0 || energy > 1000){
			ded = true;
		}
		
		
	}
	
	

}
