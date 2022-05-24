package launcher;

public class Main {
    public static Display display;
    public static void main(String[] args){
        display = new Display(600, 400, "bolt launcher", "0.0.1 -T");
    }
}
