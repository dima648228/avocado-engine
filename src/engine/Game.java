package engine;

import engine.input.Keyboard;
import engine.input.Mouse;
import engine.interfaces.IGameLogic;
import engine.interfaces.IObject;
import engine.objects.Cube;
import engine.objects.Model;

import engine.render.model.Camera;
import engine.render.model.Renderer;
import engine.render.model.Transform;
import engine.utils.TextureUtils;
import org.joml.AxisAngle4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;


public final class Game implements IGameLogic {

    private final float CAMERA_STEP = 5f;

    private int width, height;
    private String title;
    private long window;
    public static Game instance;
    private static Camera camera;
    private static Transform transform;
    private static GameBuffer gameBuffer;
    public Vector3f cameraInc;

    public Game(int width, int height, String title)
    {
        this.width = width;
        this.height = height;
        this.title = title;
        this.cameraInc = new Vector3f(0f,0f,0f);
        instance = this;

        this.camera = new Camera();
        this.transform = new Transform();

        this.preInit();
    }

    @Override
    public void preInit() {

        /*
        Pre-initialization for the game window.
         */

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwWindowHint(GLFW.GLFW_STENCIL_BITS, 1);
        window = glfwCreateWindow(this.width, this.height, this.title, 0,NULL);
        glfwMakeContextCurrent(this.window);
        glfwSwapInterval(1);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        glfwShowWindow(this.window);

        init();
    }

    @Override
    public void init() {
        GL.createCapabilities();
        glClearColor(0.3f,0.3f,1f,0.5f);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        camera.init();
        camera.setPerspective((float)Math.toRadians(70), this.width/this.height, 0.01f, 1000.0f);
        camera.setPosition(new Vector3f(0, 0, 4));

        Cube model1 = new Cube("res/RectangleVS.glsl","res/RectangleFS.glsl");
        model1.create(camera, transform);

        transform.setPosition(new Vector3f(0,0f,0f));
        transform.setRotation(new Quaternionf(new AxisAngle4f(30f,0.5f,0f,0f)));

        postInit();
    }

    @Override
    public void postInit() {
        loop();
        destroy();
    }

    @Override
    public void loop()
    {
        while (!glfwWindowShouldClose(this.window))
        {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();

            camera.setLookDir();
            handleEvents();

            GL11.glClearColor(0.3f,0.3f,1f,0.5f);

            for(Model cube : gameBuffer.cubes)
            {
                Renderer.render(cube, camera, transform);
            }

            // Debug game header
            glfwSetWindowTitle(window,
                    this.title+" x:"+Math.round(camera.getPosition().x)+" y:"+Math.round(camera.getPosition().y)+" z:"+Math.round(camera.getPosition().z)+"|"
                    +" xOffset: "+Math.toDegrees(camera.getRotation().x)+" yOffset: "+Math.toDegrees(camera.getRotation().y)+" zOffset: "+Math.toDegrees(camera.getRotation().z)
            );

            this.tick();

            // After window updating stuff
            glPopMatrix();
        }
    }

    @Override
    public void handleEvents()
    {
        handleKeyboard();

        // Main event handlers
        Keyboard.handleInput();
        Mouse.handleClick();
        Mouse.SetMousePositionCallback(window);
    }

    public void handleKeyboard()
    {

        handlePositionTranslate();

        if (Keyboard.isKeyPressed(GLFW_KEY_ESCAPE))
        {
            this.destroy();
        }
    }

    public void handlePositionTranslate()
    {

        camera.translate();
    }

    @Override
    public void print(String mes)
    {
        System.out.println(mes);
    }

    @Override
    public void tick()
    {
        glfwSwapBuffers(this.window);
        glfwPollEvents();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        glfwDestroyWindow(window);
        System.exit(1);
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public static Game getWindow()
    {
        return instance;
    }

    public static void appendModel(Cube model)
    {
        gameBuffer.cubes.add(model);
    }

    public long getWindowID()
    {
        return instance.window;
    }

    public static Camera getCamera()
    {
        return camera;
    }

    public static Transform getTransform()
    {
        return transform;
    }
}
