package stupid.packages;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

public class Main {

	int width = 240;
	int height = 135;
	
	boolean isFullScreen = true;
	
	boolean exit = false;
	
	long lastFPS;
	long lastFrame;
	
	int fps;
	
	public void start(){
		
		try{
			if(isFullScreen){
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			}
			else if(!isFullScreen){
				Display.setDisplayMode(new DisplayMode(width, height));
			}
			
			Display.create();
			Keyboard.create();
			
		} catch (LWJGLException e){
			e.printStackTrace();
			System.out.print("WTF");
			
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
	    GL11.glShadeModel(GL11.GL_SMOOTH);        
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);                    
	 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);                
	    GL11.glClearDepth(1);                                       
	 
	    GL11.glEnable(GL11.GL_BLEND);
	    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			 
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		Display.setVSyncEnabled(true);

		
		World gameWorld = new World();
		
		
		while(!Display.isCloseRequested() && !exit){
			
			updateFPS();
			
			Keyboard.poll();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				exit = true;
			}
			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			
			Color.white.bind();
			
			gameWorld.update(getDelta());
			
			
			Display.update();
			
			Display.sync(144);
			
			
		}
		
		Display.destroy();
		
	}
	
	public void updateFPS(){
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("Current: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	
	public long getTime(){
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta(){
		long time = getTime();
		int delta = (int)(time - lastFrame);
		lastFrame = time;
		if(delta < 0 || delta > 100)
			delta=0;
		return delta;
	}
	
	public static void main(String[] args) {
		Main myMain = new Main();
		myMain.start();
	}

}
