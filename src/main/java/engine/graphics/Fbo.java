package engine.graphics;

import engine.io.Display;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL14.GL_DEPTH_COMPONENT32;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.GL_FRAMEBUFFER_COMPLETE;

public class Fbo {
    private IntBuffer buffer;
    public int id;
    private int renderBuffer;
    private HashMap<Integer, RenderTexture> renderTargets = new HashMap<Integer, RenderTexture>();

    public void addRenderTarget(int slot, int type){
        RenderTexture target = new RenderTexture(type);
        renderTargets.put(slot, target);
    }

    public RenderTexture getRenderTarget(int slot){
        return renderTargets.get(slot);
    }

    public void loadTextures(){
        id = glGenFramebuffers();
        glBindFramebuffer(GL_FRAMEBUFFER, id);

        buffer = BufferUtils.createIntBuffer(renderTargets.size());
        for(int target : renderTargets.keySet()){
            renderTargets.get(target).loadTexture();
            glFramebufferTexture2D(GL_FRAMEBUFFER, target, GL_TEXTURE_2D, renderTargets.get(target).id, 0);
            renderTargets.get(target).unload();
            buffer.put(target);
            System.out.println(target);
        }
        buffer.flip();

        glDrawBuffers(buffer);

        renderBuffer = glGenRenderbuffers();
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, Display.aspectWidth, Display.aspectHeight);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderBuffer);

        if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE){
            assert false : "error creating frambebuffer";
        }

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    public void startRenderCapture(){
        glBindRenderbuffer(GL_RENDERBUFFER, renderBuffer);
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT32, Display.aspectWidth, Display.aspectHeight);
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_RENDERBUFFER, renderBuffer);
        glBindFramebuffer(GL_FRAMEBUFFER, id);
        glDrawBuffers(buffer);
        glClear(GL_DEPTH_BUFFER_BIT);
    }

    public void endRenderCapture(){
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        glClear(GL_DEPTH_BUFFER_BIT);
    }
}
