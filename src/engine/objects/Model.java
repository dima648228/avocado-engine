package engine.objects;

import engine.render.model.Camera;
import engine.render.graphics.Shader;
import engine.render.model.Transform;
import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public abstract class Model {

    private int vaoID, vboID, iboID, tboID, textureID;

    private float[] data, textureCoords;
    private int[] indicies;
    private Shader shader;


    public Model(String vsSrc, String fsSrc)
    {
        this.shader = new Shader(vsSrc, fsSrc);
    }

    public FloatBuffer storeDataInFloatBuffer()
    {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(this.getVertexData().length);
        buffer.put(this.getVertexData());
        buffer.flip();

        return buffer;
    }

    public IntBuffer storeDataInIntBuffer()
    {
        IntBuffer buffer = MemoryUtil.memAllocInt(this.getIndicies().length);
        buffer.put(this.getIndicies());
        buffer.flip();

        return buffer;
    }

    public FloatBuffer storeTextureInFloatBuffer()
    {
        FloatBuffer buffer = MemoryUtil.memAllocFloat(this.getTextureCoords().length);
        buffer.put(this.getTextureCoords());
        buffer.flip();

        return buffer;
    }

    public void create(Camera camera, Transform transform)
    {

        int vaoID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vaoID);

        int iboID = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, iboID);
        IntBuffer intbuffer = MemoryUtil.memAllocInt(indicies.length);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER,intbuffer,GL30.GL_STATIC_DRAW);
        MemoryUtil.memFree(intbuffer);

        int vboID = GL30.glGenBuffers();
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, getVboID());
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        GL30.glBufferData(GL30.GL_ARRAY_BUFFER,buffer,GL30.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(0,3,GL11.GL_FLOAT,false,0,0);
        MemoryUtil.memFree(buffer);
        GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER,vboID);

        GL30.glBindVertexArray(vaoID);

        shader.setCamera(camera);
        shader.setTransform(transform);
    }

    public float[] getVertexData()
    {
        return this.data;
    }

    public int[] getIndicies()
    {
        return this.indicies;
    }

    public void setVertexData(float[] data)
    {
        this.data = data;
    }

    public void setIndicies(int[] data)
    {
        this.indicies = data;
    }

    public Shader getShader()
    {
        return this.shader;
    }

    public float[] getTextureCoords() {
        return textureCoords;
    }

    public void setTextureCoords(float[] textureCoords) {
        this.textureCoords = textureCoords;
    }

    public int getVaoID()
    {
        return  this.vaoID;
    }

    public int getVboID()
    {
        return  this.vboID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public int getTextureID() {
        return this.textureID;
    }

}
