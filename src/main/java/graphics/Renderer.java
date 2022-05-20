package graphics;

import ecs.components.TextureLayers;
import ecs.components.Vao;
import ecs.entities.Camera;
import ecs.entities.Entity;
import ecs.scenes.Scene;
import enums.BufferTypes;
import enums.RenderTypes;
import gui.layers.Window;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Renderer {
    public static void renderScene(Scene scene){
        for(Entity entity:scene.entities){
            if(entity.hasComponent(Vao.class)){
                Vao vao = entity.getComponent(Vao.class);

                if(entity.hasComponent(TextureLayers.class)){
                    if(entity.getComponent(TextureLayers.class).skyboxTexture != null){
                        vao.load(RenderTypes.SKYBOX);
                        glDisable(GL_CULL_FACE);
                        glDepthFunc(GL_LEQUAL);
                        SkyboxTexture tex = entity.getComponent(TextureLayers.class).skyboxTexture;
                        vao.shader.uploadTexture(1, "texture_sampler");
                        glActiveTexture(GL_TEXTURE1);
                        tex.load();
                    }
                    else{
                        vao.load(RenderTypes.OBJECT);
                        Texture tex = entity.getComponent(TextureLayers.class).albedoTexture;
                        vao.shader.uploadTexture(0, "texture_sampler");
                        glActiveTexture(GL_TEXTURE0);
                        tex.load();
                    }
                }
                else if(entity.name == "test"){
                    vao.load(RenderTypes.GUI);
                }
                else{
                    vao.load(RenderTypes.OBJECT);
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
}
