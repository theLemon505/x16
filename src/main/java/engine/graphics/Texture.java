package engine.graphics;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public abstract class Texture {
    public int id;

    public abstract void loadTexture();

    public abstract void load();

    public abstract void unload();
}
