package graphics;

import ecs.components.TextureLayers;
import ecs.components.Vao;
import ecs.entities.Entity;
import ecs.scenes.Scene;
import enums.BufferTypes;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_UNSIGNED_INT_10_10_10_2;
import static org.lwjgl.opengl.GL12.GL_UNSIGNED_INT_2_10_10_10_REV;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class Renderer {
    public static void renderScene(Scene scene){
        for(Entity entity:scene.entities){
            if(entity.hasComponent(Vao.class)){
                Vao vao = entity.getComponent(Vao.class);
                vao.load();

                if(entity.hasComponent(TextureLayers.class)){
                    Texture tex = entity.getComponent(TextureLayers.class).albedoTexture;
                    vao.shader.uploadTexture(0, "texture_sampler");
                    glActiveTexture(GL_TEXTURE0);
                    tex.load();
                }

                int[] vertexSize = (int[]) vao.buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
                float[] vertices = (float[]) vao.buffers.get(BufferTypes.VERTEX_ARRAY_DATA).data;
                glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);

                if(entity.hasComponent(TextureLayers.class)){
                    Texture tex = entity.getComponent(TextureLayers.class).albedoTexture;
                    tex.unload();
                }
                vao.unload();
            }
        }
    }
}
