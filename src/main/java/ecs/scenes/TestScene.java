package ecs.scenes;

import ecs.entities.EditorCamera;
import ecs.entities.Renderer;
import ecs.entities.Skybox;
import ecs.entities.TestMap;

public class TestScene extends Scene{
    private EditorCamera editorCamera;
    private Skybox skybox;
    private TestMap map;
    public Renderer currentRenderer;
    @Override
    public void init() {
        currentRenderer = new Renderer();
        map = new TestMap();
        editorCamera = new EditorCamera();
        skybox = new Skybox();
        addEntity(currentRenderer);
        addEntity(map);
        addEntity(editorCamera);
        addEntity(skybox);
        lowerInit();
    }

    @Override
    public void loop() {
        lowerLoop();
        currentRenderer.render();
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
