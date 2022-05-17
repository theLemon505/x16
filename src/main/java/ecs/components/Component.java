package ecs.components;

import ecs.entities.Entity;

public abstract class Component {
    public Entity parent;
    public abstract void init();
    public abstract void loop();
    public abstract void end();
}
