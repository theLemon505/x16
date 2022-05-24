package gui;

import java.util.ArrayList;
import java.util.List;

public abstract class Gui {
    public List<Window> windows = new ArrayList<Window>();

    public abstract void init();

    public void addWindow(Window window){
        this.windows.add(window);
    }

    public void lowerInit(){
        for(Window window : windows){
            window.init();
        }
    }

    public abstract void loop();

    public void lowerLoop(){
        for(Window window : windows){
            window.loop();
        }
        render();
    }

    private void render(){
        for(Window window : windows){
            window.draw();
        }
    }

    public abstract void end();

    public void lowerEnd(){
        for(Window window : windows){
            window.end();
        }
    }
}
