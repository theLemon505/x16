package ecs.scenes;

import ecs.entities.EditorCamera;
import ecs.entities.Entity;
import ecs.entities.Quad;
import ecs.entities.Triangle;
import graphics.Renderer;

public class TestScene extends Scene{

    private Quad quad = new Quad();
    private EditorCamera camera = new EditorCamera("editorCamera");

    @Override
    public void init() {
        addEntity(camera);
        addEntity(quad);
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
