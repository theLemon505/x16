package ecs.entities.celestials;

import ecs.entities.Entity;

public class Planet extends Entity {
    public Planet(String name) {
        super(name);
    }

    @Override
    public void awake() {

    }

    @Override
    public void init() {
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
