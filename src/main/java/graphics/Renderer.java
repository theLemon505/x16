package graphics;

import ecs.components.Vao;
import ecs.entities.Entity;
import ecs.scenes.Scene;
import enums.BufferTypes;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    public static void renderScene(Scene scene){
        for(Entity entity:scene.entities){
            if(entity.hasComponent(Vao.class)){
                Vao vao = entity.getComponent(Vao.class);
                vao.load();
                int[] vertexSize = (int[]) vao.buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
                glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);
                vao.unload();
            }
        }
    }
}
