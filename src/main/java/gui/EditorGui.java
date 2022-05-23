package gui;

import java.util.ArrayList;
import java.util.List;

public class EditorGui{
    private List<Window> windows = new ArrayList<Window>();

    public void init(){
        for(Window window : windows){
            window.init();
        }
    }

    public void loop(){
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

    public void end(){
        for(Window window : windows){
            window.end();
        }
    }
}
