package engine.render.graphics;

import engine.render.model.Camera;
import engine.render.model.Transform;
import org.newdawn.slick.opengl.Texture;

import java.io.*;
import java.util.*;

import static org.lwjgl.opengl.GL20.*;
import static org.newdawn.slick.util.ResourceLoader.getResourceAsStream;

public class Shader {

    public int id;

    private int uniMatProjection, uniMatTransformWorld, uniMatTransformObject;

    public Shader(String vsSrc,String fsSrc)
    {
        Map<Integer, String> shaderSources = new HashMap<Integer, String>(2);
        shaderSources.put(1, this.readFile(vsSrc));
        shaderSources.put(2, this.readFile(fsSrc));

        this.compile(shaderSources);
    }

    public void compile(Map<Integer, String> shaderSources)
    {
        int program = glCreateProgram();

        List<Integer> shaderIds = new ArrayList<Integer>();
        int shaderIdxs = 1;

        for (int i=0;i<shaderSources.size();i++)
        {
            int type = i == 0 ? GL_VERTEX_SHADER : i == 1 ? GL_FRAGMENT_SHADER : -1;
            String source = shaderSources.get(shaderIdxs);

            int shader = glCreateShader(type);
            glShaderSource(shader,source);
            glCompileShader(shader);

            int isCompiled = 0;
            
            isCompiled = glGetShaderi(shader, GL_COMPILE_STATUS);

            if (isCompiled == GL_FALSE)
            {
                int max_length = 0;
                max_length = glGetShaderi(shader,GL_INFO_LOG_LENGTH);

                String infoLog = "";
                infoLog = glGetShaderInfoLog(shader, max_length);
                glDeleteShader(shader);

                String st = type==0?"VertexShader":"Fragment Shader";
                System.err.println("Unable to compile "+st+": "+infoLog);
                System.exit(-1);
            }

            glAttachShader(program,shader);
            shaderIdxs++;
        }

        glBindAttribLocation(program, 1, "textureCoords");
        glLinkProgram(program);

        int isLinked = 0;
        isLinked = glGetProgrami(program, GL_COMPILE_STATUS);

        for(int shaderId : shaderIds)
        {
            glDetachShader(program,shaderId);
        }

        uniMatProjection = glGetUniformLocation(program,"cameraProjection");
        uniMatTransformWorld = glGetUniformLocation(program,"transformWorld");
        uniMatTransformObject = glGetUniformLocation(program,"transformObject");

        this.id = program;
    }

    public void setCamera(Camera camera)
    {
        if (uniMatProjection != -1)
        {
            float matrix[] = new float[16];
            camera.getProjection().get(matrix);
            glUniformMatrix4fv(uniMatProjection,false,matrix);
        }
        if (uniMatTransformWorld != -1)
        {
            float matrix[] = new float[16];
            camera.getTransformation().get(matrix);
            glUniformMatrix4fv(uniMatTransformWorld,false,matrix);
        }
    }

    public  void setTransform(Transform transform)
    {
        if (uniMatTransformObject != -1)
        {
            float matrix[] = new float[16];
            transform.getTransformation().get(matrix);
            glUniformMatrix4fv(uniMatTransformObject,false,matrix);
        }
    }

    public String readFile(String file)
    {
        boolean appendSlashes = false;
        boolean returnInOneLine = false;
        StringBuilder shaderSource = new StringBuilder();
        try
        {
            InputStream in = getResourceAsStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while((line = reader.readLine())!=null)
            {
                shaderSource.append(line);

                if(appendSlashes)    shaderSource.append("//");
                if(!returnInOneLine) shaderSource.append("\n");
            }
            reader.close();

            return shaderSource.toString();
        }
        catch(IOException e)
        {
            System.out.println("This file '" + file + "' cound be read!");
            e.printStackTrace();
            Runtime.getRuntime().exit(-1);
        }

        return "[Reading Error]: This file '" + file + "' cound be read!";
    }

    public void bind()
    {
        glUseProgram(this.id);
    }

    public void unbind()
    {
        glUseProgram(0);
    }

}
