package gui.elements;

import gui.constraints.Constraint;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {
    public List<Constraint> constraints = new ArrayList<Constraint>();

    public abstract void init();

    public void lowerInit(){
        for(Constraint constraint : constraints){
            constraint.init();
        }
    }

    public abstract void loop();

    public void lowerLoop(){
        for(Constraint constraint : constraints){
            constraint.loop();
        }
    }

    public abstract void draw();

    public abstract void end();

    public void lowerEnd(){
        for(Constraint constraint : constraints){
            constraint.end();
        }
    }
}
