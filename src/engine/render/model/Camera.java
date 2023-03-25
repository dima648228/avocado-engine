package engine.render.model;

import engine.input.Keyboard;
import engine.input.Mouse;
import org.joml.AxisAngle4f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Vector;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    private Vector3f position;
    private Vector3f rotation;
    private Matrix4f projection;
    private Vector3f lookDir;

    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private final float sensativity = 7;
    private final float camSpeed = 0.1f;
    private float moveAt = 0f;
    public double yaw = 0; public double pitch = 0;

    public Camera() {
        position = new Vector3f();
        rotation = new Vector3f();
        projection = new Matrix4f();
        lookDir = new Vector3f(0,0,-1);
    }

    public void init()
    {

    }

    public void translate()
    {
        if (Keyboard.isKeyDown(GLFW_KEY_W)){
            moveForward(0.1f);
        } if (Keyboard.isKeyDown(GLFW_KEY_S)){
            moveForward(-0.1f);
        } if (Keyboard.isKeyDown(GLFW_KEY_A)){
            moveRight(-0.1f);
        }if (Keyboard.isKeyDown(GLFW_KEY_D)){
            moveRight(0.1f);
        }
    }

    public void moveForward(float distance) {
        float x = distance * (float)Math.sin(Math.toRadians(yaw));
        float z = -distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += x;
        position.z += z;
    }

    public void moveRight(float distance) {
        float x = distance * (float)Math.sin(Math.toRadians(yaw + 90));
        float z = -distance * (float)Math.cos(Math.toRadians(yaw + 90));
        position.x += x;
        position.z += z;
    }

    public Matrix4f updateViewMatrix() {
        return Transform.updateGenericViewMatrix(position, rotation,projection);
    }

    public Matrix4f getTransformation() {
        Matrix4f returnValue = new Matrix4f();

        returnValue.rotate(1f,rotation.x,rotation.y,rotation.z);
        returnValue.translate(position.mul(-1, new Vector3f()));

        return returnValue;
    }

    public void setOrthographic(float left, float right, float top, float bottom) {
        projection.setOrtho2D(left, right, bottom, top);
    }

    public void setPerspective(float fov, float aspectRatio, float zNear, float zFar) {
        projection.setPerspective(fov, aspectRatio, zNear, zFar);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setLookDir()
    {

        yaw = Mouse.getMouseX() - lastMouseX;
        pitch = Mouse.getMouseY() - lastMouseY;

        lastMouseX = Mouse.getMouseX();
        lastMouseY = Mouse.getMouseY();

        rotation.x+=Math.toRadians(pitch/sensativity);
        rotation.y+=Math.toRadians(yaw/sensativity);

        if (Keyboard.isKeyDown(GLFW_KEY_R))rotation = new Vector3f(0f,0f,0f);
    }

    public Matrix4f getProjection() {
        return projection;
    }
}