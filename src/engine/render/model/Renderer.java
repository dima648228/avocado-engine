package engine.render.model;

import engine.objects.Model;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_BLEND;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.*;

public class Renderer {


    public static void init()
    {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void render(Model model, Camera camera, Transform transform)
    {
        model.getShader().bind();
        model.getShader().setCamera(camera);
        model.getShader().setTransform(transform);

        glActiveTexture(GL13.GL_TEXTURE0);

        GL30.glBindVertexArray(model.getVaoID());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL11.glDrawElements(GL11.GL_TRIANGLES,model.getIndicies().length,GL30.GL_UNSIGNED_INT,0);
        //GL30.glDisableVertexAttribArray(0);
        //GL30.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);

        model.getShader().unbind();
    }

    public static void destroy()
    {
        glDisable(GL_BLEND);
    }
}
