package gui;

import engine.ecs.entities.Camera;
import engine.enums.BufferTypes;
import engine.graphics.Shader;
import engine.graphics.SkyboxTexture;
import engine.graphics.Texture;
import engine.graphics.Vbo;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GuiVao{
    public GuiShader shader;
    public int id;
    public HashMap<BufferTypes, Vbo> buffers = new HashMap<BufferTypes, Vbo>();
    public HashMap<BufferTypes, Integer> ids = new HashMap<BufferTypes, Integer>();


    public void uploadBuffer(Vbo buffer, BufferTypes name){
        buffers.put(name, buffer);
    }

    public void setBuffer(Vbo buffer, BufferTypes name){
        buffers.replace(name, buffer);
    }

    public void init() {
        if(shader == null){
            shader = new GuiShader("Gui.glsl");
        }
        id = glGenVertexArrays();
        glBindVertexArray(id);

        for(Map.Entry buffer:buffers.entrySet()){
            if(buffer.getKey() == BufferTypes.VERTEX_ARRAY_DATA){
                int vbo = glGenBuffers();

                ids.put(BufferTypes.VERTEX_ARRAY_DATA, vbo);

                glBindBuffer(GL_ARRAY_BUFFER, vbo);

                float[] data = (float[])buffers.get(BufferTypes.VERTEX_ARRAY_DATA).data;
                FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(data.length);
                dataBuffer.put(data).flip();

                glBufferData(GL_ARRAY_BUFFER, dataBuffer, GL_STATIC_DRAW);

                glVertexAttribPointer(0, buffers.get(BufferTypes.VERTEX_ARRAY_DATA).step, GL_FLOAT, false, 0,0);

                glBindBuffer(GL_ARRAY_BUFFER, 0);
            }
            else if(buffer.getKey() == BufferTypes.NORMAL_ARRAY_DATA){
                int vbo = glGenBuffers();

                ids.put(BufferTypes.NORMAL_ARRAY_DATA, vbo);

                glBindBuffer(GL_ARRAY_BUFFER, vbo);

                float[] data = (float[])buffers.get(BufferTypes.NORMAL_ARRAY_DATA).data;
                FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(data.length);
                dataBuffer.put(data).flip();

                glBufferData(GL_ARRAY_BUFFER, dataBuffer, GL_STATIC_DRAW);

                glVertexAttribPointer(2, buffers.get(BufferTypes.NORMAL_ARRAY_DATA).step, GL_FLOAT, false, 0,0);

                glBindBuffer(GL_ARRAY_BUFFER, 0);
            }
            else if(buffer.getKey() == BufferTypes.UV_ARRAY_DATA){
                int vbo = glGenBuffers();

                ids.put(BufferTypes.UV_ARRAY_DATA, vbo);

                glBindBuffer(GL_ARRAY_BUFFER, vbo);

                float[] data = (float[])buffers.get(BufferTypes.UV_ARRAY_DATA).data;
                FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(data.length);
                dataBuffer.put(data).flip();

                glBufferData(GL_ARRAY_BUFFER, dataBuffer, GL_STATIC_DRAW);

                glVertexAttribPointer(1, buffers.get(BufferTypes.UV_ARRAY_DATA).step, GL_FLOAT, false, 0, 0);

                glBindBuffer(GL_ARRAY_BUFFER, 0);
            }
            else if(buffer.getKey() == BufferTypes.INDEX_ARRAY_DATA){
                int vbo = glGenBuffers();

                ids.put(BufferTypes.INDEX_ARRAY_DATA, vbo);

                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);

                int[] data = (int[])buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
                IntBuffer dataBuffer = BufferUtils.createIntBuffer(data.length);
                dataBuffer.put(data).flip();

                glBufferData(GL_ELEMENT_ARRAY_BUFFER, dataBuffer, GL_STATIC_DRAW);
            }
        }

        glBindVertexArray(0);
        shader.link();
    }

    public void loop() {
    }

    public void end() {
        shader.cleanup();
        for(Map.Entry id:ids.entrySet()){
            int buffer = (int)id.getValue();
            glDeleteBuffers(buffer);
        }
        glBindVertexArray(0);;
        glDeleteVertexArrays(id);
    }

    public void draw(){
        load();

        int[] vertexSize = (int[]) buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
        glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);

        unload();
    }

    public void load(){
        shader.bind();
        glBindVertexArray(id);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
    }

    public void unload(){
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.unbind();
    }
}
