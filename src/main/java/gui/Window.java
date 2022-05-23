package gui;

import ecs.components.Vao;
import ecs.entities.Renderer;
import enums.BufferTypes;
import graphics.Shader;
import graphics.Vbo;
import io.Display;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public abstract class Window {
    public String title;
    public float width, height;
    public Vector2f position = new Vector2f();
    private Vector3f color = new Vector3f(1, 1, 1);
    private Vector3f tabColor = new Vector3f(0.5f, 0.5f, 0.5f);
    private Vao screen;
    private Vao tab;
    private Shader shader, texture;
    private Matrix4f matrix = new Matrix4f();
    private Matrix4f tabMatrix = new Matrix4f();
    float[] vertices = {
            1, -1, 0,
            -1, 0.8f, 0,
            1, 0.8f, 0,
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

    float[] tvertices = {
            1, 0.8f, 0,
            -1, 1f, 0,
            1, 1f, 0,
            -1, 0.8f, 0
    };
    Vbo tvertexBuffer = new Vbo(tvertices, 3);

    int[] tindices = {
            2, 1, 0,
            0, 1, 3
    };
    Vbo tindexBuffer = new Vbo(tindices, 1);

    float[] tuvs = {
            1, 0,
            0, 1,
            1, 1,
            0, 0
    };
    Vbo tuvBuffer = new Vbo(tuvs, 2);

    public Window(String title, float width, float height){
        this.title = title;
        this.width = width;
        this.height = height;
    }
    public Window(String title, float width, float height, float x, float y){
        this.title = title;
        this.width = width;
        this.height = height;
        this.position.x = x;
        this.position.y = y;
    }

    public void init(){
        shader = new Shader("Gui.glsl");
        texture = new Shader("GuiTexture.glsl");
        screen = new Vao();
        tab = new Vao();
        shader.link();
        texture.link();
        screen.uploadBuffer(vertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        screen.uploadBuffer(indexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        screen.uploadBuffer(uvBuffer, BufferTypes.UV_ARRAY_DATA);
        screen.init();
        tab.uploadBuffer(tvertexBuffer, BufferTypes.VERTEX_ARRAY_DATA);
        tab.uploadBuffer(tindexBuffer, BufferTypes.INDEX_ARRAY_DATA);
        tab.uploadBuffer(tuvBuffer, BufferTypes.UV_ARRAY_DATA);
        tab.init();
        updateMatrix();
    }

    public void loop(){
        updateMatrix();
        screen.loop();
        tab.loop();
    }

    private void updateMatrix(){
        matrix.identity();
        matrix.translate(position.x, position.y, 0);
        matrix.scale(width / 100, height / 100, 1);
        tabMatrix.identity();
        tabMatrix.translate(position.x, position.y, 0);
        tabMatrix.scale((width / 100),  (height / 100), 1);
    }

    public void draw(){
        texture.bind();

        glBindVertexArray(screen.id);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        texture.uploadMatrix(matrix, "transform");
        texture.uploadTexture(2, "texture_sampler");
        glActiveTexture(GL_TEXTURE2);
        Renderer.framebuffer.getRenderTarget(GL_COLOR_ATTACHMENT0).load();

        int[] vertexSize = (int[]) screen.buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
        glDrawElements(GL_TRIANGLES, vertexSize.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        texture.unbind();

        shader.bind();

        shader.uploadMatrix(tabMatrix, "transform");
        shader.uploadVector3f(tabColor, "element_color");

        glBindVertexArray(tab.id);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        int[] tvertexSize = (int[]) tab.buffers.get(BufferTypes.INDEX_ARRAY_DATA).data;
        glDrawElements(GL_TRIANGLES, tvertexSize.length, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);

        shader.unbind();
    }

    public void end(){
        tab.end();
        screen.end();
    }
}
