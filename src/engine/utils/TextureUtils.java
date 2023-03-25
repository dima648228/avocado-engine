package engine.utils;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.*;

public class TextureUtils {

    public static int loadAsString(String path, int filter)
    {
        Texture texture = null;

        try {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(path+".png"));
        } catch (FileNotFoundException e) {
            System.err.println("Couldn't find a file at "+path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int textureID = texture.getTextureID();

        return textureID;
    }

}
