package graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengles.GLES20.*;
import static org.lwjgl.opengles.GLES20.GL_UNSIGNED_BYTE;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class SkyboxTexture extends Texture{

    public SkyboxTexture(String[] paths){
        super(paths);
        String[] p = new String[6];
        for(int i = 0; i < paths.length; i++){
            p[i] = "src/main/resources/textures/" + paths[i];
        }
        this.paths = p;
        loadTexture();
    }

    @Override
    public void loadTexture(){
        id = GL20.glGenTextures();
        GL20.glBindTexture(GL_TEXTURE_CUBE_MAP, id);

        GL20.glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        GL20.glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        GL20.glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        GL20.glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);


        for(int i = 0; i < paths.length; i++){
            IntBuffer width = BufferUtils.createIntBuffer(1);
            IntBuffer height = BufferUtils.createIntBuffer(1);
            IntBuffer channels = BufferUtils.createIntBuffer(1);
            ByteBuffer image = stbi_load(paths[i], width, height, channels, 0);
            if(image != null){
                GL20.glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X+i, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
            }
            else{
                assert false : "could not load texture from " + path;
            }

            stbi_image_free(image);
        }
    }

    @Override
    public void load(){
        GL20.glBindTexture(GL_TEXTURE_CUBE_MAP, id);
    }

    @Override
    public void unload(){
        GL20.glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }
}
