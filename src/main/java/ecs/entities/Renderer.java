package ecs.entities;

import ecs.components.PassVao;
import ecs.components.Vao;
import enums.BufferTypes;
import graphics.Fbo;
import graphics.Shader;
import graphics.Vbo;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer extends Entity{

    private PassVao vao;

    private Fbo framebuffer = new Fbo();

    public Renderer(){
        super("renderer");
    }

    public void render(){

        framebuffer.startRenderCapture();

        glClearColor(0.2f,0.9f, 1f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        for(Entity entity : parentScene.entities){
            if(entity.hasComponent(Vao.class)){
                entity.getComponent(Vao.class).draw();
            }
        }
        framebuffer.endRenderCapture();

        display();
    }

    @Override
    public void awake() {
        vao = new PassVao();
    }

    @Override
    public void init() {
        addComponent(vao);
        framebuffer.addRenderTarget(GL_COLOR_ATTACHMENT0, GL_RGB);
        framebuffer.loadTextures();
        lowerInit();
    }

    private void display(){
        vao.texture = framebuffer.getRenderTarget(GL_COLOR_ATTACHMENT0);
        vao.draw();
    }

    @Override
    public void loop() {
        lowerLoop();
    }

    @Override
    public void end() {
        lowerEnd();
    }
}
