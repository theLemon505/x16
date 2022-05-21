package graphics;

import org.lwjgl.BufferUtils;
import org.w3c.dom.Text;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public abstract class Texture {
    public String path;
    public String[] paths;
    public int id;
    public int xres, yres;

    public Texture(String path){
        this.path = "src/main/resources/textures/" + path;
        loadTexture();
    }

    public Texture(String[] paths){
        this.paths = paths;
    }

    public Texture(int xres, int yres){
        this.xres = xres;
        this.yres = yres;
    }

    public abstract void loadTexture();

    public abstract void load();

    public abstract void unload();
}
