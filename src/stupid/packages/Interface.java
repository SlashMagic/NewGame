package stupid.packages;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

public class Interface {

	World gameWorld;
	
	Character gameCharacter;
	
	Texture button;
	Texture energyOutline;
	Texture mouseCursor;
	Texture sprite_1;
	Texture enemy;
	
	boolean help = false;
	
	ArrayList<Element> mainElements = new ArrayList<Element>(); 
	ArrayList<Element> helpElements = new ArrayList<Element>();
	ArrayList<Element> headsUp = new ArrayList<Element>();
	ArrayList<Element> lostElements = new ArrayList<Element>();
	
	float mouseX = 116;
	float mouseY = 63;
	
	float mouseXStep = 0;
	float mouseYStep = 0;
	
	public Interface(World newWorld, Character newCharacter, Texture newButton, Texture newEnergy, Texture newMouse, Texture newSprite, Texture newEnemy) {
		gameWorld = newWorld;
		
		gameCharacter = newCharacter;
		
		button = newButton;
		
		energyOutline = newEnergy;
		
		mouseCursor = newMouse;
		
		sprite_1 = newSprite;
		
		enemy = newEnemy;
		
		Mouse.setGrabbed(true);
		
		loadInterface();
	}
	
	public void loadInterface(){
		// World, this, Texture, x, y, width, height, clickable, text, text size, action.
		mainElements.add(new Element(gameWorld, this, button, 20, 10, 400, 72, false, "Energy Orbs", 4, 0));
		mainElements.add(new Element(gameWorld, this, button, 92, 54, 112, 36, true, "Play", 2, 1));
		mainElements.add(new Element(gameWorld, this, button, 92, 82, 112, 36, true, "Help", 2, 2));
		mainElements.add(new Element(gameWorld, this, button, 92, 110, 112, 36, true, "Exit", 2, 3));
		
		helpElements.add(new Element(gameWorld, this, button, 10, 10, 440, 18, false, "This is your Character,     use wasd to control it.", 1, 0));
		helpElements.add(new Element(gameWorld, this, button, 10, 23, 440, 18, false, "These are your Enemies,     they will try to kill you!", 1, 0));
		helpElements.add(new Element(gameWorld, this, button, 10, 36, 440, 18, false, "To kill an enemy, use the arrow keys to aim, and the", 1, 0));
		helpElements.add(new Element(gameWorld, this, button, 10, 49, 440, 18, false, "space bar to shoot projectiles. Projectiles cost", 1, 0));
		helpElements.add(new Element(gameWorld, this, button, 10, 62, 440, 18, false, "energy to shoot, and that energy is transfered to what", 1, 0));
		helpElements.add(new Element(gameWorld, this, button, 10, 75, 440, 18, false, "they hit. If you shoot too much, you will run out of", 1, 0));
		helpElements.add(new Element(gameWorld, this, button, 10, 88, 440, 18, false, "energy and lose, but if you get hit and gain too much", 1, 0));
		helpElements.add(new Element(gameWorld, this, button, 10, 101, 440, 18, false, "energy, you will explode!", 1, 0));
		helpElements.add(new Element(gameWorld, this, button, 10, 114, 78, 18, true, "Back", 1, 4));
		
		headsUp.add(new Element(gameWorld, this, energyOutline, 0, 2, 48, 24, false, "", 1, 0));
		
		lostElements.add(new Element(gameWorld, this, button, 10, 114, 78, 18, true, "Back", 1, 4));
		lostElements.add(new Element(gameWorld, this, button, 20, 10, 400, 72, false, "You Lose!", 4, 0));
	}
	
	public void update(){
		
		if(!gameWorld.play){
			if(!help){
				if(!gameWorld.lost){
					for(int i = 0; i < mainElements.size(); i ++){
						mainElements.get(i).update();
					}
					
					drawMouse();
				}
				else if(gameWorld.lost){
					
					for(int i = 0; i < lostElements.size(); i ++){
						lostElements.get(i).update();
					}
					
					drawMouse();
				}
			}
			else if(help){
				for(int i = 0; i < helpElements.size(); i ++){
					helpElements.get(i).update();
				}
				
				gameWorld.drawTexture(sprite_1, 114, 10, 0, 4, 4);
				gameWorld.drawTexture(enemy, 114, 23, 0, 4, 4);
				
				drawMouse();
			}
		}
		else if(gameWorld.play){
		
			float energy = gameCharacter.getEnergy() / 100f;
			gameWorld.drawElement(button, 9, 5, (int) (energy * 60), 18);
			
			for(int i = 0; i < headsUp.size(); i ++){
				headsUp.get(i).update();
			}
			
			
		}
	}
	
	public boolean isClicked(int x, int y, int width, int height){
		
		if(Mouse.isButtonDown(0) && mouseX > x && mouseY > y && mouseX < (x + width) && mouseY < (y + height)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public void drawMouse(){
		
		mouseXStep = Mouse.getDX() * 0.1f;
		mouseYStep = Mouse.getDY() * 0.1f;
		
		mouseX += mouseXStep;
		mouseY += -mouseYStep;
		
		if(mouseX < 0){
			mouseX = 0;
		}
		if(mouseY < 0){
			mouseY = 0;
		}
		if(mouseX > 233){
			mouseX = 233;
		}
		if(mouseY > 128){
			mouseY = 128;
		}
		
		gameWorld.drawTexture(mouseCursor, mouseX, mouseY);
		
	}

}
