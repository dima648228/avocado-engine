package engine;

import engine.interfaces.IObject;
import engine.objects.Cube;
import engine.objects.Model;

import java.util.ArrayList;

public class GameBuffer {

    public static ArrayList<Model> cubes = new ArrayList<Model>();

    public static int[] indicies = {
            0,  1,  2,  0,  2, 3
    };

    public static float[] points = {

            0.5f,  0.5f,  0f, // V0
            -0.5f, 0.5f,  0f, // V1
            -0.5f, -0.5f,  0f, // V2
            0.5f,  -0.5f,  0f, // V3
    };

}
