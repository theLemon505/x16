package ecs.entities;

import ecs.components.TextureLayers;
import ecs.components.Vao;
import ecs.scenes.Scene;
import enums.BufferTypes;
import graphics.*;
import io.Display;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer extends Entity{
    public RenderTexture renderTexture = new RenderTexture(Display.aspectWidth, Display.aspectHeight);
    private PassShader shader;
    private Vao vao;

    float[] vertices = {
            1, -1, 0,
            -1, 1, 0,
            1, 1, 0,
            -1, -1, 0
    };
    Vbo vertexBuffer = new Vbo(vertices, 3);

    int[] indices = {
            2, 1, 0,
            0, 1, 3
    };
    Vbo indexBuffer = new Vbo(indices, 1);

    float[] uvs = {
            1, 0,
            0, 1,
            1, 1,
            0, 0
    };
    Vbo uvBuffer = new Vbo(uvs, 2);

    public Renderer() {
        super("renderer");
    }

    public void render(){
        for(Entity entity:parentScene.entities){
            if(entity.hasComponent(Vao.class)){
                Vao vao = entity.getComponent(Vao.class);

                if(entity.hasComponent(TextureLayers.class)){
                    if(entity.getComponent(TextureLayers.class).skyboxTexture != null){
                        vao.load(true);
                        glDisable(GL_CULL_FACE);
                        glDepthFunc(GL_LEQUAL);
                        SkyboxTexture tex = entity.getComponent(TextureLayers.class).skyboxTexture;
                        vao.shader.uploadTexture(1, "texture_sampler");
                        glActiveTexture(GL_TEXTURE1);
                        tex.load();
                    }
                    else{
                        vao.load(false);
                        Texture tex = entity.getComponent(TextureLayers.class).albedoTexture;
                        vao.shader.uploadTexture(0, "texture_sampler");
                        glActiveTexture(GL_TEXTURE0);
                        tex.load();
                    }
                }
                else{
                    vao.load(false);
                }


                int[] vertexSize = (int[]) vao.buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
                glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);


                if(entity.hasComponent(TextureLayers.class)){
                    if(entity.getComponent(TextureLayers.class).skyboxTexture != null){
                        glEnable(GL_CULL_FACE);
                        SkyboxTexture tex = entity.getComponent(TextureLayers.class).skyboxTexture;
                        tex.unload();
                    }
                    else{
                        Texture tex = entity.getComponent(TextureLayers.class).albedoTexture;
                        tex.unload();
                    }
                }
                vao.unload();
            }
        }

    }

    private void renderToScreen(){
        this.shader.bind();

        glBindVertexArray(vao.id);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        this.shader.uploadTexture(2, "texture_sampler");
        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, renderTexture.texture);

        int[] vertexSize = (int[]) vao.buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
        glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);

        glBindTexture(GL_TEXTURE_2D, 0);

        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        this.shader.unbind();
    }

    @Override
    public void awake() {
        vao = new Vao();
        vao.uploadBuffer(vertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        vao.uploadBuffer(indexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        vao.uploadBuffer(uvBuffer, BufferTypes.UV_ARRAY_DATA);
    }

    @Override
    public void init() {
        this.shader = new PassShader("Forward.glsl");
        renderTexture.loadTexture();
        lowerInit();
        vao.init();
        this.shader.link();
    }

    @Override
    public void loop() {
        renderTexture.xres = Display.aspectWidth;
        renderTexture.yres = Display.aspectHeight;
        glClearColor(0.2f,0.9f, 1f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        renderTexture.load();
        glClearColor(0.2f,0.9f, 1f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        render();
        renderTexture.unload();
        lowerLoop();
        renderToScreen();
    }

    @Override
    public void end() {
        vao.end();
        lowerEnd();
    }
}
