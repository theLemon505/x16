package ecs.scenes;

import ecs.entities.Camera;
import ecs.entities.Controller;
import ecs.entities.Sun;
import ecs.entities.ships.Shuttle;

public class SolarSystem extends Scene{
    private Sun sun = new Sun();
    private Controller playerController = new Controller();
    @Override
    public void init() {
        addEntity(playerController);
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
