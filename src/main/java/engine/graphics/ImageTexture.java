package engine.graphics;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class ImageTexture extends Texture{
    public int id;
    public String path;

    public ImageTexture(String path){
        this.path = "src/main/resources/textures/" + path;
        loadTexture();
    }

    @Override
    public void loadTexture(){
        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        ByteBuffer image = stbi_load(path, width, height, channels, 0);

        if(image != null){
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        }
        else{
            assert false : "could not load texture from " + path;
        }

        stbi_image_free(image);
    }

    @Override
    public void load(){
        glBindTexture(GL_TEXTURE_2D, id);
    }

    @Override
    public void unload(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }
}
