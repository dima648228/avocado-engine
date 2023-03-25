package engine.objects;

import engine.Game;
import engine.interfaces.IObject;

public class Cube extends Model {
    public Cube(String vsSrc, String fsSrc) {
        super(vsSrc, fsSrc);
        this.setIndicies(new int[]{
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                4, 0, 3, 5, 4, 3,
                // Right face
                3, 2, 7, 5, 3, 7,
                // Left face
                6, 1, 0, 6, 0, 4,
                // Bottom face
                2, 1, 6, 2, 6, 7,
                // Back face
                7, 6, 4, 7, 4, 5,
        });
        this.setVertexData(new float[] {
                // VO
                -0.5f,  0.5f,  0.5f,
                // V1
                -0.5f, -0.5f,  0.5f,
                // V2
                0.5f, -0.5f,  0.5f,
                // V3
                0.5f,  0.5f,  0.5f,
                // V4
                -0.5f,  0.5f, -0.5f,
                // V5
                0.5f,  0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
        });

        /*
        this.setTextureCoords(new float[]{
                0,1,
                1,1,
                1,0,
                0,0
        });

        this.setTextureID(textureID);
        */

        Game.appendModel(this);
        this.create(Game.getCamera(),Game.getTransform());
        System.out.println("Aboba");
    }
}
