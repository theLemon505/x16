package engine.graphics;

public class Vbo {
    public Object data;
    public int step;

    public Vbo(float[] data, int step){
        this.data = data;
        this.step = step;
    }

    public Vbo(int[] data, int step){
        this.data = data;
        this.step = step;
    }
}
