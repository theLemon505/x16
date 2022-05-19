package ecs.entities.ships;

import ecs.components.TextureLayers;
import ecs.components.Transform;
import ecs.components.Vao;
import ecs.entities.Entity;
import importers.ObjImporter;
import org.joml.Vector3f;

public class Shuttle extends Ship {
    private Vao mesh;
    private TextureLayers textures;

    public Shuttle(String name) {
        super(name);
    }

    @Override
    public void awake() {

    }

    @Override
    public void init() {
        getComponent(Transform.class).rotation.y = 90;
        getComponent(Transform.class).scale = new Vector3f(1f, 1f, 1f);
        mesh = ObjImporter.loadData("resonante.obj");
        textures = new TextureLayers("resonante.png");
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
