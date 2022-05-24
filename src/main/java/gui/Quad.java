package gui;

import engine.graphics.Vbo;

public class Quad {
    public static float[] vertices = {
            1, -1, 0,
            -1, 1, 0,
            1, 1, 0,
            -1, -1, 0
    };
    public static Vbo vertexBuffer = new Vbo(vertices, 3);

    public static int[] indices = {
            2, 1, 0,
            0, 1, 3
    };
    public static Vbo indexBuffer = new Vbo(indices, 1);

    public static float[] uvs = {
            1, 0,
            0, 1,
            1, 1,
            0, 0
    };
    public static Vbo uvBuffer = new Vbo(uvs, 2);
}
