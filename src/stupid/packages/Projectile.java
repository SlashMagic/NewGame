package stupid.packages;

import org.newdawn.slick.opengl.Texture;

public class Projectile {

	float x;
	float y;
	
	float xVel = 1f;
	float yVel = 1f;
	
	float angle;
	
	Texture projectile;
	
	int energy = 5;
	
	World gameWorld;
	
	final float TOTAL_VELOCITY = 1.2f;
	
	float getX(){
		return x;
	}
	
	float getY(){
		return y;
	}
	
	public Projectile(World newWorld, float newX, float newY, float newAngle, Texture newTexture) {
		
		gameWorld = newWorld;
		
		x = newX;
		y = newY;
		
		angle = newAngle;
		
		projectile = newTexture;
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
		
		gameWorld.drawTexture(projectile, x, y, angle + 90, 1, 2);

	}
	
	
}
