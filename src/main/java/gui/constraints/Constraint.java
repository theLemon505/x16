package gui.constraints;

import gui.elements.Element;

public abstract class Constraint {
    public Element parent;

    public abstract void init();

    public abstract void loop();

    public abstract void end();
}
