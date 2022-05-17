package ecs.entities;

import ecs.components.Transform;
import ecs.components.Vao;
import enums.BufferTypes;
import graphics.Vbo;

public class Triangle extends Entity{

    public Triangle() {
        super("triangle");
    }

    private float[] vertices = {
            0,1,0,
            1,-1,0,
            -1,-1,0
    };
    private Vbo vbuffer = new Vbo(vertices, 3);
    private int[] indices = {
            0,1,2
    };
    private Vbo ibuffer = new Vbo(indices, 1);
    private Vao array = new Vao();

    @Override
    public void init() {
        array.uploadBuffer(vbuffer, BufferTypes.VERTEX_ARRAY_DATA);
        array.uploadBuffer(ibuffer, BufferTypes.INDEX_ARRAY_DATA);
        addComponent(array);
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
