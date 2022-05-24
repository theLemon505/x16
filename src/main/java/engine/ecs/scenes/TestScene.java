package engine.ecs.scenes;

import engine.ecs.entities.Controller;
import engine.ecs.entities.Renderer;
import engine.ecs.entities.Skybox;
import engine.ecs.entities.TestMap;

public class TestScene extends Scene{
    private Controller editorCamera;
    private Skybox skybox;
    private TestMap map;
    public Renderer currentRenderer;
    @Override
    public void init() {
        currentRenderer = new Renderer();
        map = new TestMap();
        editorCamera = new Controller();
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
