package ecs.scenes;

import ecs.components.Vao;
import ecs.entities.Controller;
import ecs.entities.Renderer;
import ecs.entities.Skybox;
import ecs.entities.TestMap;
import graphics.Shader;
import graphics.SkyboxTexture;
import importers.ObjImporter;

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
