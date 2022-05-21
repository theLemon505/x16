package ecs.scenes;

import ecs.entities.Controller;
import ecs.entities.Renderer;
import ecs.entities.Skybox;
import ecs.entities.TestMap;
import graphics.PassShader;

public class TestScene extends Scene{
    private Controller editorCamera;
    private Skybox skybox;
    private TestMap map;
    private Renderer renderer;
    @Override
    public void init() {
        renderer = new Renderer();
        map = new TestMap();
        editorCamera = new Controller();
        skybox = new Skybox();
        addEntity(renderer);
        addEntity(map);
        addEntity(editorCamera);
        addEntity(skybox);
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
