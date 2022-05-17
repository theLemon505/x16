package ecs.entities;

import ecs.components.TextureLayers;
import ecs.components.Transform;
import ecs.components.Vao;
import enums.BufferTypes;
import graphics.Vbo;

public class Quad extends Entity{
    public Quad() {
        super("quad");
    }

    private float[] vertices = {
            1,-1,0,
            -1,1,0,
            1,1,0,
            -1,-1,0
    };
    private Vbo vbuffer = new Vbo(vertices, 3);
    private float[] uvs = {
            1, 0,
            0, 1,
            1, 1,
            0, 0
    };
    private Vbo ubuffer = new Vbo(uvs, 2);
    private int[] indices = {
            2, 1, 0,
            0, 1, 3
    };
    private Vbo ibuffer = new Vbo(indices, 1);
    private Vao array = new Vao();

    @Override
    public void init() {
        array.uploadBuffer(vbuffer, BufferTypes.VERTEX_ARRAY_DATA);
        array.uploadBuffer(ibuffer, BufferTypes.INDEX_ARRAY_DATA);
        array.uploadBuffer(ubuffer, BufferTypes.UV_ARRAY_DATA);
        addComponent(array);
        addComponent(new TextureLayers("testTexture.png"));
        lowerInit();
    }

    @Override
    public void loop() {
        lowerLoop();
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
