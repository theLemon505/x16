package ecs.scenes;

import ecs.entities.EditorCamera;
import ecs.entities.Sun;

public class SolarSystem extends Scene{
    private Sun sun = new Sun();
    private EditorCamera camera = new EditorCamera("editorCamera");

    @Override
    public void init() {
        addEntity(camera);
        addEntity(sun);
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
