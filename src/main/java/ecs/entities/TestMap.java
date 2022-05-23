package ecs.entities;

import ecs.components.TextureLayers;
import ecs.components.Vao;
import graphics.Shader;
import importers.ObjImporter;

public class TestMap extends Entity{
    private Vao mesh;
    private TextureLayers textures;

    public TestMap() {
        super("test_map");
    }

    @Override
    public void awake() {
        mesh = ObjImporter.loadData("test_map.obj");
        mesh.shader = new Shader("Lit.glsl");
        textures = new TextureLayers("yellow.png");
    }

    @Override
    public void init() {
        addComponent(mesh);
        addComponent(textures);
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
