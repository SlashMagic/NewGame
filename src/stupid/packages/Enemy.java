package stupid.packages;

import org.newdawn.slick.opengl.Texture;

public class Enemy {

	float x;
	float y;
	
	float xVel;
	float yVel;
	
	float angle;
	float desiredAngle;
	
	int attackTimer = 0;
	
	Texture enemy;
	
	int energy = 1000;
	
	//boolean ded = false;
	
	World gameWorld;
	
	Character character;
	
	float getX(){
		return x;
	}
	
	float getY(){
		return y;
	}
	
	float getXVel(){
		return xVel;
	}
	
	float getYVel(){
		return yVel;
	}
	
	public void setX(float newX){
		x = newX;
	}
	
	public void setY(float newY){
		y = newY;
	}
	
	public void setXVel(float newXVel){
		xVel = newXVel;
		}
	
	public void setYVel(float newYVel){
		yVel = newYVel;
	}
	
	public Enemy(World newWorld, Character newCharacter, float newX, float newY, float newXVel, float newYVel, Texture newTexture) {
		
		gameWorld = newWorld;
		character = newCharacter;
		x = newX;
		y = newY;
		xVel = newXVel; 
		yVel = newYVel;
		enemy = newTexture;
	}
	
	public void update(int delta){
				
		desiredAngle = (float) Math.toDegrees(Math.atan2((x + 4) - (character.getX() + 4), (y + 4) - (character.getY() + 4))) + 90;
		
		if(desiredAngle > 180){
			float tempAngle = desiredAngle - 180;
			desiredAngle = -180 + tempAngle;
		}

		if(!(angle < desiredAngle+ 1 && angle>desiredAngle- 1) ){
			if(desiredAngle > angle){
				if(Math.abs(desiredAngle - angle) < 180){
					angle += 0.5 * delta * 0.15;
					if(angle > 180){
						angle = -180;
					}
					
				}
				
				else{
					angle -= 0.5 * delta * 0.15;
					if(angle < -180){
						angle = 180;
					}
				}
			}
			
			else{
				if(Math.abs(angle - desiredAngle) < 180){
					angle -= 0.5 * delta * 0.15;
					if(angle < -180){
						angle = 180;
					}
				}
				else{
					angle += 0.5 * delta * 0.15;
					if(angle > 180){
						angle = -180;
					}
				
				}
			}
		}
		
		if(attackTimer > 1000){
			if(angle < desiredAngle + 2 && angle > desiredAngle - 2){
				energy -= 10;
				int newY = (int) -((4) * Math.sin(angle * (Math.PI / 180)));
				int newX = (int) ((4) * Math.cos(angle * (Math.PI / 180)));
				gameWorld.createProjectile(x + 4 + newX, y + 4 + newY, -angle, 10);
				attackTimer = 0;
			}
		}
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
		
		x += xVel;
		y += yVel;
		gameWorld.drawTexture(enemy, x, y, -angle, 4, 4);
		
		attackTimer += delta;
		
		/*if(energy <= 0 || energy > 1000){
			ded = true;
			
		}*/
	}
}
