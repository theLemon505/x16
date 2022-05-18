package ecs.scenes;

import ecs.entities.*;
import graphics.Renderer;

public class TestScene extends Scene{

    private Cube cube = new Cube();
    private EditorCamera camera = new EditorCamera("editorCamera");

    @Override
    public void init() {
        addEntity(camera);
        addEntity(cube);
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
