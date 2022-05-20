package ecs.entities;

import ecs.components.TextureLayers;
import ecs.components.Transform;
import ecs.components.Vao;
import graphics.Shader;
import importers.ObjImporter;
import org.joml.Vector3f;

public class Skybox extends Entity{
    private Vao cube;
    private TextureLayers texture;

    public Skybox() {
        super("skybox");
    }

    @Override
    public void awake() {
        cube = ObjImporter.loadData("default_cube.obj");
        texture = new TextureLayers("front.png", "back.png", "left.png", "right.png", "top.png", "bottom.png");
    }

    @Override
    public void init() {
        getComponent(Transform.class).scale = new Vector3f(100, 100, 100);
        addComponent(texture);
        cube.shader = new Shader("Skybox.glsl");
        addComponent(cube);

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
