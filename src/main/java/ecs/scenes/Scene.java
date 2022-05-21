package ecs.scenes;

import ecs.entities.Entity;
import ecs.entities.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    public List<Entity> entities = new ArrayList<Entity>();

    public void addEntity(Entity entity){
        entity.parentScene = this;
        entity.awake();
        entities.add(entity);
    }

    public Entity getEntity(String name){
        for(Entity entity:entities){
            if(entity.name == name){
                return entity;
            }
        }
        return null;
    }

    public abstract void init();
    public void lowerInit(){
        for(Entity entity:entities){
            entity.init();
        }
    }
    public abstract void loop();
    public void lowerLoop(){
        for(Entity entity:entities){
            entity.loop();
        }
    }
    public abstract void end();
    public void lowerEnd(){
        for(Entity entity:entities){
            entity.end();
        }
    }
}
