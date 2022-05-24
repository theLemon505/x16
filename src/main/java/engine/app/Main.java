package engine.app;

import engine.io.Display;

public class Main {
    public static Display display;
    public static void main(String[] args){
        display = new Display(540, 400, "bolt engine", "0.0.1 -T");
    }
}
