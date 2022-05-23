package graphics;

import io.Display;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class RenderTexture extends Texture{
    private int type;
    public RenderTexture(int type){
        this.type = type;
    }
    @Override
    public void loadTexture() {
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, type, Display.aspectWidth, Display.aspectHeight, 0, type, GL_UNSIGNED_BYTE, 0);
    }

    @Override
    public void load() {
        glTexImage2D(GL_TEXTURE_2D, 0, type, Display.aspectWidth, Display.aspectHeight, 0, type, GL_UNSIGNED_BYTE, 0);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    @Override
    public void unload() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
