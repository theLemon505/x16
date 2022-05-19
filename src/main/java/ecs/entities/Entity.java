package ecs.entities;

import ecs.components.Component;
import ecs.components.Transform;
import ecs.scenes.Scene;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    public Scene parentScene;
    public String name;

    public List<Component> components = new ArrayList<Component>();

    public Entity(String name){
        this.name = name;
        addComponent(new Transform());
    }

    public void addComponent(Component component){
        component.parent = this;
        components.add(component);
    }


    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    public <T extends Component> boolean hasComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return true;
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return false;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public abstract void awake();

    public abstract void init();
    public void lowerInit(){
        for(Component component:components){
            component.init();
        }
    }
    public abstract void loop();
    public void lowerLoop(){
        for(Component component:components){
            component.loop();
        }
    }
    public abstract void end();
    public void lowerEnd(){
        for(Component component:components){
            component.end();
        }
    }
}
