package stupid.packages;

import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Font {

	private Texture[] font = new Texture[52];
	
	public Font() {
		loadFont();
	}

	public void loadFont(){
		for(int i = 0; i < 52; i++){
			font[i] = loadTexture("res/font/font_" + i + ".png");
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
	
public void drawString(String newString, int newX, int newY, int newSize){
		
		newString = newString.toLowerCase();
		
		for(int i = 0; i < newString.length(); i ++){
			
			float offset = (float) ((i * 1.3 * newSize) + newX);
			
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

	public void drawTexture(Texture newTexture, float newX, int newY, int newSize){
		
		newTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		
			GL11.glTexCoord2f(0,0);
	   		GL11.glVertex2f(newX,newY);
	   		GL11.glTexCoord2f(1,0);
			GL11.glVertex2f(newX + (3 * newSize), newY);
			GL11.glTexCoord2f(1,1);
			GL11.glVertex2f(newX + (3 * newSize), (newY + (5 * 1.3f *newSize)));
			GL11.glTexCoord2f(0,1);
			GL11.glVertex2f(newX , (newY + (5 * 1.3f * newSize)));	
			
		GL11.glEnd();
		
	}
	
}
