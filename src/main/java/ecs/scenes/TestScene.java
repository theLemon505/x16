package ecs.scenes;

import ecs.components.Vao;
import ecs.entities.Controller;
import ecs.entities.Skybox;
import ecs.entities.TestMap;
import graphics.Shader;
import graphics.SkyboxTexture;
import gui.layers.Window;
import importers.ObjImporter;

public class TestScene extends Scene{
    private Controller editorCamera;
    private Skybox skybox;
    private TestMap map;
    private Window window;

    @Override
    public void init() {
        window = new Window("test");
        map = new TestMap();
        editorCamera = new Controller();
        skybox = new Skybox();
        addEntity(window);
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
