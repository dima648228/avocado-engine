package engine.input;

import engine.Game;

import static org.lwjgl.glfw.GLFW.*;
public class Keyboard {

    public static Keyboard instance;
    private static boolean[] keys = new boolean[348];

    public Keyboard()
    {
        instance = this;
    }

    public static boolean isKeyDown(int keyID)
    {
        return glfwGetKey(Game.getWindow().getWindowID(), keyID) == 1;
    }

    public static boolean isKeyPressed(int keyID)
    {
        return isKeyDown(keyID) && !keys[keyID];
    }

    public static boolean isKeyReleased(int keyID)
    {
        return !isKeyDown(keyID) && keys[keyID];
    }

    public static void handleInput()
    {
        for (int i=0;i<GLFW_KEY_LAST;++i)
        {
            keys[i] = isKeyDown(i);
        }
    }


}
