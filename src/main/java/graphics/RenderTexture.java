package graphics;

import static org.lwjgl.opengl.GL30.*;

public class RenderTexture extends Texture{
    public int texture = 0;
    int renderBuffer = 0;

    public RenderTexture(int xres, int yres){
        super(xres, yres);
    }

    @Override
    public void loadTexture() {
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);

        // Create the texture to render the data to, and attach it to our framebuffer
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, xres, yres,
                0, GL_RGB, GL_UNSIGNED_BYTE, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D,
                texture, 0);

        // Create renderbuffer store the depth info
        renderBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, xres, yres);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderBuffer);

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            assert false : "Error: Framebuffer is not complete";
        }
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    @Override
    public void load() {
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, xres, yres,
                0, GL_RGB, GL_UNSIGNED_BYTE, 0);
        glBindTexture(GL_TEXTURE_2D, 0);
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, xres, yres);
        glBindRenderbuffer(GL_RENDERBUFFER, 0);
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    @Override
    public void unload() {
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }
}
