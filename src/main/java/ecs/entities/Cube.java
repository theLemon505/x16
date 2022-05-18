package ecs.entities;

import ecs.components.TextureLayers;
import ecs.components.Transform;
import ecs.components.Vao;
import enums.BufferTypes;
import graphics.Vbo;
import importers.ObjImporter;

public class Cube extends Entity{
    public Cube() {
        super("cube");
    }

    @Override
    public void init() {
        Vao array = ObjImporter.loadData("default_cube.obj");
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
