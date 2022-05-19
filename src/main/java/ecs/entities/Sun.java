package ecs.entities;

import ecs.components.TextureLayers;
import ecs.components.Transform;
import ecs.components.Vao;
import importers.ObjImporter;
import org.joml.Vector3f;

public class Sun extends Entity{
    private Vao mesh;
    private TextureLayers textures;

    public Sun() {
        super("sun");
    }

    @Override
    public void awake() {

    }

    @Override
    public void init() {
        getComponent(Transform.class).scale = new Vector3f(100, 100, 100);

        mesh = ObjImporter.loadData("cube_sphere.obj");
        textures = new TextureLayers("yellow.png");

        addComponent(mesh);
        addComponent(textures);

        lowerInit();
    }

    @Override
    public void loop() {
        getComponent(Transform.class).rotation.y += 0.001;

        lowerLoop();
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
