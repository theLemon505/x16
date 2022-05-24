package engine.ecs.components;

import engine.enums.BufferTypes;
import engine.graphics.*;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class PassVao extends Component{
    public RenderTexture texture;

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
    public PassShader shader;
    public int id;
    public HashMap<BufferTypes, Vbo> buffers = new HashMap<BufferTypes, Vbo>();
    public HashMap<BufferTypes, Integer> ids = new HashMap<BufferTypes, Integer>();

    public void uploadBuffer(Vbo buffer, BufferTypes name){
        buffers.put(name, buffer);
    }

    @Override
    public void init() {
        shader = new PassShader("Forward.glsl");
        id = glGenVertexArrays();
        glBindVertexArray(id);

        uploadBuffer(vertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        uploadBuffer(indexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        uploadBuffer(uvBuffer, BufferTypes.UV_ARRAY_DATA);

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

    @Override
    public void loop() {
    }

    @Override
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

        shader.uploadTexture(0, "texture_sampler");
        glActiveTexture(GL_TEXTURE0);
        texture.load();

        int[] vertexSize = (int[]) buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
        glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);

        texture.unload();

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
