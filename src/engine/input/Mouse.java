package engine.input;

import engine.Game;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse {
    public static Mouse instance;
    private static boolean[] buttons = new boolean[7];

    private static double mouseX, mouseY;

    public Mouse()
    {
        instance = this;
    }

    public static boolean isButtonDown(int buttonID)
    {
        return glfwGetMouseButton(Game.getWindow().getWindowID(), buttonID) == 1;
    }

    public static boolean isButtonPressed(int buttonID)
    {
        return isButtonDown(buttonID) && !buttons[buttonID];
    }

    public static boolean isButtonReleased(int buttonID)
    {
        return !isButtonDown(buttonID) && buttons[buttonID];
    }

    public static void handleClick()
    {
        for (int i=0;i<GLFW_MOUSE_BUTTON_LAST;++i)
        {
            buttons[i] = isButtonDown(i);
        }
    }

    public static void SetMousePositionCallback(long windowID) {
        glfwSetCursorPosCallback(windowID, new GLFWCursorPosCallbackI() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        });
    }

    public static double getMouseX()
    {
        return mouseX;
    }

    public static double getMouseY()
    {
        return mouseY;
    }
}
