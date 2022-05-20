package ecs.components;

import ecs.entities.Camera;
import enums.BufferTypes;
import enums.RenderTypes;
import graphics.Shader;
import graphics.Vbo;
import gui.layers.Window;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Vao extends Component{
    public Shader shader;
    public int id;
    public HashMap<BufferTypes, Vbo> buffers = new HashMap<BufferTypes, Vbo>();
    public HashMap<BufferTypes, Integer> ids = new HashMap<BufferTypes, Integer>();

    public void uploadShader(Shader shader){
        this.shader = shader;
    }

    public void uploadBuffer(Vbo buffer, BufferTypes name){
        buffers.put(name, buffer);
    }

    public void setBuffer(Vbo buffer, BufferTypes name){
        buffers.replace(name, buffer);
    }

    @Override
    public void init() {
        if(shader == null){
            shader = new Shader("Unlit.glsl");
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

    public void load(RenderTypes type){
        Camera camera = (Camera)parent.parentScene.getEntity("playerCamera");
        shader.bind();
        shader.uploadVector3f(new Vector3f(100, 100, 100), "sun");
        shader.uploadMatrix(camera.projection, "projection");
        if(type == RenderTypes.SKYBOX){
            camera.view.m30(0);
            camera.view.m31(0);
            camera.view.m32(0);
            shader.uploadMatrix(camera.view, "view");
        }
        else if(type == RenderTypes.GUI){
            Window window = (Window)parent;
            shader.uploadVector3f(window.color, "element_color");
        }
        else if(type == RenderTypes.OBJECT){
            shader.uploadMatrix(camera.view, "view");
            if(parent.getComponent(TextureLayers.class).damp < 1){
                parent.getComponent(TextureLayers.class).damp = 1;
            }
            shader.uploadFloat(parent.getComponent(TextureLayers.class).damp, "damp");
            shader.uploadFloat(parent.getComponent(TextureLayers.class).specular, "specular");
        }
        shader.uploadMatrix(parent.getComponent(Transform.class).matrix, "transform");
        glBindVertexArray(id);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
    }

    public void unload(){
        glEnableVertexAttribArray(2);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.unbind();
    }
}
